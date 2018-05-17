package BoundedBuffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class BoundedBuffer implements Buffer {
    Lock lock = new ReentrantLock();
    Condition condVar = lock.newCondition();
    private static final int BUFFER_SIZE = 5;
    private int count, in, out;
    private Object[] buffer;

    public BoundedBuffer() {
        count = 0;
        in = 0;
        out = 0;
        buffer = new Object[BUFFER_SIZE];
    }

    @Override
    public void insert(Object item) {
        lock.lock();
        try {
            try {
               while(count==BUFFER_SIZE)
                   condVar.await();
            } catch (InterruptedException e) {
            }


            ++count;
            buffer[in] = item;
            in = (in + 1) % BUFFER_SIZE;
            System.out.println(Thread.currentThread().getName()+"生产了"+item);
            condVar.signal();
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Object remove() {
        lock.lock();
        Object item=null;
        try {
            try {
                while (count == 0)
                    condVar.await();
            } catch (InterruptedException e) {

            }
            --count;
            item = buffer[out];
            System.out.println(Thread.currentThread().getName()+"消费了"+item);
            out = (out + 1) % BUFFER_SIZE;
            condVar.signal();

        }


        finally {
            lock.unlock();
        }
        return item;
    }
}
