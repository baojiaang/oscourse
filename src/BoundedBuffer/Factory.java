package BoundedBuffer;


import java.util.ArrayList;
import java.util.List;

public class Factory {
    public static void main(String[] args) {
        final int PRODUCTOR_COUNT = 5;
        final int CONSUMER_COUNT = 10;

        List<Thread> producers = new ArrayList<>();
        List<Thread> consumers = new ArrayList<>();

        BoundedBuffer buffer = new BoundedBuffer();

            Thread p = new Thread(new Producer(buffer));

            p.start();


        for(int i = 0; i<CONSUMER_COUNT; ++i) {
            Thread c = new Thread(new Consumer(buffer));
            consumers.add(c);
            c.start();
        }
    }

}

