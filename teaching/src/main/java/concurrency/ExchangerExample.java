package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable taskA = () -> {
            try {
                String message = exchanger.exchange("from A");
                System.out.printf("Message from B {%s}", message);
//                assert ("from B", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        Runnable taskB = () -> {
            try {
                String message = exchanger.exchange("from B");
                System.out.printf("Message from A {%s}", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };
        CompletableFuture.allOf(
                CompletableFuture.runAsync(taskA), CompletableFuture.runAsync(taskB)).join();
    }
}
