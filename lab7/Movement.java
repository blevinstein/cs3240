import lejos.nxt.*;
import java.util.ArrayList;

public class Movement {
	
	private NXTRegulatedMotor left_motor;
	private MotorPort left_motor_port;
	
	private NXTRegulatedMotor right_motor;
	private MotorPort right_motor_port;
	
	private int LEFT_ROTATION = 1;
	private int RIGHT_ROTATION = 1;
	
	private static final int START_SPEED = 0;
	private static final int MAX_SPEED = 128;
	
	
	public Movement(MotorPort leftport, MotorPort rightport){
		left_motor = new NXTRegulatedMotor(leftport);
		right_motor = new NXTRegulatedMotor(rightport);
		
		setSpeed(START_SPEED, START_SPEED);
		
		setLeftMotorPort(leftport);
		setRightMotorPort(rightport);
	}
	
	public void setSpeed(int left_speed, int right_speed){
		
		left_motor.setSpeed(left_speed);
		right_motor.setSpeed(right_speed);
		
		if (left_speed >= 0 && right_speed >= 0){
			LEFT_ROTATION = 1;
			RIGHT_ROTATION = 1;
			left_motor.forward();
			right_motor.forward();
		}
		else if (left_speed < 0 && right_speed >= 0){
			LEFT_ROTATION = -1;
			RIGHT_ROTATION = 1;
			left_motor.backward();
			right_motor.forward();
		}
		else if (left_speed >= 0 && right_speed < 0){
			LEFT_ROTATION = 1;
			RIGHT_ROTATION = -1;
			left_motor.forward();
			right_motor.backward();
		}
		else if (left_speed < 0 && right_speed < 0){
			LEFT_ROTATION = -1;
			RIGHT_ROTATION = -1;
			left_motor.backward();
			right_motor.backward();
		}
	}
	
	public int getLeftSpeed(){
		return (int)(LEFT_ROTATION * left_motor.getSpeed());
	}
	public int getRightSpeed(){
		return (int)(RIGHT_ROTATION * right_motor.getSpeed());
	}
	public MotorPort getLeftMotorPort(){
		return left_motor_port;
	}
	public MotorPort getRightMotorPort(){
		return right_motor_port;
	}
	public void setLeftMotorPort(MotorPort lport){
		left_motor_port = lport;
	}
	public void setRightMotorPort(MotorPort rport){
		right_motor_port = rport;
	}
	
	
}
