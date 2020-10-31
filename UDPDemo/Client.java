

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



public class Client {
	public static void main(String[] args) throws Exception {
		DatagramSocket ds = new DatagramSocket();
		InetAddress ia = InetAddress.getLocalHost();
		byte b[]= new byte[1024];
		String Str = "Hello World";
		b = Str.getBytes();
		DatagramPacket dp = new DatagramPacket(b,b.length,ia,1234);
		ds.send(dp);
		
		
		
	}

}