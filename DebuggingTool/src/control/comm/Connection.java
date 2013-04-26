package control.comm;
import lejos.pc.comm.*;

import java.io.IOException;
import java.util.*;

import control.gui.DebugInterface;

/**
 * Represents a connection to the NXT base station. Used for communication wiht the base station.
 */
public class Connection {
  private NXTComm comm; // the private NXTComm object used for communication
  private int seqNum = 0;
  DebugInterface debug;
  
  public Connection(DebugInterface debug) {
	  this.debug = debug;
  }

  /**
   * Connect to the base station using the hard-coded base station address. Exits if a connection
   * cannot be made.
   * @return the NXTComm connection object
   */
  private void connect()
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
  }
  
  public List<Message> sendMessage(Message msg) throws IOException {
	  msg.setSeqNum(seqNum);
	  comm.write(msg.toString().getBytes());
	  seqNum = seqNum == 0 ? 1 : 0;
	  
	  int numResponses = msg.getMap().get(0)[0].equals("query") ? 3 : 2;
	  
	  StringBuffer buf = new StringBuffer();
	  ArrayList<Message> responses = new ArrayList<Message>();
	  
	  for (int i=0; i<numResponses; i++) {
		  while (true) {
			  buf.append(new String(comm.read()));
			  if (buf.indexOf("}") != -1) {
				  break;
			  }
		  }
		  responses.add(new Message(buf.substring(0, buf.indexOf("}") + 1)));
		  buf.delete(0, buf.indexOf("}") + 1);
	  }
	  debug.updateSeqNums(seqNum);
	  return responses;
  }
  
  public NXTComm getComm() {
	  return comm;
  }
  
  public void setComm(NXTComm comm) {
	  this.comm = comm;
  }
}
