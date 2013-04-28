import java.util.*;
import java.io.*;

import lejos.nxt.*;
import lejos.nxt.comm.*;
/**
 * Class which provides for communication with the base station.
 * Each instance of a Communicator can start a separate thread for the purpose
 * of receiving and buffering incoming messages.
 */
public class Communicator implements Runnable {
    private Queue<Message> messageQueue;    // queue of messages received from the base station
    private NXTConnection connection;       // connection to the base station
    private InputStream is;                 // input stream from the base station
    private OutputStream os;                // output stream to the base station
    private boolean terminateFlag;          // this flag is used internally to signal that the receiving thread should terminate
    private boolean connected;              // this flag indicates whether there is an active connection to the base station

    /**
     * Constructs a standard Communicator object for communicating with the base station.
     */
    public Communicator() {
        messageQueue = new Queue<Message>();
    }

    /**
     * @return true iff there is an active connection to the base station.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Starts a separate thread to receive and buffer input from the base station.
     */
    public void start() {
        (new Thread(this)).start();
    }

    /**
     * If connected to the base station, this will terminate the connection and stop the receiving thread.
     */
    public void stop() {
        terminateFlag = true;
    }

    /**
     * @return true iff there is a message in the queue
     */
    public boolean hasMessage() {
        return !messageQueue.empty();
    }

    /**
     * @return The oldest message in the message queue.
     */
    public Message getMessage() {
        return (Message)messageQueue.pop();
    }

    /**
     * Sends a message to the base station.
     * @param m The message to be sent.
     */
    public void sendMessage(Message m) {
        try{
            os.write(m.toString().getBytes());
            os.flush();
        } catch(IOException e) {
            System.out.println("Failed to send message!");
        }
    }

    /**
     * This is the main method of the separate thread which buffers input.
     * In general, this method should not be called from methods outside Communicator.
     */
    public void run() {
        String stringBuffer = "";
        byte[] byteBuffer = new byte[1024];

        connection = Bluetooth.waitForConnection();
        connected = true;
        is = connection.openDataInputStream();
        os = connection.openDataOutputStream();
        terminateFlag = false;

        while(!terminateFlag) {
            // read into byte[] buffer
            int bytesRead;
            try {
                bytesRead = is.read(byteBuffer);
            } catch (IOException e) {
                bytesRead = 0;
            }
            if(bytesRead > 0) {
                // transfer from byte[] into StringBuffer
                stringBuffer += new String(byteBuffer, 0, bytesRead);
                // check for }
                // if found, this suggests that we just finished receiving a full message
                int endChar = stringBuffer.indexOf("}");
                if(endChar != -1) {
                    // check for matching {
                    int startChar = stringBuffer.indexOf("{");
                    if(startChar != -1 && startChar < endChar) {
                        // parse the message and add it to the queue
                        Message messageRead = new Message(stringBuffer.substring(startChar, endChar+1));
                        messageQueue.push(messageRead);
                        Message ack = new Message(messageRead.getSeqNum());
                        ack.pairs.add(new String[]{"ack", null});
                        sendMessage(ack);
                    }

                    // clean command up to } off stringBuffer
                    if (endChar == stringBuffer.length() - 1) {
                      stringBuffer = "";
                    } else {
                      stringBuffer = stringBuffer.substring(endChar + 1);
                    }
                }
            }
        }

        // disconnect
        try {
            is.close();
            os.close();
        } catch(IOException e) {
            // TODO: not fail silently
        }
        connection.close();
        connected = false;
    }
}
