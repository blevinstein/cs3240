import java.io.*;
//import lejos.pc.*;
import lejos.pc.comm.*;

public class Controller {
    Communicator comm;
    MotionController motionController

    public static void main(String[] args) {
        comm = new Communicator();
        motionController = new MotionController();
        boolean connected = false;

        comm.start();

        while (true) {
            String message;
            if (comm.hasMessage()) {
                message = comm.getMessage();
            }
            motionController.act(message);
            act(message);
        }
    }

    private void act(String message) {
        // if message == "query", send sensory data via comm
    }
}
