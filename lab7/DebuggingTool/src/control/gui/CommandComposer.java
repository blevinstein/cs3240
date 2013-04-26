package control.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.*;

//import control.communication.CommandMessage;

/**
 * User interface class (JPanel) for the composition and issuing of possible commands. Allows
 * for the execution of the following commands:
 * <ul>
 * <li> INIT: Ensure that a BlueTooth connection exists
 * <li> MOVE: Set both left and right motor speed (value for both speeds to be specified by JSpinner)
 * <li> TURN: Rotate the robot (number of degrees specified by JSpinner)
 * <li> CLAW: Set value of claw motor (value to be specified by JSpinner)
 * <li> STOP: Stop robot movement
 * <li> QUERY: Request a response of all telemetry/motor data
 * <li> QUIT: Close the connection between the robot and base station
 * <li> ACKNOWLEDGE: Send acknowledgment message
 * </ul>
 * <p>
 * TODO: 
 * Generalize JSpinner, {@link CommandComposer#myDegrees}, so that it may be used to set
 * parameters for all applicable commands, not just TURN (ie. MOVE, etc...)
 * @author Steph
 */

public class CommandComposer extends JPanel{
	
	/**
	 * Title label, to read "Command Composer"
	 */
	private JLabel composerLabel;
	
	/**
	 * JComboBox object responsible for displaying and selecting commands:
	 * INIT, MOVE, TURN, CLAW, STOP, QUERY, QUIT, and ACKNOWLEDGE
	 */
	private JComboBox commandList;
	
	/**
	 * JSpinner object responsible for displaying and controlling degree value
	 * for TURN commands
	 */
	private JSpinner myDegrees;
	//private JCheckBox myTimestamp;
	
	/**
	 * JButton object responsible for issuing commands
	 */
	private JButton addCommand;
	
	/**
	 * Default Constructor for CommandComposer
	 * <p>
	 * Generates the user interface for selecting commands and their parameters, as well as issuing said commands. 
	 * When the command selected by {@link CommandComposer#commandList} is changed, 
	 * the JSpinner will update to allow for the selection of parameters for the newly-selected command.
	 * Finally, when {@link CommandComposer#addCommand} is pressed, it will issue the command with the
	 * specified parameters.
	 */
	public CommandComposer() {

		// Set size and layout of panel
		setPreferredSize(new Dimension(250, 170));
		setLayout(new FlowLayout(FlowLayout.LEADING, 8, 3));
		
		// Set title label message and style
		composerLabel = new JLabel("Command Composer");
		composerLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		// Populate commandList with commands and set style
		String[] commands = {"Init", "Move", "Turn", "Claw", "Stop", "Query", "Quit", "Acknowledge"};
		commandList = new JComboBox(commands);
		commandList.setFont(new Font("Arial", Font.PLAIN, 13));
		commandList.setPreferredSize(new Dimension(235, 40));
		commandList.setEditable(false);
		
		// Action listener for the change in JComboBox selection for commandList
		commandList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get selected action and set JSpinner accordingly
				JComboBox cb = (JComboBox) e.getSource();
				String command = (String) cb.getSelectedItem();
				if(command.equals("Init")) {
					myDegrees.setEnabled(false);	
				} else if(command.equals("Move")) {
					SpinnerModel model = new SpinnerNumberModel(0, -720, 720, 5);
					myDegrees.setModel(model);
					myDegrees.setValue(0);
					try {
						myDegrees.commitEdit();
					} catch (ParseException e1) { }
					myDegrees.setEnabled(true);
				} else if(command.equals("Turn")) {
					SpinnerModel model = new SpinnerNumberModel(0, -180, 180, 5);
					myDegrees.setModel(model);
					myDegrees.setValue(0);
					myDegrees.setEnabled(true);
				} else if(command.equals("Claw")) {
					SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 100, 5);	
					myDegrees.setModel(model);
					myDegrees.setValue(0);
					myDegrees.setEnabled(true);
				} else if(command.equals("Stop")) {
					myDegrees.setEnabled(false);
				} else if(command.equals("Query")) {
					myDegrees.setEnabled(false);	
				} else if(command.equals("Quit")) {
					myDegrees.setEnabled(false);	
				} else if(command.equals("Acknowledge")) {
					myDegrees.setEnabled(false);
				}
			}
		});
		
		// Parameter JLabel
		JLabel params = new JLabel("  Parameter");
		params.setFont(new Font("Arial", Font.PLAIN, 13));
		
		SpinnerModel model = new SpinnerNumberModel(0, -720, 720, 5);
		myDegrees = new JSpinner(model);
		myDegrees.setFont(new Font("Arial", Font.PLAIN, 13));
		myDegrees.setPreferredSize(new Dimension(90, 30));
		myDegrees.setEditor(new JSpinner.DefaultEditor(myDegrees));
		myDegrees.setEnabled(false);
		
		//myTimestamp = new JCheckBox("Include Timestamp");
		//myTimestamp.setFont(new Font("Arial", Font.PLAIN, 13));	
		
		
		// Issue selected command with specified parameters
		addCommand = new JButton("Send Command");
		addCommand.setFont(new Font("Arial", Font.PLAIN, 14));
		addCommand.setPreferredSize(new Dimension(235, 35));
		
		add(composerLabel);
		add(commandList);
		add(params);
		add(myDegrees);
		//add(myTimestamp);
		add(addCommand);
		
	}
	
	/**
	 * Simple Getter for {@link CommandComposer#commandList} (JComboBox 
	 * object responsible for displaying and selecting commands)
	 * @return {@link CommandComposer#commandList}
	 */
	public JComboBox getCommands() {
		return commandList;
	}
	
	/**
	 * Simple Getter for {@link CommandComposer#myDegrees} (JSpinner object 
	 * responsible for displaying and controlling degree value for TURN commands)
	 * @return {@link CommandComposer#myDegrees}
	 */
	public JSpinner getDegrees() {
		return myDegrees;
	}
	
	/*public boolean getTimestamp() {
		return myTimestamp.isSelected();
	}*/
	
	/**
	 * Simple Getter for {@link CommandComposer#addCommand} (JButton object
	 * responsible for issuing commands)
	 * @return {@link CommandComposer#addCommand}
	 */
	public JButton getAddButton() {
		return addCommand;
	}

}
