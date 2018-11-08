package parkinglot.core;

import parkinglot.vehicle.Car;
import parkinglot.vehicle.Vehicle;

public class ParkingLotInputParser {
    public static int getSlotCapacity(String arr[]){
        checkArrayLen(arr, 2);
        return Integer.parseInt(arr[1]);
    }

    public static Vehicle getVehicleDetails(String arr[]){
        checkArrayLen(arr, 3);
        return new Car(arr[1], arr[2]);
    }

    public static int getSlotNumberToFree(String arr[]){
        checkArrayLen(arr, 2);
        return Integer.parseInt(arr[1]);
    }
    public static String getStatusType(String arr[]) {
        checkArrayLen(arr, 2);
        return arr[1];
    }

    private static void checkArrayLen(String arr[], int expectedLen){
        if(arr.length < expectedLen) throw new IllegalArgumentException("Input is not correct please try again");
    }
}
