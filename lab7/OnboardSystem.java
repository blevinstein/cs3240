
import java.util.ArrayList;
import lejos.nxt.*;

public class OnboardSystem {
	
	public static final String INIT = "init";
	public static final String MOVE = "move";
	public static final String TURN = "turn";
	public static final String CLAW = "claw";
	public static final String STOP = "stop";
	public static final String QUERY = "query";
	public static final String QUIT = "quit";
	public static final String ACK = "ack";
	
	public static final String[] commands = {INIT, MOVE, TURN, CLAW, STOP, QUERY, QUIT, ACK};
	
	private static Controller controller;
	
	public static void main(String[] args){
		
		System.out.println("Welcome to the onboard system for group 13!");
		System.out.println("Press any button to continue");
		Button.waitForPress();
	
		System.out.println("Initializing..........");
		controller = new Controller(MotorPort.A, MotorPort.B, MotorPort.C, SensorPort.S3, SensorPort.S1, SensorPort.S2, SensorPort.S4);
		
		//START EXECUTION OF ONBOARD SYSTEM
		controller.mainLoop();
		
	}
}
