import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalExample {
    String shared;
    ThreadLocal<String> local;
    Random random = new Random();
    ThreadLocalExample(String shared, String local) {
        this.shared = shared;
        this.local = new ThreadLocal<>();
        this.local.set(local);
    }
    void setNewLocal(String s) {
        this.local.set(s);
    }
    void setShared(String s) {
        this.shared = s;
    }
    int getRandomNumber() {
//        return random.nextInt();
        return ThreadLocalRandom.current().nextInt();
    }

    public static void main(String[] args) {
        ThreadLocalExample threadLocalExample = new ThreadLocalExample("Main Thread", "Main Thread");
        Thread t = new Thread(() -> {
            threadLocalExample.setNewLocal("New Thread");
            threadLocalExample.setShared("New Thread");
        });
        try {
            t.start();
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("Object values: shared:{%s}, local:{%s}\n", threadLocalExample.shared, threadLocalExample.local.get());

    }
}

