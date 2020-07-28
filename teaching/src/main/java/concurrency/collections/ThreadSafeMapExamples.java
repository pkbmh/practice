package concurrency.collections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadSafeMapExamples {
    public static void main(String[] args) {
        int THREAD_POOL_SIZE = 5;
        try {
            performTest(new Hashtable<>(), THREAD_POOL_SIZE);
            performTest(Collections.synchronizedMap(new HashMap<>()), THREAD_POOL_SIZE);
            performTest(new ConcurrentHashMap<>(), THREAD_POOL_SIZE);
            performTest(new ConcurrentSkipListMap<>(), THREAD_POOL_SIZE); //
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private static void performTest(final Map<Integer, Integer> map, int concurrency) throws InterruptedException {
        System.out.println("Test started for: " + map.getClass());
        long averageTime = 0;
        for (int i = 0; i < 5; i++) {

            long startTime = System.nanoTime();
            ExecutorService ExServer = Executors.newFixedThreadPool(concurrency);

            for (int j = 0; j < concurrency; j++) {
                ExServer.execute(() -> {
                    for (int i1 = 0; i1 < 2000000000; i1++) {
                        Integer RandomNumber = (int) Math.ceil(Math.random() * 550000);

                        // Retrieve value. We are not using it anywhere
                        Integer Value = map.get(RandomNumber);

                        // Put value
                        map.put(RandomNumber, RandomNumber);
                    }
                });
            }

            // Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. Invocation
            // has no additional effect if already shut down.
            // This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.
            ExServer.shutdown();

            // Blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, or the current thread is
            // interrupted, whichever happens first.
            ExServer.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

            long entTime = System.nanoTime();
            long totalTime = (entTime - startTime) / 1000000L;
            averageTime += totalTime;
            System.out.println("1M entried added/retrieved in " + totalTime + " ms");
        }
        System.out.println("For " + map.getClass() + " the average time is " + averageTime / 5 + " ms\n");
    }
}
