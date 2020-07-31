package future;

import java.util.Random;
import java.util.concurrent.*;

public class FutureExample {
    static class CallableExample implements Callable<Integer> {
        public Integer call() throws Exception {
            Random generator = new Random();
            Integer randomNumber = generator.nextInt(5);
            Thread.sleep(randomNumber * 1000);
            return randomNumber;
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Callable<Integer> callable = new CallableExample();
        FutureTask<Integer> task = new FutureTask(callable);
        Thread t = new Thread(task);
        t.start();
    }
}
