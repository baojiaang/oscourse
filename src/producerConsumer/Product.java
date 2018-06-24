package producerConsumer;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dell
 */
public class Product {
    private String content;
    
    public Product(String c) { this.content = c; }
    
    public void setContent(String c) { this.content = c; }
    public String getContent() { return this.content; }
    
    @Override
    public String toString() {
        return content;
    }
}