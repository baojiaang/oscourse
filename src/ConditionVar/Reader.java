package ConditionVar;

import java.util.Date;
import java.util.Random;

public class Reader implements Runnable{
    private RWLock rwLock;
    private int readerID;

    public Reader(RWLock rwLock, int readerID) {
        this.rwLock = rwLock;
        this.readerID = readerID;
    }

    @Override
    public void run() {
        while(true) {
            rwLock.acquireReadLock();
            System.out.println(new Date().toString() + ": Reader " + readerID + " reading." );
            rwLock.releaseReadLock();

            int sleep_time = new Random().nextInt()%10;
            try {
                Thread.sleep(Math.abs(sleep_time)*1000);
            } catch (InterruptedException e) {
                break;
            }
        }

    }
}
