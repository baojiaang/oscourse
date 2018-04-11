package progress;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try {
            ServerSocket socket=new ServerSocket(7777);
            while(true){
                Socket client=socket.accept();
                BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintStream pout=new PrintStream(client.getOutputStream(),true);
                while(true){
//                    String get= in.readLine();
//                   if(get != null||"".equals(get))
//                   {  pout.println(get);
//                       System.out.println(get);
//                        pout.flush();
//                   }
//                   if(get.equals("exit"))
//                       break;
                        int ch=0;
                        ch=in.read();
                        if(ch!=-1)
                        {
                            pout.print((char)ch);
                            System.out.print((char)ch);
                            pout.flush();
                        }
                    else
                        break;
                }
                pout.close();
                 client.close();
            }
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }
}
