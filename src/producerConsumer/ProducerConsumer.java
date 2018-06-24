package producerConsumer;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author dell
 */
public class ProducerConsumer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        final int PRODUCTOR_COUNT = 5;
        final int CONSUMER_COUNT = 10;
        
        List<Thread> producers = new ArrayList<>();
        List<Thread> consumers = new ArrayList<>();
        
        BoundedBuffer buffer = new BoundedBuffer();
        
        for(int i = 0; i<PRODUCTOR_COUNT; ++i) {
            Thread p = new Thread(new Productor(buffer));
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
