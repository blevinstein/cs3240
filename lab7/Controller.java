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
            	
            	// parse message
            	if (message.command().equals("query")) {
            		// get telemetry data, construct message, comm.send() it
            	}
            
            }
        }
    }
}
