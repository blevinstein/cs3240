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
  }

  /**
   * Connect to the base station using the hard-coded base station address. Exits if a connection
   * cannot be made.
   * @return the NXTComm connection object
   */
  public void connect() {
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
  
  public void disconnect() {
	  try {
		  comm.close();
	  } catch (IOException e) {
		  e.printStackTrace();
	      System.err.println("Couldn't disconnect to NXT. Exiting.");
	      System.exit(-2);
	  }
  }
  
  public List<Message> sendMessage(Message msg) {
	  msg.setSeqNum(seqNum);
	  try {
		  comm.write(msg.toString().getBytes());
	  } catch (IOException e) {
		  return null;
	  }
	  seqNum = seqNum == 0 ? 1 : 0;
	  
	  int numResponses = 2;
	  
	  StringBuffer buf = new StringBuffer();
	  ArrayList<Message> responses = new ArrayList<Message>();
	  
	  for (int i=0; i<numResponses; i++) {
		  while (true) {
			  try {
				  buf.append(new String(comm.read()));
			  } catch (IOException e) {
				  return null;
			  }
			  if (buf.indexOf("}") != -1) {
				  break;
			  }
		  }
		  responses.add(new Message(buf.substring(0, buf.indexOf("}") + 1)));
		  buf.delete(0, buf.indexOf("}") + 1);
	  }
	  
	  for (Message m : responses) {
		  debug.writeResponse(m.toString() + "\n");
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
  
  public int getSeqNum() {
	  return seqNum;
  }
}
