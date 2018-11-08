package parkinglot.core;


import parkinglot.vehicle.Vehicle;

public interface ParkingLot {
    boolean parkVehicle(Vehicle vehicle) throws Exception;
    boolean leaveSlot(int slotNumber) throws Exception;
    void printStatusBooked();
    void printFreeStatus();
}
