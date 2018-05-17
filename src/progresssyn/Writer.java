package progresssyn;

import ReaderWriter.RWLock;

public class Writer implements Runnable {
    private RWLock db;
    public Writer(RWLock db){
        this.db=db;
    }
    public void run(){
        while(true){
            SleepUtilities.nap();
            db.acquireWriteLock();
            SleepUtilities.nap();
            db.releaseReadLock();
        }
    }
}
