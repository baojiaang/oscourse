package ReaderWriter;

import progresssyn.SleepUtilities;

public class Reader implements Runnable {
    private RWLock db;
    public Reader(RWLock db){
        this.db=db;
    }
    public void run(){
        while(true){
            SleepUtilities.nap();
            db.acquireReadLock();
            SleepUtilities.nap();
            db.releaseReadLock();
        }
    }
}
