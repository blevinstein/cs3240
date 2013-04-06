import java.io.*;
//import lejos.pc.*;
import lejos.pc.comm.*;

public class BaseCommTest {
    public static void main(String[] args) throws IOException, NXTCommException {
        NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
        NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "LEAD6", "00:16:53:13:F6:A4");
        nxtComm.open(nxtInfo);

        OutputStream os = nxtComm.getOutputStream();
        InputStream is = nxtComm.getInputStream();

        for(int i=0; i<100; i++) {
            int n = i;
            System.out.println("write "+n);
            os.write(n);
        }
        for(int i=0; i<100; i++) {
            os.flush();
            System.out.println("read "+is.read());
        }

        os.close();
        is.close();
    }
}
