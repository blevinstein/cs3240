import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

// http://lejos.sourceforge.net/nxt/nxj/tutorial/Communications/Communications.htm
public class RobotCommTest {
    public static void main(String[] args) {
        while(true) {
            LCD.drawString("Waiting...",0,0);
            NXTConnection connection = Bluetooth.waitForConnection();
            LCD.clear();
            LCD.drawString("Connected",0,0);

            DataInputStream dis = connection.openDataInputStream();
            DataOutputStream dos = connection.openDataOutputStream();

            for(int i=0; i<100; i++) {
                try {
                    int n = dis.readInt();
                    LCD.drawInt(n, 7, 0, 1);
                    dos.writeInt(-n);
                    dos.flush();
                } catch (IOException e) {
                    LCD.drawString("Loop exception",0,0);
                }
            }

            try {
                dis.close();
                dos.close();
            } catch (IOException e) {
                LCD.drawString("Close exception",0,0);
            }

            LCD.clear();
            LCD.drawString("Closing...",0,0);

            connection.close();
            LCD.clear();
        }
    }
}
