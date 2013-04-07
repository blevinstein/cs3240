import java.util.*;
import java.io.*;

import lejos.nxt.*;
import lejox.nxt.comm.*;

public class Communicator implements Runnable {
    private Queue<Message> messageQueue;
    private NXTConnection connection;
    private InputStream is;
    private OutputStream os;
    private boolean terminateFlag;
    private boolean connected;

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
        return messageQueue.peek() != null;
    }

    // Retrieve oldest message in queue
    public Message getMessage() {
        return messageQueue.remove();
    }

    // Send message to base station
    public void sendMessage(Message m) {
        os.write(m.toString().getBytes());
    }

    // Main thread
    public void run() {
        StringBuffer stringBuffer = new StringBuffer();
        byte[] byteBuffer = new byte[1024];

        connection = Bluetooth.waitForConnection();
        connected = true;
        is = connection.openDataInputStream();
        os = connection.openDataOutputStream();
        terminateFlag = false;

        while(!terminateFlag) {
            // read into byte[] buffer
            int bytesRead = is.read(byteBuffer);
            if(bytesRead > 0) {
                // transfer from byte[] into StringBuffer
                stringBuffer.append(new String(byteBuffer, 0, bytesRead));
                // check for }
                // if found, this suggests that we just finished receiving a full message
                int endChar = stringBuffer.indexOf("}");
                if(endChar != -1) {
                    // check for matching {
                    int startChar = stringBuffer.indexOf("{");
                    if(startChar != -1 && startChar < endChar) {
                        // parse the message and add it to the queue
                        messageQueue.offer(new Message(stringBuffer.substring(startChar, endChar+1)));
                        stringBuffer.replace(startChar, endChar+1, "");
                        // clean out beginning of buffer
                        if(startChar > 0) {
                            stringBuffer.replace(0, startChar, "");
                        }
                    } else {
                        // delete malformed input from the buffer
                        stringBuffer.replace(0, endChar+1, "");
                        // TODO: log message about malformed input, send error response back to base station
                    }
                }
            }
        }

        // disconnect
        is.close();
        os.close();
        connection.close();
        connected = false;
    }
}
