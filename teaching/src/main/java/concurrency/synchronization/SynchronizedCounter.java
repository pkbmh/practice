package concurrency.synchronization;

public class SynchronizedCounter {
    private long c = 0;
    //synchronized-method intrinsic lock, thread will acquire the lock on this
    public synchronized void increment() {
        System.out.println("Inside the increment");
        System.out.println(System.currentTimeMillis());
        System.out.println(Thread.currentThread().getName());
        c++;
    }
    public synchronized void decrement(int a) {
        if (a == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
         return;
        }
        System.out.println("Inside the decrement");
        System.out.println(System.currentTimeMillis());
        System.out.println(Thread.currentThread().getName());
        c--;
        decrement(a-1); // Reentrant using the same lock
    }
    public  long value() {
        long v;
        System.out.println("Inside the value");
        System.out.println(System.currentTimeMillis());
        System.out.println(Thread.currentThread().getName());
        synchronized (this) { // synchronized-block
             v = c;
        }
        return v;
    }
}