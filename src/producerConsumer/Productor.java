package producerConsumer;

import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dell
 */
public class Productor implements Runnable{
    private BoundedBuffer buffer;
    
    public Productor(BoundedBuffer buf) {
        this.buffer = buf;
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Integer randVal = new Random().nextInt();
                Product newProduct = new Product(randVal.toString());
                buffer.put(newProduct);

                Thread.sleep(1000);
            }catch(InterruptedException e) {}
        }
    }
}
