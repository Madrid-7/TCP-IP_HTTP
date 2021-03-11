package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ：ZXF
 * @program: Socket+IO
 * @description: client
 * @date ：2021-03-10 23:39
 */

public class Client1 {
    public static String name = null;
    static Socket socket = null;

    static BufferedReader in;
    static PrintWriter out;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("输入用户名:>");
        name = sc.nextLine();

        try {
            socket = new Socket("127.0.0.1", 5678);

            System.out.println("已连接服务器>");

            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread receive = new Thread(new receiveThread());
            receive.start();

            out.println(name);
            out.flush();

            String msg;

            while (true) {
//                System.out.print("你说:>");
                msg = sc.nextLine();

                out.println(msg);
                out.flush();

                if (msg.equalsIgnoreCase("end"))
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class receiveThread implements Runnable{

        @Override
        public void run() {
            try {
                while (socket!=null) {

                    String str = in.readLine();
                    String message = str.split("-\\|1\\|2\\|-")[0];
                    String name = str.split("-\\|1\\|2\\|-")[1];
                    System.out.println(name + ":>" + message);

                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
