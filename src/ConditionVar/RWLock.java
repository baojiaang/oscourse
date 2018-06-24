package ConditionVar;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RWLock {
    private int read_count;
    private ReentrantLock lock;
    private Condition writeCondition;

    public RWLock() {
        lock = new ReentrantLock();
        writeCondition = lock.newCondition();
    }

    public void acquireReadLock() {
        //如果有写者占有了lock，那么所有的读者都会阻塞在这里
        lock.lock();
        read_count ++;
        lock.unlock();
    }

    public void releaseReadLock() {
        lock.lock();
        read_count --;

        //如果读者的数量降为0，那么释放写者
        if(read_count == 0) {
            //因为Writer最多只能有一个获得写锁，所以这里只唤醒一个
            writeCondition.signal();
        }

        lock.unlock();
    }

    public void acquireWriteLock() {
        lock.lock();
        if(read_count > 0) {
            try {
                writeCondition.await();
            } catch (InterruptedException e) {
                lock.unlock();
            }
        }
    }

    public void releaseWriteLock() {
        //写者优先：如果还有写者在排队，那么唤醒下一个写者
        if(read_count == 0)
            writeCondition.signal();

        lock.unlock();
    }
}