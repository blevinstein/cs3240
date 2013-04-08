import lejos.nxt.*;
import java.util.*;

//class that is capable of reading all of the TELEMETRY data from the robot.
//provides getter and setters for ports, as well as getters for the values
//on the sensors themselves.

public class Telemetry {
	
	private LightSensor light;
	private SensorPort lightPort;
	
	private TouchSensor touch;
	private SensorPort touchPort;
	
	private UltrasonicSensor ultrasonic;
	private SensorPort ultrasonicPort;
	
	private SoundSensor sound;
	private SensorPort soundPort;
	
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
	
	public List<String> getTelemetryData(){
		List<String> telemetry_data = new ArrayList<String>();
		telemetry_data.add(Integer.toString(getLight()));
		telemetry_data.add(Integer.toString(getSound()));
        telemetry_data.add(getTouch() ? "1" : "0");
		telemetry_data.add(Integer.toString(getUltrasonic()));
		return telemetry_data;
	}
	
	public int getLight(){
		return light.readNormalizedValue();
	}
	
	public boolean getTouch(){
		return touch.isPressed();
	}
	
	public int getUltrasonic(){
		return ultrasonic.getDistance();
	}
	public int getSound(){
		return sound.readValue();
	}
	
	public SensorPort getLightPort(){
		return lightPort;
	}
	
	public SensorPort getTouchPort(){
		return touchPort;
	}
	
	public SensorPort getUltrasonicPort(){
		return ultrasonicPort;
	}
	
	public SensorPort getSoundPort(){
		return soundPort;
	}
	
	public void setLightPort(SensorPort lport){
		lightPort = lport;
	}
	
	public void setTouchPort(SensorPort tport){
		touchPort = tport;
	}
	public void setUltrasonicPort(SensorPort uport){
		ultrasonicPort = uport;
	}
	public void setSoundPort(SensorPort sport){
		soundPort = sport;
	}
	
}
