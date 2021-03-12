package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：ZXF
 * @program: Socket+IO
 * @description: server
 * @date ：2021-03-10 23:57
 */

public class Server1 {

    static public List<Client> list = new ArrayList<Client>();

    static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        //创建一个ServerSocket监听端口
        try (ServerSocket server = new ServerSocket(5678)) {

            while (true) {                    // 利用死循环不停的监听端口

                Socket s = server.accept();// 利用Socket服务器的accept()方法获取客户端Socket对象。
                addClient(s);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 添加客户端
    private static void addClient(Socket s) {
        String name;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            name = in.readLine();
            Client c = new Client(name, s);// 创建客户端处理线程对象
            System.out.println(name);
            list.add(c);

            cachedThreadPool.execute(c);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Client implements Runnable {

        String name;        // 客户端名字
        Socket s = null;    // 保存客户端Socket对象

        BufferedReader in;
        PrintWriter out;

        Client(String name, Socket s) {

            this.s = s;
            this.name = name;

            try {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(s.getOutputStream());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String str = in.readLine();
                    for (int j = 0; j < list.size(); j++) {
                        Server1.Client c = list.get(j);
                        if (c != this) {
                            System.out.println(str);
                            c.send(str+"-|1|2|-"+name);
                        }
                    }

                    if (str.equals("end"))
                        break;
                }

                try {
                    s.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        public void send(String str) throws IOException {
            out.println(str);
            out.flush();
        }
    }
}
