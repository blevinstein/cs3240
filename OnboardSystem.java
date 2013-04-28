import java.util.ArrayList;
import lejos.nxt.*;

/**
 * This class provides for the initialization and execution of a Controller with the specific configuration pertaining to our robot.
 */
public class OnboardSystem {
	
	private static Controller controller; // the Controller instance which will provide the main loop

    /**
     * The main method for the NXT brick.
     */
	public static void main(String[] args){
		
		controller = new Controller(MotorPort.A, MotorPort.B, MotorPort.C, SensorPort.S3, SensorPort.S1, SensorPort.S2, SensorPort.S4);
        try {
            controller.mainLoop();
        } catch (Exception e) {
            // provide for outputting a stack trace on the NXT brick
            System.out.println("Error!");
            e.printStackTrace(System.out);
        }
		
	}
}
