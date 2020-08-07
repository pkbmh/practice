package concurrency.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RaceConditionExample {
    protected long count = 0;

    public void increment(long value) {
        synchronized (this) {
            this.count = this.count + value;
            // 100.....
        }
    }
//    public void decrement(long value) {
//        this.count = this.count - value;
//    }
    public static void main(String[] args) {
        for(;;) {
            ExecutorService executor = Executors.newFixedThreadPool(10);
            RaceConditionExample example = new RaceConditionExample();
            for (int loop = 0; loop < 100; ++loop)
                executor.submit(() -> {
                    for (int i = 0; i < 5; ++i) {
                        example.increment(100);
                        // 100 * 5 * 100 = 50,000
//                        example.decrement(100);
                    }
                });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Counter final value=%d", example.count);
            System.out.println();
            executor.shutdown();
        }
    }
}
