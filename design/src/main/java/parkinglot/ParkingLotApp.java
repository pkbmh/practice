package parkinglot;

import parkinglot.core.ParkingLot;
import parkinglot.core.ParkingLotImpl;
import parkinglot.core.ParkingLotInputParser;
import parkinglot.util.Util;
import parkinglot.vehicle.Vehicle;

import java.util.Scanner;

import static parkinglot.core.ParkingLotConstants.CREATE_OPERATION;
import static parkinglot.core.ParkingLotConstants.LEAVE_OPERATION;
import static parkinglot.core.ParkingLotConstants.PARK_OPERATION;
import static parkinglot.core.ParkingLotConstants.STATUS_OPERATION;
import static parkinglot.core.ParkingLotConstants.STATUS_OPERATION_TYPE_ALLOCATED;
import static parkinglot.core.ParkingLotConstants.STATUS_OPERATION_TYPE_FREE;

public class ParkingLotApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Please input the operations, -1 to exit");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        exitInstruction(input);
        String inputArr[] = Util.splitStringBySpace(input);
        if(inputArr.length < 2 && inputArr[0].equals(CREATE_OPERATION))throw new Exception("input format is not correct");
        int capacity = ParkingLotInputParser.getSlotCapacity(inputArr);
        if(capacity < 1) throw new Exception("Slot capacity should be greater then 0");
        ParkingLot parkingLot = ParkingLotImpl.of(capacity);
        while(true) {
            try {

                input = scanner.nextLine();
                exitInstruction(input);
                inputArr = Util.splitStringBySpace(input);

                if (inputArr.length > 1) {
                    switch (inputArr[0]) {
                        case PARK_OPERATION:
                            Vehicle vehicle = ParkingLotInputParser.getVehicleDetails(inputArr);
                            parkingLot.parkVehicle(vehicle);
                            break;
                        case LEAVE_OPERATION:
                            Integer slotNumberToFree = ParkingLotInputParser.getSlotNumberToFree(inputArr);
                            parkingLot.leaveSlot(slotNumberToFree);
                            break;
                        case STATUS_OPERATION:
                            switch (ParkingLotInputParser.getStatusType(inputArr)) {
                                case STATUS_OPERATION_TYPE_ALLOCATED:
                                    parkingLot.printStatusBooked();
                                    break;
                                case STATUS_OPERATION_TYPE_FREE:
                                    parkingLot.printFreeStatus();
                                    break;
                                default:
                                    throw new IllegalArgumentException("No status found");
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("No operation found");
                    }
                }
                else {
                    throw new IllegalArgumentException("Invalid Input");
                }
            }catch (Exception e){
                System.out.println("Exception msg: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void exitInstruction(String str){
        if(str != null && str.length() > 0  && str.equals("-1")){
            System.out.println("bye bye..!!");
            System.exit(1);
        }
    }

}
