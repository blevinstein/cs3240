import lejos.nxt.*;

public class Claw {
	
	private int angle;
	private NXTRegulatedMotor motor;
	private MotorPort motorPort;
	
	private static final int GEAR_RATIO = 3;
	
	public Claw(MotorPort mport){
		angle = 0;
		motor = new NXTRegulatedMotor(mport);
		motor.resetTachoCount();
	}
	
	public int getAngle(){
		return angle;
	}
	
	public MotorPort getMotorPort(){
		return motorPort;
	}
	
	public void setMotorPort(MotorPort mport){
		motorPort = mport;
	}
	
	public void rotate(int degrees){
		degrees %= 360; //anything over 360 is redundant
		motor.rotate(degrees / GEAR_RATIO);
		angle = motor.getTachoCount();
	}
}
