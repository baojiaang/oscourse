package BoundedBuffer;


import progresssyn.BoundedBuffer;
import progresssyn.Product;
import progresssyn.SleepUtilities;

import java.util.Date;
import java.util.Random;

public class Producer implements Runnable {
    private Buffer buffer;
    public Producer(Buffer buffer){
        this.buffer=buffer;
    }
    public void run(){
        Date message;
        while(true){
            SleepUtilities.nap();
            message=new Date();
            buffer.insert(message);
        }
    }
}

