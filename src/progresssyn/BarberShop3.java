package progresssyn;

import java.util.concurrent.Semaphore;

public class BarberShop3 {
    private Semaphore lock,customer,barber;
    int waiting=0;
    int chairs=5;
    boolean isBusy=false;
    public BarberShop3(){
        lock=new Semaphore(1);
        customer=new Semaphore(0);
        barber=new Semaphore(0);
    }
    public synchronized void barber() throws InterruptedException {
        lock.acquire();//
        while(waiting==0)
            wait();
        isBusy=true;
        waiting--;
        chairs++;
        SleepUtilities.nap();
        isBusy=false;
        lock.release();
    }
    public synchronized void customer(int i) throws InterruptedException {
         lock.acquire();
        System.out.println("顾客"+i+"来了");
            waiting++;
          notify();
            if(waiting>chairs){
                waiting--;
                System.out.println("顾客"+i+"人满离开");
                lock.release();

            }
            else {
                chairs--;
               if(isBusy==true)
                   System.out.println("顾客"+i+"等待");
               while(isBusy==true)
                   wait();
                System.out.println("顾客"+i+"理发完成离开");

            }
    }

    public static void main(String[] args) {
        BarberShop3 b=new BarberShop3();
        Thread barber=new Thread(new Barber3());
        barber.start();
        for(int i=0;i<100;i++){
            new Thread(new Customer3(b,i)).start();
            SleepUtilities.nap();
        }
    }

}
class Barber3 implements Runnable{
   BarberShop3 b =new BarberShop3();
    @Override
    public void run() {
        try {
            b.barber();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class Customer3 implements Runnable{
    BarberShop3 b;
    int index;
    public Customer3(BarberShop3 b,int index){
        this.b=b;
        this.index=index;
    }
    @Override
    public void run() {
        try {
            b.customer(index);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
