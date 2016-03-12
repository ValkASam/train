package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Valk on 07.01.16.
 */
public class ServerSocketTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(123);
        Socket socket = serverSocket.accept(); //тут будем ждать подключения. Отдельно запускаем SocketTest - видим, как сдесь получаем сообщение
        BufferedReader stream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println(stream.readLine());
        System.out.println("Ждем нового сообщения ... ");
        String str = stream.readLine();
        System.out.println(str);
        //
        new PrintStream(socket.getOutputStream()).println("Hi !");
        Thread.sleep(1000);
    }
}
