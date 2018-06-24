package producerConsumer;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dell
 */
public class Consumer implements Runnable {
    private BoundedBuffer buffer;
    
    public Consumer(BoundedBuffer buf) {
        this.buffer = buf;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Product newProduct = buffer.get();

                Thread.sleep(1000);
            }catch(InterruptedException e) {}
        }
    }    
}
