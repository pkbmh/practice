package concurrency.synchronization;

import java.util.concurrent.locks.*;

public class LockExample {
    Lock reentrantLock = new ReentrantLock(); // implements LOCK
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); // ReadWriteLock
    StampedLock stampedLock  = new StampedLock(); // java 8 optimistic locking
}
