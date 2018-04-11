package progresssyn;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class Product {
    private String content;
    Mutex m=new Mutex();

    public Product(String s) {
        this.content=s;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Product{" +
                "content='" + content + '\'' +
                '}';
    }

    public void setContent(String content) {
        this.content = content;
    }
}
