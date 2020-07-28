package concurrency.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor(); // only with single thread.
//        ExecutorService executorService = Executors.n(); // only with single thread.

    }
}
