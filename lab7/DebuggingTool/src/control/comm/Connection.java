package control.comm;
import lejos.pc.comm.*;

/**
 * Represents a connection to the NXT base station. Used for communication wiht the base station.
 */
public class Connection {
  private NXTComm comm; // the private NXTComm object used for communication

  /**
   * Connect to the base station using the hard-coded base station address. Exits if a connection
   * cannot be made.
   * @return the NXTComm connection object
   */
  private NXTComm connect()
  {
    comm = null;
    // try to create a connection
    try
    {
      comm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
    }
    catch (NXTCommException e)
    {
      // uh oh! could not create it
      e.printStackTrace();
      System.err.println("Couldn't create NXT comm. Exiting.");
      System.exit(-1);
    }
    
    // base station parameters
    NXTInfo info = new NXTInfo();
    info.protocol = NXTCommFactory.BLUETOOTH;
    info.name = "LEAD7";
    info.deviceAddress = "00165313E6DB";
    
    // try to connect
    try
    {
      comm.open(info, NXTComm.PACKET);
    }
    catch (NXTCommException e)
    {
      // uh oh! couldn't connect
      e.printStackTrace();
      System.err.println("Couldn't connect to NXT " + info.name
          + ". Exiting.");
      System.exit(-2);
    }
    return comm;
  }
}
