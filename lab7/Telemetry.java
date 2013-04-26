import lejos.nxt.*;
import java.util.*;

/**
 * Class that is capable of reading all of the TELEMETRY data from the robot.
 * provides getter and setters for ports, as well as getters for the values
 * on the sensors themselves.
 */
public class Telemetry {
	
	// sensors and their corresponding ports used by the robot
	
	private LightSensor light;
	private SensorPort lightPort;
	
	private TouchSensor touch;
	private SensorPort touchPort;
	
	private UltrasonicSensor ultrasonic;
	private SensorPort ultrasonicPort;
	
	private SoundSensor sound;
	private SensorPort soundPort;
	
	/**
	 * Constructs a standard Telemetry object for interfacing with the robot's sensors
	 * @param lport the port to use for the light sensor
	 * @param tport the port to use for the touch sensor
	 * @param uport the port to use for the ultrasonic sensor
	 * @param sport the port to use for the sound sensor
	 */
	public Telemetry(SensorPort lport, SensorPort tport, SensorPort uport, SensorPort sport){
		
		light = new LightSensor(lport);
		setLightPort(lport);
		
		touch = new TouchSensor(tport);
		setTouchPort(tport);
		
		ultrasonic = new UltrasonicSensor(uport);
		setUltrasonicPort(uport);
		ultrasonic.continuous();
		
		sound = new SoundSensor(sport);
		setSoundPort(sport);
		
	}
	
	/**
	 * Gets a list of strings containing all of the sensory data. The list contains, in order:
	 * <ol>
	 * <li>The light sensor value (<code>getLight</code>)</li>
	 * <li>The sound sensor value (<code>getSound</code>)</li>
	 * <li>The touch sensor value, either "0" or "1" depending on <code>getTouch</code></li>
	 * <li>The ultrasonic sensor value (<code>getUltrasonic</code>)</li>
	 * </ol>
	 * 
	 * @return an ordered list of the sensory data
	 */
	public List<String> getTelemetryData(){
		List<String> telemetry_data = new ArrayList<String>();
		telemetry_data.add(Integer.toString(getLight()));
		telemetry_data.add(Integer.toString(getSound()));
        telemetry_data.add(getTouch() ? "1" : "0");
		telemetry_data.add(Integer.toString(getUltrasonic()));
		return telemetry_data;
	}
	
	/**
	 * 
	 * @return the <code>readNormalizedValue</code> value of the light sensor
	 */
	public int getLight(){
		return light.readNormalizedValue();
	}
	
	/**
	 * 
	 * @return whether the touch sensor is presesed
	 */
	public boolean getTouch(){
		return touch.isPressed();
	}
	
	/**
	 * 
	 * @return the <code>getdistance</code> value of the ultrasonice sensor
	 */
	public int getUltrasonic(){
		return ultrasonic.getDistance();
	}
	
	/**
	 * 
	 * @return the value of the sound sensor
	 */
	public int getSound(){
		return sound.readValue();
	}
	
	/**
	 * 
	 * @return the port of the light sensor
	 */
	public SensorPort getLightPort(){
		return lightPort;
	}
	
	/**
	 * 
	 * @return the port of the touch sensor
	 */
	public SensorPort getTouchPort(){
		return touchPort;
	}
	
	/**
	 * 
	 * @return the port of the ultrasonic sensor
	 */
	public SensorPort getUltrasonicPort(){
		return ultrasonicPort;
	}
	
	/**
	 * 
	 * @return the port of the sound sensor
	 */
	public SensorPort getSoundPort(){
		return soundPort;
	}
	
	/**
	 * Sets the light sensor port.
	 * @param lport the given light sensor port.
	 */
	public void setLightPort(SensorPort lport){
		lightPort = lport;
	}

	/**
	 * Sets the touch sensor port.
	 * @param tport the given touch sensor port.
	 */
	public void setTouchPort(SensorPort tport){
		touchPort = tport;
	}
	
	/**
	 * Sets the ultrasonic sensor port.
	 * @param uport the given ultrasonic sensor port
	 */
	public void setUltrasonicPort(SensorPort uport){
		ultrasonicPort = uport;
	}
	
	/**
	 * Sets the sounds sensor port
	 * @param sport the given sounds sensor port
	 */
	public void setSoundPort(SensorPort sport){
		soundPort = sport;
	}
	
}
