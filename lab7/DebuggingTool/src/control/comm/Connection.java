package control.comm;
import lejos.pc.comm.*;

public class Connection {
  private NXTComm comm;

  private NXTComm connect()
  {
    comm = null;
    try
    {
      comm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
    }
    catch (NXTCommException e)
    {
      
      e.printStackTrace();
      System.err.println("Couldn't create NXT comm. Exiting.");
      System.exit(-1);
    }
    
    // Connect
    NXTInfo info = new NXTInfo();
    info.protocol = NXTCommFactory.BLUETOOTH;
    info.name = "LEAD7";
    info.deviceAddress = "00165313E6DB";
    
    try
    {
      comm.open(info, NXTComm.PACKET);
    }
    catch (NXTCommException e)
    {
      e.printStackTrace();
      System.err.println("Couldn't connect to NXT " + info.name
          + ". Exiting.");
      System.exit(-2);
    }
    return comm;
  }
}
