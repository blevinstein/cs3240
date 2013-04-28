package control.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.*;

import control.comm.*;

 /**
 * @author 
 * Brian Levinstein (bpl4ab), 
 * Yuchen Zou (yz2ak), 
 * Neal Milstein (nrm2zf)
 * Bryan Walsh (bpw7xx)
 * Alex Weaver (baw4ux)
 */

//import lejos.pc.comm;

//import control.communication.CommandMessage;
//import control.communication.CommandMessage.CommandType;
//import control.communication.ResponseMessage;
//import control.main.Controller;

/** 
 * This class  unifies all the GUI elements: CommandQueue, CommandComposer, OtherCommands, 
 * ProgramVariables, and RobotResponse.
 */
public class DebugInterface {
	
	private static CommandQueue myQueue;
	private static CommandComposer myComposer;
	private static OtherCommands myOther;
	private static ProgramVariables myVariables;
	private static RobotResponse myResponse;
	
	private Connection conn;
	
	private JFrame myFrame;
	
	/* *
	 * debug
	 *		refresh variables based on received string
	 *
	 * command queue
	 *		own queue based off of what's displayed
	 * 		step sends one and removes it (to controller)
	 * 		execute sends and removes all of them (to controller)
	 * 		delete deletes the selected one
	 *		command queue list should be a JLIST not a text area
	 * */
	
	//Creates the DebugInterface and displays it
	public static void main(String[] args) {
		DebugInterface myDebug = new DebugInterface();
		myDebug.display();
		
		while (true);	
	}
	
	/** 
	 * @return the GUI element CommandQueue
	 */
	public static CommandQueue getMyQueue() {
		return myQueue;
	}

	/** 
	 * @param Takes a CommandQueue and set the DebugInterface's CommandQueue to it
	 */
	public static void setMyQueue(CommandQueue myQueue) {
		DebugInterface.myQueue = myQueue;
	}

	/** 
	 * @return the GUI element CommandComposer
	 */
	public static CommandComposer getMyComposer() {
		return myComposer;
	}

	/** 
	 * @param Takes a CommandComposer and set the DebugInterface's CommandComposer to it
	 */
	public static void setMyComposer(CommandComposer myComposer) {
		DebugInterface.myComposer = myComposer;
	}

	/** 
	 * @return the GUI elmement OtherCommands
	 */
	public static OtherCommands getMyOther() {
		return myOther;
	}

	/** 
	 * @param Takes a OtherCommands and set the DebugInterface's OtherCommands to it
	 */
	public static void setMyOther(OtherCommands myOther) {
		DebugInterface.myOther = myOther;
	}

	/** 
	 * @return the GUI element ProgramVariables
	 */
	public static ProgramVariables getMyVariables() {
		return myVariables;
	}

	/** 
	 * @param Takes a ProgramVariables and set the DebugInterface's ProgramVariables to it
	 */
	public static void setMyVariables(ProgramVariables myVariables) {
		DebugInterface.myVariables = myVariables;
	}

	/** 
	 * @return the GUI element RobotResponse
	 */
	public static RobotResponse getMyResponse() {
		return myResponse;
	}

	/** 
	 * @param Takes a RobotResponse and set the DebugInterface's RobotResponse to it
	 */
	public static void setMyResponse(RobotResponse myResponse) {
		DebugInterface.myResponse = myResponse;
	}

	//public Controller getMyController() {
	//	return myController;
	//}

	//public void setMyController(Controller myController) {
	//	this.myController = myController;
	//}

	/** 
	 * @return the JFrame object of the DebugingTool
	 */
	public JFrame getMyFrame() {
		return myFrame;
	}

	/** 
	 * @param Takes a JFrame and set the DebugInterface's JFrame to it
	 */
	public void setMyFrame(JFrame myFrame) {
		this.myFrame = myFrame;
	}

	/** 
	 * Constructor for the DebugInterface. This is where the main GUI is set up 
	 * and all sub-GUIs belonging to it are created and linked to the DebugInterface
	 */
	public DebugInterface() {
		conn = new Connection(this);
		conn.connect();
		
		myFrame = new JFrame("ROBOT DEBUGGER");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setSize(500, 600);
		myFrame.setLocation(650, 100);
		
		JPanel content = new JPanel();
		content.setLayout(new GridLayout(1, 2));
		
		myQueue = new CommandQueue();
		myComposer = new CommandComposer();
		myOther = new OtherCommands();
		myVariables = new ProgramVariables();
		myResponse = new RobotResponse();
		
    
		myComposer.getAddButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// make command message and add to controller queue
				// for original programming purposes, print string
				String selected = (String)myComposer.getCommands().getSelectedItem();
				
				Message msg = new Message();
				String val = myComposer.getDegrees().getValue().toString();
				if(selected.equals("Init")) {
					msg.put("init", null);
				} else if(selected.equals("Query")) {
					msg.put("query", null);					
				} else if(selected.equals("Quit")) {
					msg.put("quit", null);					
				} else if(selected.equals("Move")) {
					msg.put("move", val);
				} else if(selected.equals("Turn")) {
					msg.put("turn", val);
				} else if(selected.equals("Claw")) {
					msg.put("claw", val);
				} else if(selected.equals("Stop")) {
					msg.put("stop", val);
				} else {
					msg.put("ack", null);
				}
				myQueue.addMessage(msg);
				myQueue.updateMessages(conn.getSeqNum());
			}
		});
		
		myOther.getAutonomous().addItemListener( new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(myOther.getAutonomous().isSelected()) {
					Message msg = new Message();
					msg.put("auto", null);
					conn.sendMessage(msg);
				} else {
					Message msg = new Message();
					msg.put("halt", null);
					conn.sendMessage(msg);
				}
			}
		});
		
		myOther.getHalt().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message();
				msg.getMap().add(new String[] {"halt", null});
				conn.sendMessage(msg);
			}
		});
		
		myOther.getPower().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message();
				msg.getMap().add(new String[] {"powd", null});
				conn.sendMessage(msg);
			}
		});
		
		myOther.getReset().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message();
				msg.getMap().add(new String[] {"rset", null});
				conn.sendMessage(msg);
			}
		});
		
		myVariables.getRequestUpdate().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = new Message();
				msg.put("updt", null);
				Message resp = conn.sendMessage(msg).get(1);
				resp.getMap().remove(0);
				myVariables.update(resp.getMap());
			}
		});
		
		myQueue.getStep().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = (Message)myQueue.getMyList().remove(0);
				conn.sendMessage(msg);
			}
		});
		
		myQueue.getExecute().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while (!myQueue.getMyList().isEmpty()) {
					Message msg = (Message)myQueue.getMyList().remove(0);
					conn.sendMessage(msg);
				}
			}
		});
		
		myQueue.getDelete().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (myQueue.getMyList().size() == 0 || myQueue.getQueue().isSelectionEmpty())
					return;
				int[] selected = myQueue.getQueue().getSelectedIndices();
				for (int i=selected.length - 1; i>=0; i--) {
					myQueue.getMyList().remove(selected[i]);					
				}
				myQueue.updateMessages(conn.getSeqNum());
				int first = selected[0];
				if (myQueue.getMyList().size() > 0) {
					if (first < myQueue.getMyList().size())
						myQueue.getQueue().setSelectedIndex(first);
					else
						myQueue.getQueue().setSelectedIndex(0);
				}
			}
		});

		
		JPanel left = new JPanel();
		left.setSize(250, 600);
		left.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		JPanel right = new JPanel();
		right.setSize(250, 600);
		right.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
		
		left.add(myQueue);
		left.add(myComposer);
		left.add(myOther);
		
		right.add(myVariables);
		right.add(myResponse);
		
		content.add(left);
		content.add(right);
		
		myFrame.setContentPane(content);
		myFrame.setResizable(false);
		//frame.setVisible(true);
	}
	
	//Unhides DebugInterface GUI
	public void display() {
		myFrame.setVisible(true);
	}
	
	public void updateSeqNums(int seqNum) {
		myQueue.updateMessages(seqNum);
	}
	
	public void writeResponse(String resp) {
		myResponse.getMyResponses().append(resp);
	}
	
	/*public void updateVariables(ResponseMessage r) {
		// update variables in myVariables
		String response = r.getFormattedMessage(); // message with curly braces
		String[] groups = response.split("&");
		ArrayList<Object> splits = new ArrayList<Object>();
		for(int x = 1; x < groups.length; x++) {
			if(x < groups.length - 1) {
				String[] temp = groups[x].split(":");
				splits.add(temp[1]);
			}
			else {
				String[] temp = groups[x].split(":");
				int index = temp[1].indexOf('|');
				splits.add(temp[1].substring(0, index));
			}
		}
		System.out.println(splits);
		myVariables.update(splits);
	}*/
	
	/** 
	 * @return the GUI element CommandQueue
	 */
	public CommandQueue getQueue() {
		return myQueue;
	}
}
