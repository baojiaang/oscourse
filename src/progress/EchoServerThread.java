package progress;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServerThread {
    public static void main(String[] args) {
       ExecutorService executor=Executors.newCachedThreadPool();
      //  ExecutorService executor= Executors.newFixedThreadPool(4);
        ServerSocket server;
        try {
            server = new ServerSocket(7777);
            Socket client = null;
            while(true){
                System.out.println("服务器端等待客户端发起连接请求");
                client = server.accept();
                System.out.println("客户端向服务器端发起了连接请求,且连接成功");
                executor.execute(new EchoThread(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
