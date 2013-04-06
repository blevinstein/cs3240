import java.io.*;
//import lejos.pc.*;
import lejos.pc.comm.*;

public class MotionController {
    Communicator comm;

    public MotionController(Communicator comm) {
        this.comm = comm;
    }

    public void act(String message) {
        if (comm.isConnected()) {
            // demultiplex, act
        } else {
            // autopilot
        }
    }
}
