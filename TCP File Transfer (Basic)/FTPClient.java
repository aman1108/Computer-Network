import java.net.*;
import java.io.*;


//Client Program

class FTPClient{
    public static void main(String[] args){
        
        String fileName;
        File file;
        FileOutputStream fileOutputStream = null;
        
        
        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
    
        Socket socket = null;
        byte[] byteArray;
    
        try {
    
            socket = new Socket("localhost", 7313); 
            System.out.println("Connected..."); 

            inputStream = socket.getInputStream(); 
            
            dataInputStream = new DataInputStream(inputStream); 
            
            fileName = dataInputStream.readUTF(); //filename server is sending
            file = new File(fileName); 
            fileOutputStream = new FileOutputStream(file); 
            
            int bytesRead = 0; 
            byteArray = new byte[1024 * 10];

            while((bytesRead = inputStream.read(byteArray)) > -1){ 
                fileOutputStream.write(byteArray, 0, bytesRead); 
            }
            
            System.out.println("Received Successfully...");
            fileOutputStream.close(); 
            socket.close(); 

        } catch (IOException e) {
            System.err.println("Error 1");
            e.printStackTrace();
        }
    }

}