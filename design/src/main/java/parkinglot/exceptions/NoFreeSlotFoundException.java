package parkinglot.exceptions;

public class NoFreeSlotFoundException extends Exception {
    public NoFreeSlotFoundException(String msg){
        super(msg);
    }
}
