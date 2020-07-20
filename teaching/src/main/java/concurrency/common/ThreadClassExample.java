package concurrency.common;

public class ThreadClassExample  extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running...");
    }
}

class RunnableClassExample implements Runnable {
    @Override
    public void run() {
        System.out.println("Runnable is running...");
    }
}
