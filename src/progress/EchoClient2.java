package progress;

import java.io.*;
import java.net.Socket;

public class EchoClient2 {
    public static void main(String[] args) {
        try {
            Socket sock=new Socket("127.0.0.1",7777);
            InputStream input=sock.getInputStream();
            BufferedReader in=new BufferedReader(new InputStreamReader(input));
            PrintStream out=new PrintStream(sock.getOutputStream());
            BufferedReader write=new BufferedReader(new InputStreamReader(System.in));
            while (true){
              String str=write.readLine();
              if(str.equals("exit"))
                  break;
              out.println(str);
              out.flush();
                System.out.println(in.readLine());
            }
            sock.close();
        }
        catch (IOException ioe){
            System.err.println(ioe);
        }
    }
}
