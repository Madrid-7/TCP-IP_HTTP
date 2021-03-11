import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author ：ZXF
 * @program: Socket+IO
 * @description: SocketTest
 * @date ：2021-03-08 17:32
 */

public class Test {
    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);

            InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
            System.out.println(Arrays.toString(addresses));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
