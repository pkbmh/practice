package parkinglot.slot;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SlotImpl implements Slot {
    private  boolean isAvailable;
    private final int slotNumber;

    public static Slot createNewFreeSlot(int slotNumber){
        return new SlotImpl(true, slotNumber);
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean markUnavailable() throws Exception{
        if(isAvailable) {
            isAvailable = false;
            return true;
        }
        throw new Exception("Slot is already booked by someone else");
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    @Override
    public boolean markAvailable() throws Exception {
        if(!isAvailable) {
            isAvailable = true;
            return true;
        }
        throw new Exception("Slot is already free");
    }
}
