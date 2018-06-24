package ConditionVar;

import java.util.Date;
import java.util.Random;

public class Writer implements Runnable{
    private RWLock rwLock;
    private int writerID;

    public Writer(RWLock rwLock, int writerID) {
        this.rwLock = rwLock;
        this.writerID = writerID;
    }

    @Override
    public void run() {
        while(true) {
            rwLock.acquireWriteLock();
            System.out.println(new Date().toString() + ": Writer " + writerID + " writing." );
            rwLock.releaseWriteLock();

            int sleep_time = new Random().nextInt()%10;
            try {
                Thread.sleep(Math.abs(sleep_time)*1000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
