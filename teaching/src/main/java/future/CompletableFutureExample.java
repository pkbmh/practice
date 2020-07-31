package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    public static void main(String[] args) {
        //TODO CompletableFuture.runAsync() :
        System.out.println("*********************************");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("trigger future");
            future.get(); // blocks the call until function is executed
            System.out.printf("Future get done time [%dms]\n", System.currentTimeMillis()-startTime);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }


        System.out.println("*********************************");
        //TODO CompletableFuture.supplyAsync() :
        CompletableFuture<String> futureString = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("trigger future get result");
            System.out.printf("futureString=[%s]\n", futureString.get());
            System.out.printf("Future get result done time [%dms]\n", System.currentTimeMillis()-startTime);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("*********************************");


        //TODO Callback
        // thenApply
        CompletableFuture<String> futureWithThenApply = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenApply(futureResult -> {
            System.out.println("Then apply received callback");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return futureResult + " " + "then apply";
        });
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("trigger futureWithThenApply get result");
            System.out.printf("futureWithThenApply=[%s]\n", futureWithThenApply.get());
            System.out.printf("futureWithThenApply get result done time [%dms]\n", System.currentTimeMillis()-startTime);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("*********************************");

        // TODO thenAccept
        CompletableFuture<Void> futureWithThenAccept = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAccept(string -> {
            System.out.printf("got string in thenAccept: [%s]\n", string);
        });
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("trigger futureWithThenAccept");
            futureWithThenAccept.get();
            System.out.printf("done futureWithThenAccept: time[%dms]\n", System.currentTimeMillis()-startTime);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("*********************************");

        // TODO thenRun
        CompletableFuture<String> futureWithThenRun = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });
        CompletableFuture<Void> thenRunFuture = futureWithThenRun.thenRun(() -> {
            System.out.printf("start: then run after the futureWithThenRun\n");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("end: then run after the futureWithThenRun\n");
        });
        try {
            System.out.printf("got string from futureWithThenRun: [%s]\n", futureWithThenRun.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("*********************************");
        try {
            thenRunFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        // TODO exceptionally
        System.out.println("*********************************");
        CompletableFuture<String> futureExceptionExample = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (true) throw new IllegalArgumentException("inten");
            return "hello";
        }).exceptionally(e -> {
            e.printStackTrace();
            return "error happened";
        });
        try {
            String str = futureExceptionExample.get();
            System.out.printf("futureExceptionExample value = [%s]\n", str);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("*********************************");
    }

}
