package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author ：ZXF
 * @program: Socket+IO
 * @description: EchoServer
 * @date ：2021-03-08 19:25
 */

public class EchoServer {
    public static void main(String[] args) throws IOException {

        try (ServerSocket s = new ServerSocket(8189)) {
            // wait for client connection
            try (Socket incoming = s.accept()) {
                InputStream inputStream = incoming.getInputStream();
                OutputStream outputStream = incoming.getOutputStream();

                try (Scanner in = new Scanner(inputStream, "utf-8")) {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                    out.println("Hello! Enter Bye to exit.");

                    // echo client input
                    boolean done = false;
                    while (!done && in.hasNextLine()) {
                        String line = in.nextLine();
                        out.println("Echo:" + line);
                        if (line.trim().equalsIgnoreCase("BYE"))
                            done = true;
                    }
                }
            }
        }
    }
}
