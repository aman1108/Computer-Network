import java.net.*;
import java.io.*;
import java.util.*;


class FTPServer{
    public static void main(String[] args){
        
        String fileName = null;
    
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;
        
        ServerSocket serverSocket = null;
        Socket client = null;
        Scanner scan = new Scanner(System.in);
        File file;
        byte[] bytes;
        
        try 
        {
            serverSocket = new ServerSocket(7313); //creating server
            System.out.println("FTP Server started...");
            client = serverSocket.accept(); //waiting until client get's connected
            System.out.println("Client Hi this is Aman's Server");
            outputStream = client.getOutputStream(); //get the output stream to send data to client
            
            dataOutputStream = new DataOutputStream(outputStream); //DataOutputStream is used to work with strings unlike output stream
            
            System.out.println("Enter file name to send");
            fileName = scan.nextLine(); 		//get the file name or it's absolute path
            file = new File(fileName);
            fileInputStream = new FileInputStream(file); 
		//fileInputStream to read the content of file
            int fileLength = (int) file.length();
            bytes = new byte[fileLength];

            fileInputStream.read(bytes, 0, bytes.length); //read the content of file and save it's bytes to byte Array
            dataOutputStream.writeUTF(file.getName()); //send user the file name on DataOutputStream
            outputStream.write(bytes, 0, bytes.length); //send file bytes to client
            outputStream.close(); //closing the stream
            scan.close(); //closing the stream
            System.out.println("Sent Successfully");
            serverSocket.close(); //closing the connection
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}