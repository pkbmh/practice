package concurrency.synchronization;

public class VolatileExample {
    private static  int MY_INT = 0;

    public static void main(String[] args) {
        new ChangeListener().start();
        new ChangeMaker().start();
    }

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while ( local_value < 5){ // this line encourage JVM/thread-stack to cache the MY_INT value because it's used in the loop so many times
                if( local_value != MY_INT){
                    System.out.printf("Got Change for MY_INT : {%d}", MY_INT);
                    System.out.println();
                    local_value= MY_INT;
                }
//                else {
//                    System.out.printf("No Change for MY_INT : {%d}", MY_INT);
//                    System.out.println();
//                }
            }
        }
    }

    static class ChangeMaker extends Thread{
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT < 5){
                System.out.printf( "Incrementing MY_INT to {%d}", local_value+1);
                System.out.println();
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
