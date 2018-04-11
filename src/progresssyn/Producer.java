package progresssyn;

import java.util.Random;

public class Producer implements Runnable {
    private BoundedBuffer buf;

    public Producer(BoundedBuffer buf) {
        this.buf = buf;
    }

    @Override
    public void run() {
        while(true){
           Integer id=new Random().nextInt();
           Product newProduct=new Product(id.toString());
            try {
                buf.insert(newProduct);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
