import java.io.*;
import java.net.*;
import java.util.Random;

/*
 * java Client <send rate> <host> <port> <local file> <call on server>
 * eg Java Client localhost 9999  
 */
class Client {

    private static int totalTransferred = 0;
    private static final double previousTimeElapsed = 0;
    private static final int previousSize = 0;
    private static int sendRate = 0;
    private static String hostName;
    private static int port;
    private static String fileName;
    private static String destFileName;
    private static StartTime timer = null;
    private static int retransmitted = 0;

    public static void main(String args[]) throws Exception {

        sendRate = Integer.parseInt("99");
        setLossRate(sendRate);
        hostName = args[0];
        setHostname(hostName);
        port = Integer.parseInt(args[1]);
        setPort(port);
        fileName = "IIITPune.txt";
        setFileName(fileName);
        destFileName = "IIITPune.txt";
        setDestFile(destFileName);

	System.out.println("Sending the file");
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(getHostname());

        String saveFileAs = getDestFileName();
        byte[] saveFileAsData = saveFileAs.getBytes();

        DatagramPacket fileStatPacket = new DatagramPacket(saveFileAsData, saveFileAsData.length, address, getPort());
        socket.send(fileStatPacket);

        File file = new File(getFileName());
        
        byte[] fileByteArray = new byte[(int) file.length()];

        startTimer();
        beginTransfer(socket, fileByteArray, address);
        socket.close();

    }

    
    private static void beginTransfer(DatagramSocket socket, byte[] fileByteArray, InetAddress address) throws IOException {

        int sequenceNumber = 0;
        boolean flag;
        int ackSequence = 0;

        for (int i = 0; i < fileByteArray.length; i = i + 1021) {
            sequenceNumber += 1;
            // Create message
            byte[] message = new byte[1024];
            message[0] = (byte) (sequenceNumber >> 8);
            message[1] = (byte) (sequenceNumber);

            if ((i + 1021) >= fileByteArray.length) {
                flag = true;
                message[2] = (byte) (1);
            } else {
                flag = false;
                message[2] = (byte) (0);
            }

            if (!flag) {
                System.arraycopy(fileByteArray, i, message, 3, 1021);
            } else { 
                System.arraycopy(fileByteArray, i, message, 3, fileByteArray.length - i);
            }

            int randomInt = shouldThisPacketBeSent();

            DatagramPacket sendPacket = new DatagramPacket(message, message.length, address, getPort());

            if (randomInt <= getLossRate()) {
                socket.send(sendPacket);
            }

            totalTransferred = gatherTotalDataSentSoFarStatistic(sendPacket);

            if (Math.round(totalTransferred / 1000) % 50 == 0) {
                PrintFactory.printCurrentStatistics(totalTransferred, previousSize, timer, previousTimeElapsed);
            }

            System.out.println("Sent: Sequence number = " + sequenceNumber);

            // For verifying the the packet
            boolean ackRec;

            // The acknowledgment is not correct
            while (true) {
                // Create another packet by setting a byte array and creating
                // data gram packet
                byte[] ack = new byte[2];
                DatagramPacket ackpack = new DatagramPacket(ack, ack.length);

                try {
                    // set the socket timeout for the packet acknowledgment
                    socket.setSoTimeout(50);
                    socket.receive(ackpack);
                    ackSequence = ((ack[0] & 0xff) << 8)
                            + (ack[1] & 0xff);
                    ackRec = true;

                }
                // we did not receive an ack
                catch (SocketTimeoutException e) {
                    System.out.println("Socket timed out waiting for the ");
                    ackRec = false;
                }

                // everything is ok so we can move on to next packet
                // Break if there is an acknowledgment next packet can be sent
                if ((ackSequence == sequenceNumber)
                        && (ackRec)) {
                    System.out.println("Ack received: Sequence Number = "
                            + ackSequence);
                    break;
                }

                // Re send the packet
                else {
                    socket.send(sendPacket);
                    System.out.println("Resending: Sequence Number = "
                            + sequenceNumber);
                    // Increment retransmission counter
                    retransmitted += 1;
                }
            }
        }
    }

    private static int gatherTotalDataSentSoFarStatistic(DatagramPacket sendPacket) {
        totalTransferred = sendPacket.getLength() + totalTransferred;
        totalTransferred = Math.round(totalTransferred);

        return totalTransferred;
    }

    private static int shouldThisPacketBeSent() {
        Random randomGenerator = new Random();

        return randomGenerator.nextInt(100);
    }

    private static void startTimer() {
        timer = new StartTime();
    }

    private static int getLossRate() {
        return sendRate;
    }

    private static void setLossRate(int passed_loss_rate) {
        sendRate = passed_loss_rate;
    }

    private static int getPort() {
        return port;
    }

    private static void setPort(int passed_port) {
        port = passed_port;
    }

    private static String getFileName() {
        return fileName;
    }

    private static void setFileName(String passed_file_name) {
        fileName = passed_file_name;
    }

    private static void setDestFile(String passed_dest_file) {
        destFileName = passed_dest_file;
    }

    private static String getDestFileName() {
        return destFileName;
    }

    private static String getHostname() {
        return hostName;
    }

    private static void setHostname(String passed_host_name) {
        hostName = passed_host_name;
    }
}