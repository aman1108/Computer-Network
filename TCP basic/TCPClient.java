import java.io.*;
import java.net.*;

class TCPClient{
    public static void main(String[] args){
        Socket client;
        
        InputStream inputStream;
        DataInputStream dataInputStream;
        
        try{
            client = new Socket("localhost", 7313);
            inputStream = client.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            System.out.println(dataInputStream.readUTF());
            System.out.println(dataInputStream.readUTF());
            client.close();
        }catch(IOException e){
            System.out.println(e);
        }
        
    }
}