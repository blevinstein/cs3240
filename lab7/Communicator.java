import java.util.*;
import java.io.*;

import lejos.nxt.*;
import lejos.nxt.comm.*;

public class Communicator implements Runnable {
    private Queue<Message> messageQueue;
    private NXTConnection connection;
    private InputStream is;
    private OutputStream os;
    private boolean terminateFlag;
    private boolean connected;

    public Communicator() {
        messageQueue = new Queue<Message>();
    }

    // Return true if connected
    public boolean isConnected() {
        return connected;
    }

    // Start listening for messages
    public void start() {
        (new Thread(this)).start();
    }

    // Stop listening for messages
    public void stop() {
        terminateFlag = true;
    }

    // Return true if there is a message in the queue
    public boolean hasMessage() {
        return !messageQueue.empty();
    }

    // Retrieve oldest message in queue
    public Message getMessage() {
        return (Message)messageQueue.pop();
    }

    // Send message to base station
    public void sendMessage(Message m) {
        try{
            os.write(m.toString().getBytes());
            os.flush();
        } catch(IOException e) {
            System.out.println("Failed to send message!");
        }
    }

    // Main thread
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
