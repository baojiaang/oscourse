package progress;

import java.util.concurrent.Semaphore;

public class BarberShop {
    static int cnt = 0;// 顾客
    static int MAX = 5;// 假设5张可供顾客理发的椅子
    static int busy = 0;
    static Semaphore mutex = new Semaphore(1);// 临界区互斥访问信号量(二进制信号量)，相当于互斥锁。

    public static void main(String args[]) throws InterruptedException {
        BarberShop bar = new BarberShop();
        for (int i = 1; i <= 20; i++) {// 假设一共有20个顾客来访
            new Thread(new Barber(bar, i)).start();
            Thread.sleep((int) (400 - Math.random() * 300));// 使得当前线程休眠 随机0-0.1s
        }
    }

    public synchronized boolean isFull() {
        if (cnt == MAX) {
            return true;
        }
        return false;
    }

    public synchronized boolean isEmpty() {
        if (cnt == 0) {
            return true;
        }
        return false;
    }

    public synchronized boolean isBusy() {
        if (busy == 1) {
            return true;
        }
        return false;
    }

    public void Gobar(int index) throws InterruptedException {

        System.out.println("顾客 " + index + " 来了");
        cnt++;
        // 判断是否满
        if (isFull()) {
            System.out.println("没有可供顾客等待的椅子了," + "顾客 " + index + " 离开了");
            cnt--;
        } else {
            if (busy == 1) {
                System.out.println("顾客" + index + " 正在等待理发师");
            }
            mutex.acquire();// 信号量减操作，防止其他进程再进入
            synchronized (this) {
                while (busy == 1) {
                    // 若有人在理发，则等待
                    wait();
                }
            }

            if (cnt == 1) {
                System.out.println("现在理发店只有顾客" + index + ",理发师是清醒的");
            }
            busy = 1;
            System.out.println("顾客" + index + " 正在理发");
            Thread.sleep(1000);
            System.out.println("顾客" + index + " 离开了");
            cnt--;
            mutex.release();// 信号量加操作
            synchronized (this) {
                busy = 0;
                notify();// 唤醒
            }
            if (cnt == 0) {
                System.out.println("没有顾客了，理发师开始睡觉");
            }
        }
    }
}

class Barber implements Runnable {
    BarberShop ob;
    int index;

    public Barber(BarberShop ob, int i) {
        this.ob = ob;
        index = i;
    }

    public void run() {
        // TODO Auto-generated method stub
        try {
            ob.Gobar(index);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
