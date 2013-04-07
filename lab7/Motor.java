import lejos.nxt.*;

public class Motor {

 	public NXTRegulatedMotor motor;

	public void speed(float speed) {
		motor.setSpeed(speed);
	}
	
	public void move() {
		motor.forward();
	}
	
	public void rotate(float angle) {
		motor.rotate(angle);
	}

	public float angle() {
		return motor.getRotationSpeed();
	}
}
