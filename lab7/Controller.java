import java.io.*;
//import lejos.pc.*;
import lejos.pc.comm.*;

public class Controller {
    Communicator comm;
    Claw claw;
    Movement movement;
    Telemetry telemetry;
    
    public Controller(MotorPort clawPort,
    				  MotorPort leftport,
    				  MotorPort rightport,
    				  SensorPort light,
    				  SensorPort touch,
    				  SensorPort ultra,
    				  SensorPort sound) {
    	claw = new Claw(clawPort);
    	movement = new Movement(leftPort, rightPort);
    	telemetry = new Telemetry(light, touch, ultra, sound);
    }
   
    public static void mainLoop() {
        comm = new Communicator();
        motionController = new MotionController();
       
        comm.start();

        while (true) {
            Message message;
            
            if (!comm.hasMessage() && comm.isConnected()) {
            	Thread.sleep(20);
            	continue;
            }
            if (!comm.isConnected()) {
            	//autopilot
            }
            if (comm.hasMesssage()) {
            	
            	message = comm.getMessage();
            	
            	//parse the message
            	
               	String command = message.pairs.get(0)[0];
               	String param = message.pairs.get(0)[1];
               	
            	if (command.equals("init")){
            		
            	}
            	else if (command.equals("move")){
            		int move = Integer.parseInt(param);
            		movement.setSpeed(move, move);
            	}
            	else if (command.equals("turn")){
            		int degrees = Integer.parseInt(param);
            		//somehow do something to turn the robot
            	}
            	else if (command.equals("claw")){
            		float heading = Float.parseFloat(param);
            		
            	}
            	else if (command.equals("stop")){
            		movement.setSpeed(0,0);
            	}
            	else if (command.equals("query")) {
            		// get telemetry data, construct message, comm.send() it
            		
            	}
            	else if (command.equals("quit")){
            		System.exit(-1);
            	}
            	else if (command.equals("ack")){
            		System.out.println("Waiting for additional commands...");
            	}
            	else{
            		//not a valid command... 
            	}
            			
            	
            	
            
            
            }
        }
    }
}
