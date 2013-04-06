import java.io.*;
import lejos.nxt.comm.*;

public class BluetoothConnection {

	//the bluetooth connection
	private NXTConnection connection;
	
	//the data input stream
	private DataInputStream input;
	
	//the data output stream
	private DataOutputStream output;
	
	//constructor that starts a bluetooth connection with robot
	public BluetoothConnection(){
		connect();
	}
	
	//getters for the class
	public NXTConnection getConnection(){
			return connection;
	}
		
	public DataInputStream getInput(){
			return input;
	}
	public DataOutputStream getOutput(){
			return output;
	}
	
	//returns true if BT connection exists
	public boolean connected(){
		return connection != null;
	}
	
	
	
	//establishes a bluetooth connection with robot
	public boolean connect(){
		
		connection = Bluetooth.waitForConnection();
		
		if(!connected()){
			return false;
		}
		else{
			input = connection.openDataInputStream();
			output = connection.openDataOutputStream();
			return true;
		}
	}
	
	public boolean disconnect(){
		//try to close the input and output data streams
		//return false if an IOException occurs
		//return true if streams close and connection closes
		
		try{
			input.close();
		}
		catch(IOException e){
			return false;
		}
		try{
			output.close();
		}
		catch(IOException e){
			return false;
		}
		connection.close();
		return true;
	}
	
	public synchronized void send(String s) throws IOException{
		for (int i = 0; i < s.length(); ++i){
			byte byteToSend = (byte) s.charAt(i);
			output.write(byteToSend);
		}
	}
	public byte[] read(){
		try{
			
			byte[] msg = new byte[256];
			int size = input.read(msg);
			//if the size is greater than zero then we have a message
			if(size > 0){
				return msg;
			}
			
		}
		catch(Exception e){
			//if we lose a connection, reconnect
			if(disconnect() == true){
				this.connect();
			}
		}
		return null;
	}
	
}
