package progresssyn;

import java.util.ArrayList;
import java.util.List;

public class Factory {
    public static void main(String[] args) {
        final int PRODUCTOR_COUNT = 5;
        final int CONSUMER_COUNT = 10;

        List<Thread> producers = new ArrayList<>();
        List<Thread> consumers = new ArrayList<>();

        BoundedBuffer buffer = new BoundedBuffer();

        for(int i = 0; i<PRODUCTOR_COUNT; ++i) {
            Thread p = new Thread(new Producer(buffer));
            producers.add(p);
            p.start();
        }

        for(int i = 0; i<CONSUMER_COUNT; ++i) {
            Thread c = new Thread(new Consumer(buffer));
            consumers.add(c);
            c.start();
        }

        try {
            Thread.sleep(60*1000);
        }catch(InterruptedException e) {}
    }
}

