package concurrency.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadRunExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 5; i++) {
            RunnableClassExample r = new RunnableClassExample();
            executorService.submit(r);
            ThreadClassExample t = new ThreadClassExample();
            executorService.submit(t);
        }
        executorService.shutdown();
        System.out.println("***************");
        RunnableClassExample r = new RunnableClassExample();
        ThreadClassExample t = new ThreadClassExample();
        t.start();
        Thread thread = new Thread(r);
        thread.start();
        try {
            t.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
