import lejos.nxt.*;
import java.util.ArrayList;

/**
 * Interface for all of the motorized peripherals of the robot. Contains methods to read and
 * maniuplate the left and right motor of the robot.
 * 
 */
public class Movement {
	
	// NXT motors and their corresponding ports
	
	private NXTRegulatedMotor left_motor;
	private MotorPort left_motor_port;
	
	private NXTRegulatedMotor right_motor;
	private MotorPort right_motor_port;
	
	// The status of the left and right motors. Forward = 1, backward = -1
	private int LEFT_ROTATION = 1;
	private int RIGHT_ROTATION = 1;
	
	// starting motor speed
	private static final int START_SPEED = 0;
	
	// max motor speed
	private static final int MAX_SPEED = 128;
	
	/**
	 * Creates a new Movement class with the given left and right ports.
	 * @param leftport the port to use for the left motor
	 * @param rightport the port to use for the right motor
	 */
	public Movement(MotorPort leftport, MotorPort rightport){
		left_motor = new NXTRegulatedMotor(leftport);
		right_motor = new NXTRegulatedMotor(rightport);
		
		setSpeed(START_SPEED, START_SPEED);
		
		setLeftMotorPort(leftport);
		setRightMotorPort(rightport);
	}
	
	/**
	 * Sets the speed of the left and right motor. Negative speed values indicate backwards motion
	 * @param left_speed speed of the left motor
	 * @param right_speed speed of the right motor
	 */
	public void setSpeed(int left_speed, int right_speed){
		
		left_motor.setSpeed(left_speed);
		right_motor.setSpeed(right_speed);
		
		if (left_speed >= 0) {
			LEFT_ROTATION = 1;
			left_motor.forward();
		} else {
			LEFT_ROTATION = -1;
			left_motor.backward();
		}
		
		if (right_speed >= 0) {
			RIGHT_ROTATION = 1;
			right_motor.forward();
		} else {
			RIGHT_ROTATION = -1;
			right_motor.backward();
		}
	}
	
	/**
	 * 
	 * @return the speed of the left motor
	 */
	public int getLeftSpeed(){
		return (int)(LEFT_ROTATION * left_motor.getSpeed());
	}
	
	/**
	 * 
	 * @return the speed of the right motor
	 */
	public int getRightSpeed(){
		return (int)(RIGHT_ROTATION * right_motor.getSpeed());
	}
	
	/**
	 * 
	 * @return the port of the left motor
	 */
	public MotorPort getLeftMotorPort(){
		return left_motor_port;
	}
	
	/**
	 * 
	 * @return the port of the right motor
	 */
	public MotorPort getRightMotorPort(){
		return right_motor_port;
	}
	
	/**
	 * Sets the port of the left motor
	 * @param lport the new port for the left motor
	 */
	public void setLeftMotorPort(MotorPort lport){
		left_motor_port = lport;
	}
	
	/**
	 * Sets the port of the right motor
	 * @param rport the new port for the right motor
	 */
	public void setRightMotorPort(MotorPort rport){
		right_motor_port = rport;
	}
	
	
}
