package concurrency.synchronization.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    int value;
    Lock reentrantLock = new ReentrantLock(); // implements LOCK
    public void increment(int cnt) {
        reentrantLock.lock();
        value += cnt;
        reentrantLock.unlock();
    }
    public int getValue() {
        reentrantLock.lock();
        int tmp = value;
        reentrantLock.unlock();
        return tmp;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        LockExample lockExample = new LockExample();
        long t = System.currentTimeMillis();
        for(int i = 0; i < 10; ++i) {
            executorService.submit(() -> {
                while(System.currentTimeMillis()-t < 200) {
                    lockExample.increment(1);
                }
            });
        }
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
           executorService.shutdown();
        }
        System.out.printf("Final value {%d}\n", lockExample.value);
    }
}
