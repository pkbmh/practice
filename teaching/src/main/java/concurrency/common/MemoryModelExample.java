package concurrency.common;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemoryModelExample {
    public static void main(String[] args) {
        Coordinate coordinate = new Coordinate(0, 0);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            executorService.submit(() -> {
                int x = random.nextInt();
                int y = random.nextInt();
                System.out.printf("coordinate distance from (%d,%d) is = %f", x, y, coordinate.distance(x, y));
            });
        }
    }
}

class Coordinate {
    int x, y; //stored in heap memory, thread will share these variables
    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    double distance(int x, int y) {
        //stored in method stack memory, each thread/caller will have own copy of tmp
        double tmp = Math.pow(this.x-x, 2) + Math.pow(this.y-y, 2);
        return Math.sqrt(tmp);
    }
}