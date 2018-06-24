package producerConsumer;

import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dell
 */
public class BoundedBuffer {
    private final int BUFFER_SIZE = 10;
    private int inPos = 0;
    private int outPos = 0;
    private Product[] buffer;
    Semaphore lock;
    Semaphore free_count;
    Semaphore product_count;
    
    public BoundedBuffer() {
        buffer = new Product[BUFFER_SIZE];
        
        free_count = new Semaphore(BUFFER_SIZE);
        product_count = new Semaphore(0);
        
        lock = new Semaphore(1);
    }
    
    public void put(Product newProduct) throws InterruptedException {
        free_count.acquire();
        lock.acquire();
        
        System.out.println(Thread.currentThread().getName() + " put Product:" + newProduct.toString());
        
        buffer[inPos] = newProduct;
        inPos = (inPos + 1)% BUFFER_SIZE;
        
        lock.release();
        product_count.release();
    }
    
    public Product get() throws InterruptedException {
        product_count.acquire();
        lock.acquire();
        
        Product retProd = buffer[outPos];
        outPos = (outPos + 1)%BUFFER_SIZE;
        
        System.out.println(Thread.currentThread().getName() + " get Product:" + retProd.toString() );
        
        lock.release();
        free_count.release();
        
        return retProd;
    }
}
