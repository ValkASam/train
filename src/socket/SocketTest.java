package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Pattern;

/**
 * Created by Valk on 07.01.16.
 */
public class SocketTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Socket socket = new Socket("localhost",123); //предварительно запускаем ServerSocketTest.
        //или, указав ip адрес
        InetAddress ipAddress = InetAddress.getByName("127.0.0.1");
        Socket socket = new Socket(ipAddress,123);
        //
        new PrintStream(socket.getOutputStream()).println("Hellow !");
        Thread.sleep(2000);
        new PrintStream(socket.getOutputStream()).println("Hellow one more !");
        String str = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
        System.out.println(str);
        Thread.sleep(2000);
    }
}
