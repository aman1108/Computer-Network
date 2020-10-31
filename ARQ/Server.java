import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;

class Server{
	private static final int BUFFER_SIZE = 1024;
	private static final int PORT = 6789;

	public static void main(String[] args) throws IOException {
		// Create a server socket
		DatagramSocket serverSocket = new DatagramSocket( PORT );

		// Set up byte arrays for sending/receiving data
        	byte[] receiveData = new byte[ BUFFER_SIZE ];
        	byte[] dataForSend = new byte[ BUFFER_SIZE ];

        // Infinite loop to check for connections 
        while(true){

        	// Get the received packet
        	DatagramPacket received = new DatagramPacket( receiveData, receiveData.length );
          	serverSocket.receive( received );

          	// Get the message from the packet
          	int message = ByteBuffer.wrap(received.getData( )).getInt();

                System.out.println("FROM CLIENT: " + message);

                // Get packet's IP and port
                InetAddress IPAddress = received.getAddress();
                int port = received.getPort();

                // Convert message to uppercase 
                dataForSend = ByteBuffer.allocate(4).putInt( message ).array();
  
                // Send the packet data back to the client
                DatagramPacket packet = new DatagramPacket( dataForSend, dataForSend.length, IPAddress, port );
                serverSocket.send( packet ); 
        
       	}

	}
}