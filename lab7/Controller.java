import java.io.*;
import java.util.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;


/**
 * Provides the main loop for the NXT brick, once it has been initialized with a specific port configuration.
 */
public class Controller {
    Communicator comm;
    Claw claw;
    Movement movement;
    Telemetry telemetry;
  
    
    private static final int AUTONOMOUS_SPEED = 180;
    private static final int AUTONOMOUS_CLAW_SPEED = 120;
  
    /**
     * Constructs a Controller instance with the given port configuration.
     * @param clawPort The motor which is connected to the claw.
     * @param leftPort The motor which is connected to the left wheel.
     * @param rightPort The motor which is connected to the right wheel.
     * @param light The port which is connected to the light sensor.
     * @param touch The port which is connected to the touch sensor.
     * @param ultra The port which is connected to the ultrasonic sensor.
     * @param sound The port which is connected to the sound sensor.
     */
    public Controller(MotorPort clawPort,
    				  MotorPort leftPort,
    				  MotorPort rightPort,
    				  SensorPort light,
    				  SensorPort touch,
    				  SensorPort ultra,
    				  SensorPort sound) {
    	claw = new Claw(clawPort);
    	movement = new Movement(leftPort, rightPort);
    	telemetry = new Telemetry(light, touch, ultra, sound);
    }
    
    public void autonomousMode(){
    	movement.setSpeed(AUTONOMOUS_SPEED, -AUTONOMOUS_SPEED);
    	claw.rotate(AUTONOMOUS_CLAW_SPEED);
    }

    /**
     * The main control loop for the program.
     * Interprets incoming messages from the Communicator and performs the appropriate action.
     */
    public void mainLoop() {
        comm = new Communicator();
       
        comm.start();

        while (true) {
            Message message;
            
            if (!comm.isConnected()) {
            	autonomousMode();
            } 
            
            else if (comm.hasMessage()) {
            	
            	message = comm.getMessage();
            	
            	//parse the message
            	
               	String command = message.pairs.get(0)[0];
               	String param = message.pairs.get(0)[1];
               	
                System.out.println(command);
               	
                Message ack = new Message(message.getSeqNum());
               	
            	if (command.equals("init")){
            		System.out.println("Robot is successfully initialized. Proceed to" +
            				" send commands.")
            	}
            	else if (command.equals("move")){
            		int move = Integer.parseInt(param);
            		movement.setSpeed(move, move);
            	}
            	else if (command.equals("turn")){
            		int rate = Integer.parseInt(param);
                    movement.setSpeed(rate, -rate);
            	}
            	else if (command.equals("claw")){
                    int heading = Integer.parseInt(param);
            		claw.rotate(heading);
            	}
            	else if (command.equals("stop")){
            		movement.halt();
            	}
            	else if (command.equals("query")) {
            		// get telemetry data, construct message, comm.send() it
            	    List<String> data =	telemetry.getTelemetryData();
                    Message m = new Message(message.getSeqNum());
                    m.pairs.add(new String[]{"data",null});
                    m.pairs.add(new String[]{"distance", Double.toString(movement.getDistTraveled())});
                    m.pairs.add(new String[]{"light",data.get(0)});
                    m.pairs.add(new String[]{"sound",data.get(1)});
                    m.pairs.add(new String[]{"touch",data.get(2)});
                    m.pairs.add(new String[]{"claw",claw.getAngle()+""});
                    m.pairs.add(new String[]{"heading", Double.toString(movement.getHeading())});
                    m.pairs.add(new String[]{"speed",(movement.getLeftSpeed() + movement.getRightSpeed())/2 + ""});
                    m.pairs.add(new String[]{"ultrasonic",data.get(3)});
                    comm.sendMessage(m);
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
            	
            	ack.pairs.add(new String[]{"done", command});
            	comm.sendMessage(ack);
            			
            } else {
            	System.out.println("Command queue empty. Awaiting additional commands.");
                // queue is empty, do nothing
            }
        }
    }
}
