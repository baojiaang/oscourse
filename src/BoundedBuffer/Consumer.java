package BoundedBuffer;


import progresssyn.SleepUtilities;

import java.util.Date;

public class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }
    public void run(){
        Date message;
        while(true){
            SleepUtilities.nap();
            message=(Date)buffer.remove();
        }
    }
}

