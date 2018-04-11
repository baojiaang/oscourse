package progress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class EchoThread extends Thread {
    private Socket client;
    PrintStream out;
    BufferedReader buf;
    public EchoThread(Socket client){
        this.client = client;
        try {
            out = new PrintStream(client.getOutputStream());
            buf = new BufferedReader(new InputStreamReader(client.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run(){
        try{
            while(true){
                int ch=0;
                ch=buf.read();
                if(ch!=-1)
                {
                    out.print((char)ch);
                    System.out.print((char)ch);
                    out.flush();
                }
                else
                    break;
            }
            out.close();
            client.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
