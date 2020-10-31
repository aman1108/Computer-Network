
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {

	public static void main(String[] args) throws Exception {
		DatagramSocket ds1 = new DatagramSocket(1234);
		byte b1[] = new byte[1024];
		DatagramPacket dp1 =new DatagramPacket(b1,b1.length);
		ds1.receive(dp1);
		String Str = new String(dp1.getData());
		System.out.println(Str);
		
	}

}