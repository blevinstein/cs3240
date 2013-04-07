import lejos.nxt.*;

public class Motor extends NXTRegulatedMotor {

  public Motor(TachoMotorPort port) {
		super(port);
		this.setSpeed(0);
	}

	public void speed(float speed) {
		this.setSpeed(speed);
	}
	
	public void move() {
		
	}
	
	public void rotate(float angle) {
		this.rotate(angle);
	}

	public float angle() {
		return this.getRotationSpeed();
	}
}
