package udp.v1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        try (DatagramSocket socket = new DatagramSocket()) {
            while (true) {
                System.out.print("please input something:>");
                sc.nextLine();

                byte[] sendBuffer = { 9, 5, 2, 7 };
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, 0,
                        sendBuffer.length, InetAddress.getByName("127.0.0.1"), 9939);

                socket.send(sendPacket);
            }

        }
    }
}
