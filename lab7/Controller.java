import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

public class Controller {
    Communicator comm;
    Claw claw;
    Movement movement;
    Telemetry telemetry;
    
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
   
    public void mainLoop() {
        comm = new Communicator();
       
        comm.start();

        while (true) {
            Message message;
            
            if (!comm.hasMessage() && comm.isConnected()) {
            	//Thread.sleep(20);
            	continue;
            }
            if (!comm.isConnected()) {
            	//autopilot
            }
            if (comm.hasMessage()) {
            	message = comm.getMessage();
            
            }
        }
    }
}
