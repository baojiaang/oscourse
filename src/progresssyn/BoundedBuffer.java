package progresssyn;

import java.util.concurrent.Semaphore;

public class BoundedBuffer {
    private static final int BUFFER_SIZE=10;
    private Object[] buffer;
    private int inPos=0,outPos=0;
    private Semaphore lock,freeCount,productCount;
    public BoundedBuffer(){
        buffer=new Product[BUFFER_SIZE];
        lock=new Semaphore(1); //  0 1 信号量 作为锁
        freeCount=new Semaphore(BUFFER_SIZE);
        productCount=new Semaphore(0);
    }
    public void insert(Object item) throws InterruptedException {
//        int cur=freeCount.availablePermits();
       // System.out.println("now bufffer has"+cur);
//        if(cur==0)
//            System.out.println("buffer is full");
        freeCount.acquire();
        lock.acquire();
        System.out.println(Thread.currentThread().getName()+"生产了小米手机 ID"+item.toString());
        buffer[inPos]=item;
        inPos=(inPos+1)%BUFFER_SIZE;
        lock.release();
        productCount.release();
    }
    public Object get() throws InterruptedException {
//        int cur=productCount.availablePermits();
//        if(cur==0)
//            System.out.println("雷军耍猴中");
        productCount.acquire();
        lock.acquire();
        Object item=buffer[outPos];
        outPos=(outPos+1)%BUFFER_SIZE;
        System.out.println(Thread.currentThread().getName()+"买到了小米手机 ID"+item.toString());
        lock.release();
        freeCount.release();
        return item;
    }
}
