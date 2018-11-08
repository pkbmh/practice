package parkinglot.slot;

public interface Slot {
    boolean isAvailable();
    boolean markUnavailable() throws Exception;
    int getSlotNumber();
    boolean markAvailable() throws Exception;
}
