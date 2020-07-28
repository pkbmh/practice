package concurrency.collections;

import java.util.concurrent.*;

public class LinkedTransferQueueExample {
    TransferQueue<String> queue = new LinkedTransferQueue<>();
    public void sendMessage(String s) {
        System.out.printf("sending {%s}\n", s);
        try {
            queue.transfer(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("received by consumer {%s}\n", s);
    }
    public void receiveMessage() {
       while(true) {
           try {
               Thread.sleep(3000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           String s = queue.poll();
           if (s == null) return;
           System.out.printf("receive {%s}\n", s);
       }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        LinkedTransferQueueExample example = new LinkedTransferQueueExample();
        executorService.submit(()-> {
           for(int i = 0; i < 10; ++i) {
               example.sendMessage(i+"");
           }
        });
        executorService.submit(() -> {
            example.receiveMessage();
        });
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
