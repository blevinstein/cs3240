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
  
    private static final int STOP_SPEED = 0;
    private static final int AUTONOMOUS_SPEED = 0;
    private static final int AUTONOMOUS_CLAW_SPEED = 30;
    private static final int SELF_PRESERVATION_SPEED = 360;
  
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
    	claw.setSpeed(AUTONOMOUS_CLAW_SPEED);
    }

    /**
     * The main control loop for the program.
     * Interprets incoming messages from the Communicator and performs the appropriate action.
     * @throws InterruptedException 
     */
    public void mainLoop() throws InterruptedException {
        comm = new Communicator();
       
        comm.start();
        int initialize_flag = 0;
        int powd_flag = 0;
        int danger_flag = 0;
       
        while (true) {
            Message message;
            List<String> data =	telemetry.getTelemetryData();
            if (telemetry.getUltrasonic() <= 20){
            	danger_flag = 1;
            }
            //what to do if the robot backs up into something
            if (telemetry.getTouch()){
            	movement.setSpeed(SELF_PRESERVATION_SPEED, SELF_PRESERVATION_SPEED);
            	claw.stop();
            	Thread.sleep(1000);
            	movement.setSpeed(STOP_SPEED, STOP_SPEED);
            }
            if (!comm.isConnected()) {
            	initialize_flag = 0;
            	autonomousMode();
            } else if (comm.hasMessage()) {
            	
            	//if the robot exits autonomous mode, it must stop all motors before 
            	//receiving additional commands
            	++initialize_flag;
            	if (initialize_flag == 1){
            		movement.setSpeed(STOP_SPEED, STOP_SPEED);
            		claw.stop();
            	}
            	
            	
            	message = comm.getMessage();
            	
            	//parse the message
            	
               	String command = message.pairs.get(0)[0];
               	String param = message.pairs.get(0)[1];
               	
                System.out.println(command);
               	
                Message ack = new Message(message.getSeqNum());
               	
            	if (command.equals("init")){
            		System.out.println("Connection established. Send commands.");
            	}
            	else if (command.equals("move")){
            		int move = Integer.parseInt(param);
            		if (danger_flag == 1 && move > 0){
            			System.out.println("DANGER! Cannot move forward.");
            		}
            		else{
            			danger_flag = 0;
            			movement.setSpeed(move, move);
            		}
            	}
            	else if (command.equals("turn")){
            		int rate = Integer.parseInt(param);
                    movement.setSpeed(rate, -rate);
            	}
            	else if (command.equals("claw")){
                    int heading = Integer.parseInt(param);
            		claw.setSpeed(heading);
            	}
            	else if (command.equals("stop")){
            		movement.halt();
            	}
            	else if (command.equals("query")) {
            		// get telemetry data, construct message, comm.send() it
            	  
                    // update distance travelled and heading
                    movement.updateDistHeading();

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
            	else if (command.equals("quit") || command.equals("powd")){
            		powd_flag = 1;
            	}
            	else if (command.equals("ack")){
            		//System.out.println(".");
            	}
            	else if (command.equals("halt")){
            		movement.halt();
            		claw.stop();
            	}
            	else if (command.equals("updt")){
                    // update distance travelled and heading
                    movement.updateDistHeading();

            		ack.pairs.add(new String[]{"location", Double.toString(movement.getDistTraveled())});
            		ack.pairs.add(new String[]{"light", data.get(0)});
            		ack.pairs.add(new String[]{"sound", data.get(1)});
            		ack.pairs.add(new String[]{"touch", data.get(2)});
            		ack.pairs.add(new String[]{"claw", claw.getAngle() + ""});
            		ack.pairs.add(new String[]{"heading", Double.toString(movement.getHeading())});
            		ack.pairs.add(new String[]{"speed",(movement.getLeftSpeed() + movement.getRightSpeed())/2 + ""});
            		ack.pairs.add(new String[]{"ultrasonic",data.get(3)});
            		ack.pairs.add(new String[]{"connection", comm.isConnected() + ""});
            		ack.pairs.add(new String[]{"motorA", claw.getSpeed() + ""});
            		ack.pairs.add(new String[]{"motorB", movement.getLeftSpeed() + ""});
            		ack.pairs.add(new String[]{"motorC", movement.getRightSpeed() + ""});
            		
            	}
            	else if (command.equals("auto")){
            		autonomousMode();
            	}
            	else{
            		//not a valid command... 
            	}
            	
            	ack.pairs.add(0, new String[]{"done", command});
            	comm.sendMessage(ack);
            	
            	if(powd_flag == 1){
            		System.out.println("Powering down...");
            		Thread.sleep(2000);
            		System.exit(-1);
            	}
            			
            } else {
            	//System.out.println(".");
                // queue is empty, do nothing
            }
        }
    }
}
