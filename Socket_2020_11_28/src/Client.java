import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
//        String request = "what is fuck?";
//        if (args.length > 0) {
//            request = args[0];
//        }

        Socket socket = new Socket("127.0.0.1", 9898);

        OutputStream os = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(os, "utf-8"));

        Scanner conslon = new Scanner(System.in);
        String request = conslon.nextLine();

        writer.println(request);
        writer.flush();

        InputStream is = socket.getInputStream();
        Scanner sc = new Scanner(is, "utf-8");

        String response = sc.nextLine();
        System.out.println(response);

        socket.close();
    }
}
