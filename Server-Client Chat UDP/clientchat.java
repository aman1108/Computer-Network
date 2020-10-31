//Client.java
import java.net.DatagramSocket;
import java.net.DatagramPacket; 
import java.io.IOException;
import java.util.Scanner;
import java.net.InetAddress;



public class clientchat{

    public static void main(String args[]){
	
        try{
	    int portno=Integer.parseInt(args[1]);	//8080
	    String ipadd=args[0];	//"127.0.0.1"

            DatagramSocket dsock = new DatagramSocket();
            byte[] arr = new byte[1024];

            DatagramPacket dpack_send ,dpack_recv ;
            
            Scanner inp = new Scanner(System.in);
	    String clstr=new String("exit");
	    String arr1;

            while(true){
                
                clearBytes(arr);            // to remove data from previous operation from 
                System.out.print("Enter Message : ");
		arr1=inp.nextLine();
		
                arr = (arr1).getBytes();
                dpack_send =  new DatagramPacket( arr , arr.length ,  InetAddress.getByName(ipadd) , portno );
                dsock.send(dpack_send);
		clearBytes(arr);            // to remove data from previous operation from 
		if (arr1.equals(clstr))
			break;


                dpack_recv = new DatagramPacket( arr , arr.length );
                dsock.receive(dpack_recv);
		arr1=new String(arr);
		if (arr1.equals(clstr))
			break;
                System.out.println("Server : " + arr1 );

            }

        }catch(IOException e){
            System.out.println("Error : " + e );
        }

    }

    public static void clearBytes(byte[] arr){
        for( int i = 0 ; i < arr.length ; i ++ )
            arr[i] = '\0' ;
    }

}