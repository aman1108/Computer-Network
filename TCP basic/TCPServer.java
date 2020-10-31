import java.io.*;
import java.net.*;
import java.util.*;


class TCPServer{
    public static void main(String[] args){
        ServerSocket server;
        Socket client;
        
        OutputStream outputStream;
        DataOutputStream dataOutputStream;

        Calendar calendar;
        
        try{
            server = new ServerSocket(7313);
	    //System.out.println("Aman");
            System.out.println("Server started...");
            client = server.accept();
            
            outputStream = client.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("Hi from server. We provide time service.");

            calendar = Calendar.getInstance();
            dataOutputStream.writeUTF("Time: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) 
            + ":" + calendar.get(Calendar.SECOND));
            server.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}