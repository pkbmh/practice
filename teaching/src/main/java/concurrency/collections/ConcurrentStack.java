package concurrency.collections;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentStack<T> {
    private AtomicReference<Node> head = new AtomicReference<Node>();
    ConcurrentStack() {
    }
    public void push(T t) {
        if (t == null) {
            return;
        }
        Node<T> n = new Node<T>(t);
        Node<T> current;
        do {
            current = head.get();
            n.setNext(current);
        } while (!head.compareAndSet(current, n));
    }

    public T pop() {
        Node<T> currentHead = null;
        Node<T> futureHead = null;
        do {
            currentHead = head.get();
            if (currentHead == null) {
                return null;
            }
            futureHead = currentHead.next;
        } while (!head.compareAndSet(currentHead, futureHead));

        return currentHead.data;
    }
    public boolean isEmpty() {
        if (head.get() == null) {
            return true;
        }
        return false;
    }
    private static class Node<T> {

        private final T data;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }

        private void setNext(Node next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ConcurrentStack<Integer> stack = new ConcurrentStack<>();
        ExecutorService e = Executors.newFixedThreadPool(5);
        e.submit(() -> {
           for(int i = 0; i < 10; ++i) {

               try {
                   Thread.sleep(30);
               } catch (InterruptedException interruptedException) {
                   interruptedException.printStackTrace();
               }
               System.out.printf("push:[%d]\n", i);
               stack.push(i);
           }
        });
        e.submit(()-> {
            int i = 0;
            //System.out.println("consumer is running");
            while(i < 10) {
                try {
                    Thread.sleep(60);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                Integer a = stack.pop();
                if(a != null) {
                    System.out.printf("Top:[%d]\n",a);
                    i++;
                }
            }
        });


        try {
            Thread.sleep(500);
            e.shutdown();
            e.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

    }
}
