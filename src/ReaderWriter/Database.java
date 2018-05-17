package ReaderWriter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Database implements RWLock {
    private int readerCount;
   private boolean isWriting;
    Lock lock = new ReentrantLock();
    Condition condVar = lock.newCondition();
    Condition writer=lock.newCondition();
    public Database(){
        readerCount=0;
       isWriting=false;
    }

    @Override
    public void acquireReadLock() {
        lock.lock();
        try {
            try {
                while(isWriting==true)
                    condVar.await();
            }
            catch (InterruptedException e){

            }
            readerCount++;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void acquireWriteLock() {
       lock.lock();
       try {
           try {
               while(readerCount>0||isWriting==true)
                   condVar.await();
           }
           catch (InterruptedException e){

           }
           isWriting=true;

       }
       finally {
           lock.unlock();
       }


    }

    @Override
    public void releaseReadLock() {
        lock.lock();
        --readerCount;
        if(readerCount==0)
            condVar.signal();
        lock.unlock();
    }

    @Override
    public void releaseWriteLock() {
        lock.lock();
        int readed=readerCount;
      isWriting=false;
       try {
           condVar.signalAll();

       }
       finally {
           lock.unlock();
       }
    }
}
