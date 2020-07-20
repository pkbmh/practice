package concurrency.common;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            return "Hello";
        });
        try {
            String v = future.get();
            System.out.println(v);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
