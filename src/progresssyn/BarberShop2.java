package progresssyn;

import java.util.concurrent.Semaphore;

public class BarberShop2 {
    static private Semaphore lock;
    private int customerNum,chairs;
    private boolean busy;

    public BarberShop2(){
        lock=new Semaphore(1);
        customerNum=0;
        chairs=5;
        busy=false;
    }
    public  void customer(int index) throws InterruptedException {

        if(customerNum==0){
            System.out.println("sleeping");
        }
        System.out.println("customer"+index+"coming");
        customerNum++;
        if(isFull()){
            System.out.println("no seat customer" + index + "leaving");
            customerNum--;
        }
        else {
            if(busy==true){
                System.out.println("customer" + index + "waiting");
            }
            lock.acquire();
            synchronized (this){
                while(busy==true)
                    wait();
            }

            busy=true;
            System.out.println("customer"+index+"is cutting hair");
            Thread.sleep(1000);
            System.out.println("customer work done"+index+"leaving");
            customerNum--;
            lock.release();
            synchronized (this){
                busy=false;
                notify();
            }
            if (customerNum == 0) {
                System.out.println("no customer  barber is sleeping");
            }

        }
    }

    public synchronized boolean isFull(){
        if(customerNum==chairs)
        {
            return true;
        }
        return false;
    }
    public synchronized boolean isBusy(){
        if(busy==true)
            return true;
        return false;
    }


    public static void main(String[] args) throws InterruptedException {
        BarberShop2 b2=new BarberShop2();
        for(int i=0;i<100;i++){
            new Thread(new BarberAndCustomer(b2,i)).start();
          //  t.start();
            Thread.sleep((long) (400 - Math.random() * 300));// 使得当前线程休眠 随机0-0.1s
        }
    }
}
class BarberAndCustomer implements Runnable{
    BarberShop2 b2;
    int index;
    public BarberAndCustomer(BarberShop2 b2,int index){
        this.index=index;
        this.b2=b2;
    }
    public void run(){
        try {
            b2.customer(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
