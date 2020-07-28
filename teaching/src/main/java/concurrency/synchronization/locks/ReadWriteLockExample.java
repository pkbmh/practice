package concurrency.synchronization.locks;

import java.util.concurrent.locks.*;

public class ReadWriteLockExample {
    int value;
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); // ReadWriteLock

    public void updateValue(int newValue) {
        readWriteLock.writeLock().lock();
        value = newValue;
        readWriteLock.writeLock().unlock();
    }
    public int getValue() {
        readWriteLock.readLock().lock();
        int tmp = value;
        readWriteLock.readLock().unlock();
        return tmp;
    }
}
