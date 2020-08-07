package concurrency.common;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class LiveLockExample {
    private Lock lock1 = new ReentrantLock(true);
    private Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        LiveLockExample livelock = new LiveLockExample();
            new Thread(livelock::operation1, "T1").start();
            new Thread(livelock::operation2, "T2").start();

    }

    public void operation1() {
        try{
        while (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
//            boolean getLock = lock1.tryLock(100, TimeUnit.MILLISECONDS);
            System.out.printf("Thread: %s, lock1 acquired, trying to acquire lock2.\n", Thread.currentThread().getName());
            sleep(200);

            if (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
                System.out.printf("Thread: %s, lock2 acquired.\n", Thread.currentThread().getName());
            } else {
                System.out.printf("Thread: %s, cannot acquire lock2, releasing lock1.\n", Thread.currentThread().getName());
                lock1.unlock();
                continue;
            }

            System.out.printf("Thread: %s, executing first operation.\n", Thread.currentThread().getName());
            break;
        }
        lock2.unlock();
        lock1.unlock();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void operation2()  {
        try {
            while (lock2.tryLock(100, TimeUnit.MILLISECONDS)) {
//                lock2.tryLock(100, TimeUnit.MILLISECONDS);
                System.out.printf("Thread: %s, lock2 acquired, trying to acquire lock1.\n", Thread.currentThread().getName());
                sleep(200);

                if (lock1.tryLock(100, TimeUnit.MILLISECONDS)) {
                    System.out.printf("Thread: %s, lock1 acquired.\n", Thread.currentThread().getName());
                } else {
                    System.out.printf("Thread: %s, cannot acquire lock1, releasing lock2.\n", Thread.currentThread().getName());
                    lock2.unlock();
                    continue;
                }

                System.out.printf("Thread: %s, executing second operation.\n", Thread.currentThread().getName());
                break;
            }
            lock1.unlock();
            lock2.unlock();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}