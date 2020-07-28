package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolMotivation {
    public static long  runThread(List<Runnable> runnableList) {
        long startTime = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for(Runnable runnable : runnableList) {
            Thread t = new Thread(runnable);
            threads.add(t);
            t.start();
        }
        for(Thread t: threads) {
            if(t.isAlive()) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.
                            printStackTrace();
                }
            }
        }
        return System.currentTimeMillis()-startTime;
    }
    public static long runExecutorService(List<Runnable> runnableList) {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(Runnable runnable: runnableList) {
            executorService.execute(runnable);
        }
        executorService.shutdown();
        return System.currentTimeMillis()-startTime;
    }
    public static void main(String[] args) {
        List<Runnable> runnableList = new ArrayList<>();
        for(int i = 0; i < 20000; i++) {
            runnableList.add(new Runnable() {
                @Override
                public void run() {
        //            System.out.println("hello");
                }
            });
        }
        System.out.printf("Time taken in the runThread: [%d]ms\n", runThread(runnableList));
        System.out.printf("Time taken by executor service: [%d]ms\n", runExecutorService(runnableList));
    }
}
