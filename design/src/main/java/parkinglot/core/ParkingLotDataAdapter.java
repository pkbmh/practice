package parkinglot.core;

import parkinglot.exceptions.NoFreeSlotFoundException;
import parkinglot.slot.Slot;
import parkinglot.vehicle.Vehicle;

public interface ParkingLotDataAdapter {
    Slot getFreeSlot() throws NoFreeSlotFoundException;
    boolean bookSlot(Slot slot, Vehicle vehicle) throws Exception;
    Slot getSlotByNumber(int slotNumber);
    Vehicle getVehicleBySlot(Slot slot);
    boolean addFreeSlot(Slot slot);
    boolean removeVehicleFromSlot(Slot slot);
    void printStatusBooked();
    void printFreeStatus();
}
