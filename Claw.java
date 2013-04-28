import lejos.nxt.*;

/**
 * An interface for the claw peripheral.
 */
public class Claw {
	
	private int angle; // holds the current rotation angle of the claw
	private NXTRegulatedMotor motor; // the NXT motor that manipulates the claw
	private MotorPort motorPort; // the port for the NXT motor
	
	private static final int GEAR_RATIO = 3;
	private static final int MAX_ROTATION_ANGLE = 360;
	
	/**
	 * Creates a Claw object to interface with the claw
	 * @param mport the port for the claw motor
	 */
	public Claw(MotorPort mport){
		angle = 0;
		motor = new NXTRegulatedMotor(mport);
		motor.resetTachoCount();
	}
	
	/**
	 * Gets the current rotation angle of the claw
	 * @return the angle of the claw
	 */
	public int getAngle(){
		return angle;
	}
	
	/**
	 * Gets the port used by the claw motor
	 * @return the port of the claw motor
	 */
	public MotorPort getMotorPort(){
		return motorPort;
	}
	
	/**
	 * Sets the port for the claw motor to use
	 * @param mport the given claw motor port
	 */
	public void setMotorPort(MotorPort mport){
		motorPort = mport;
	}
	
	/**
	 * Rotates the claw by the given number of degrees (relative to present rotation)
	 * @param degrees the given number of degrees to rotate by
	 */
	public void rotate(int degrees){
		degrees %= MAX_ROTATION_ANGLE; //anything over 360 is redundant
		motor.rotate(degrees / GEAR_RATIO);
		angle = motor.getTachoCount();
	}

  public void setSpeed(int speed) {
    motor.setSpeed(speed);
  }
	
	/**
	 * Stops the claw from spinning
	 */
	public void stop(){
		motor.setSpeed(0);
	}
	
}
