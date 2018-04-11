package progresssyn;

public class Consumer implements Runnable {
    private BoundedBuffer buf;
    public Consumer(BoundedBuffer buf){
        this.buf=buf;
    }
    public void run(){
        while(true){
            try {
                Product newProduct= (Product) buf.get();
                Thread.sleep(1000);
            }catch (InterruptedException e){

            }
        }
    }
}
