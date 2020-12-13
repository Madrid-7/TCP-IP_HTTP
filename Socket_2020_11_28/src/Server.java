import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {


    private static class ServiceMan extends Thread {
        private Socket socket;

        ServiceMan (Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {

                //这里通过建立连接后，得到一个通信用的 Socket
                //每有一个 Client 连接上来，就会有一个 Socket
                //Socket socket = Socket.accept();

                //获取输入流
                InputStream is = socket.getInputStream();
                //封装成 Scanner
                Scanner scanner = new Scanner(is, "UTF-8");
                //使用 \r\n 进行分割的方式，读取一个请求
                String request = scanner.nextLine(); //nextLine已经把 \r\n 去掉了
                System.out.println("收到：" + request);

                //业务处理
                String response = request;

                //发送响应，也要使 \r\n 跟在后边，进行分割
                OutputStream os = socket.getOutputStream();
                //封装成 PrintWriter
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "UTF-8"));

                //发送请求
                writer.println(response);  //println 会自己在后面加 \r\n
                writer.flush();

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {

        //创建一个Server socket
        ServerSocket serverSocket = new ServerSocket(9898);
        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        //循环处理业务
        while (true) {
            Socket socket = serverSocket.accept();

            /*
            //交给线程去处理
            new ServiceMan(socket).start();
            */

            threadPool.execute(new ServiceMan(socket));
        }
    }
}
