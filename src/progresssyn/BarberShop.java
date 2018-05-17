package progresssyn;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class BarberShop {
    private Semaphore lock,customer,barber;
    int waiting=0;
    int chairs=5;
    public BarberShop(){
        lock=new Semaphore(1);
        customer=new Semaphore(0);
        barber=new Semaphore(0);
    }
    public void barber() throws InterruptedException {
        customer.acquire();
        lock.acquire();
            chairs++;
        barber.release();
       SleepUtilities.nap();
        lock.release();
        System.out.println("working done");
    }


    public void customer(int i) throws InterruptedException {
            lock.acquire();
            if(chairs>0){
                System.out.println("customer"+i+"sitting");
                chairs--;
                customer.release();
                lock.release();
                barber.acquire();
            }
            else{
                System.out.println("customer"+i+"go away");
                lock.release();
                Thread.interrupted();
            }
    }

    public static void main(String[] args) {
        BarberShop b=new BarberShop();

     Thread barber=new Thread(new Barber(b));
        ArrayList<Thread> a=new ArrayList<>();
     barber.start();
     for(int i=0;i<100;i++) {
         Thread customer = new Thread(new Customer(b,i));
         customer.start();
         SleepUtilities.nap();
     }
    }
}
class Barber implements Runnable{
    BarberShop b;
    int index;
    public Barber(BarberShop b){
     this.b=b;
    }
    @Override
    public void run() {
        while(true){
            try {
                b.barber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Customer implements Runnable{
    BarberShop b;
    int index;
    public Customer(BarberShop b,int index){
      this.b=b;
      this.index=index;
    }
    public void run(){
        while(true){
            try {
                b.customer(index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
