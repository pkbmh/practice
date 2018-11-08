package parkinglot.core;


import parkinglot.slot.Slot;
import parkinglot.vehicle.Vehicle;

public class ParkingLotImpl implements ParkingLot{
    private final ParkingLotDataAdapter parkingLotDataAdapter;

    private ParkingLotImpl(int capacity) {
        System.out.println("Creating parking lot of capacity : " + capacity);
        this.parkingLotDataAdapter = ParkingLotDataAdapterImpl.of(capacity);
    }
    public static ParkingLot of(int capacity){
        return new ParkingLotImpl(capacity);
    }

    @Override
    public boolean parkVehicle(Vehicle vehicle) throws Exception {
        Slot slot = parkingLotDataAdapter.getFreeSlot();
        return parkingLotDataAdapter.bookSlot(slot, vehicle);
    }

    @Override
    public boolean leaveSlot(int slotNumber) throws Exception {
        Slot slot = parkingLotDataAdapter.getSlotByNumber(slotNumber);
        Vehicle vehicle = parkingLotDataAdapter.getVehicleBySlot(slot);
        if(slot == null)throw new IllegalArgumentException("Not an valid slot number");
        if(slot.isAvailable()) throw new IllegalArgumentException("Slot is free not used by any vehicle");
        if(vehicle == null) throw new IllegalStateException("Slot must be used by some vehicle");
        synchronized (this){
            System.out.println("slot_number " + slot.getSlotNumber() + "freed by vehicle: " +vehicle.getNumber());
            slot.markAvailable();
            parkingLotDataAdapter.addFreeSlot(slot);
            parkingLotDataAdapter.removeVehicleFromSlot(slot);
            return true;
        }
    }

    @Override
    public void printStatusBooked() {
        parkingLotDataAdapter.printStatusBooked();
    }

    @Override
    public void printFreeStatus() {
        parkingLotDataAdapter.printFreeStatus();
    }
}
