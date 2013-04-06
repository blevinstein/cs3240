import java.util.Random;

/*class with a method that just generates random valid commands, as defined in our group's
 * communication protocol.
 */
public class RandomCommand {
	
	private static String[] command_list = {"move", "turn","claw","stop","query", "invalid command"};
	
	public static String generateCommand(){
		
		//generate a random index to pull out a random valid command
		Random rand = new Random();
		int random_index = rand.nextInt(command_list.length);
		String command = command_list[random_index];
		String output = "";
		
		//can't switch on a string so if statements must be used
		
		if(command == "move"){
			int speed = rand.nextInt(1440) - 720;
			output = "{move&speed:" + speed + "}";
		}
		else if(command == "turn"){
			int heading = rand.nextInt(360) - 180;
			output = "{turn&heading:" + heading + "}";
		}
		else if(command == "claw"){
			double position = rand.nextDouble();
			output = "{claw&position:" + position + "}";
		}
		else if(command == "stop"){
			output = "{stop}";
		}
		else if(command == "query"){
			output = "{query}";
		}
		else if(command == "terminate"){
			output = "{terminate}";
		}
		else{
			output = "{an invalid command}";
		}
		return output;
	}
	
	public static void main(String args[]){
		//BluetoothConnection btConnection = new BluetoothConnection();
		System.out.println("Test");
	}

}
