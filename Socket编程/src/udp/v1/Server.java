package udp.v1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws SocketException {
        // 1. 创建 Server的 socket
        //      内部会进行本地 IP + port 的绑定
        //      例子：饭店开张，提供一个大家都认识的 IP + port
        //      ip虽然没有给，内部会帮忙处理，把所有的 IP都绑定
        try (DatagramSocket socket = new DatagramSocket(9939)) {

            while (true) {
                action(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param socket
     * @throws IOException
     */
    private static void action (DatagramSocket socket) throws IOException {
        // 1.读取客户端发来的请求

        // 1.1 准备一个字节数组，用来一会存放要读到的数据
        byte[] receiveBuffer = new byte[8192];
        // 1.2 把 buffer 封装成 datagram
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, 0, 8192);
        // 1.3 读取请求
        socket.receive(receivePacket);
        // 1.4 reveice返回就有人发请求了
        System.out.println(
                Arrays.toString(
                        Arrays.copyOfRange(receiveBuffer, 0, receivePacket.getLength())
                )
        );
        // 2. 进行服务 -- 根据请求，处理业务，并生成响应

        // 3. 发送响应回去

    }


}
