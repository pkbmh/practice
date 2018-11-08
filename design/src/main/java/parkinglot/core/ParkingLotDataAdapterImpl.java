package parkinglot.core;

import lombok.Getter;
import parkinglot.exceptions.NoFreeSlotFoundException;
import parkinglot.slot.Slot;
import parkinglot.slot.SlotImpl;
import parkinglot.vehicle.Vehicle;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Getter
public class ParkingLotDataAdapterImpl implements ParkingLotDataAdapter {
    private final int capacity;
    private final Queue<Slot> freeSlots;
    private final Map<Integer, Slot> slotMap;
    private final Map<Slot, Vehicle> slotVehicleMap;


    private ParkingLotDataAdapterImpl(int capacity){
        this.capacity = capacity;
        this.freeSlots = new LinkedList<Slot>();
        this.slotMap = new HashMap<>();
        this.slotVehicleMap = new HashMap<>();
        init();
    }

    private void init(){
        for(int i = 1; i <= capacity; i++){
            Slot slot = SlotImpl.createNewFreeSlot(i);
            freeSlots.add(slot);
            slotMap.put(i, slot);
        }
    }
    public static ParkingLotDataAdapter of(int capacity){
        return new ParkingLotDataAdapterImpl(capacity);
    }

    @Override
    public Slot getFreeSlot() throws NoFreeSlotFoundException {
        if(freeSlots.isEmpty()) throw new NoFreeSlotFoundException("Sorry no, Free slot available");
        return freeSlots.poll();
    }

    @Override
    public boolean bookSlot(Slot slot, Vehicle vehicle) throws Exception {
        try {
            if (!slot.isAvailable()) throw new IllegalStateException("Slot is not available");
            synchronized (this) {
                slot.markUnavailable();
                slotVehicleMap.put(slot, vehicle);
                System.out.println("Allocated slot number : " + slot.getSlotNumber() + " For Vehicle : " + vehicle.getNumber());
                return true;
            }
        }catch (Exception e){
            System.out.println("Could not book given slot");
            addSlotToPoolIfNotBooked(slot);
            e.printStackTrace();
        }
        return false;
    }

    private void addSlotToPoolIfNotBooked(Slot slot){
        if(slot.isAvailable()){
            freeSlots.add(slot);
        }
    }
    @Override
    public Slot getSlotByNumber(int slotNumber) {
        return slotMap.getOrDefault(slotNumber, null);
    }

    @Override
    public Vehicle getVehicleBySlot(Slot slot) {
        return slotVehicleMap.getOrDefault(slot, null);
    }

    @Override
    public boolean addFreeSlot(Slot slot) {
        return freeSlots.add(slot);
    }

    @Override
    public boolean removeVehicleFromSlot(Slot slot) {
        return slotVehicleMap.remove(slot) != null;
    }

    @Override
    public void printStatusBooked() {
        System.out.println("Slot Number         Registration            color");
        for(Map.Entry<Slot, Vehicle> slotVehicleEntry : slotVehicleMap.entrySet()){
            System.out.println(slotVehicleEntry.getKey().getSlotNumber() + "      " + slotVehicleEntry.getValue().getNumber() + "   " + slotVehicleEntry.getValue().getColor());
        }
    }

    @Override
    public void printFreeStatus() {
        System.out.println("Slots");
        for(Map.Entry<Integer, Slot> slotEntry : slotMap.entrySet()) {
            if(slotEntry.getValue().isAvailable()) System.out.println(slotEntry.getKey());
        }
    }
}
