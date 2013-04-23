package control.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

//import control.communication.CommandMessage;

/**
 * User interface class (JPanel) responsible for containing JList object displaying queued commands.
 * The panel also contains controls for stepping through each command, executing commands, and deleting
 * commands from the queue, accomplished through the use of JButtons.
 * <p>
 * TODO: Create the ActionListener objects for each of the GUI elements contained in this panel
 * <br>
 * TODO: Format text in myQueue (translate commands to readable English)
 * @author Steph
 * @author Sarina
 */

public class CommandQueue extends JPanel{
	
	/**
	 * Title label for queue, to read: "Command Queue"
	 */
	private JLabel commandLabel;
	
	/**
	 * JList object containing names of commands to be executed
	 */
	private JList<Object> myQueue;
	
	/**
	 * JButton object responsible for stepping through commands in queue
	 */
	private JButton myStep;
	
	/**
	 * JButton object responsible for executing the selected command
	 */
	private JButton myExecute;
	
	/**
	 * JButton object responsible for deleting the selected command from the queue
	 */
	private JButton myDelete;
	
	/**
	 * List model for {@link CommandQueue#myQueue}
	 */
	private DefaultListModel<Object> myList;
	
	/**
	 * Default constructor for CommandQueue.
	 * <p>
	 * Responsible for setting appearance of the panel and initializing graphical components, 
	 * as well establishing their properties and action listeners
	 */
	public CommandQueue() {
		
		// Set size and layout of panel
		setPreferredSize(new Dimension(250, 220));
		setLayout(new FlowLayout(FlowLayout.LEADING, 10, 3));
		
		// Set title label for the panel
		commandLabel = new JLabel("Command Queue");
		commandLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		// Instantiate myList
		myList = new DefaultListModel<Object>();
		
		// Instantiate and set style for myQueue
		myQueue = new JList();
		myQueue.setFont(new Font("Arial", Font.PLAIN, 13));
		myQueue.setVisibleRowCount(8);
		//myQueue.setColumns(15);
		//myQueue.setLineWrap(true);
		//myQueue.setEditable(false);
		//myQueue.setPreferredSize(new Dimension(235, 150));
		myQueue.setAutoscrolls(true);
		// add in scroll bars, set auto scroll
		JScrollPane sp = new JScrollPane(myQueue);
		sp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		sp.setPreferredSize(new Dimension(232, 150));
		
		//DefaultCaret caret = (DefaultCaret)myQueue.getCaret();
		//caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		sp.setAutoscrolls(true); 
		
		// Instantiate containing panel for buttons as well as buttons themselves
		JPanel buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(235, 40));
		buttons.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 3));
		
		// Step control button
		myStep = new JButton("Step");
		myStep.setFont(new Font("Arial", Font.PLAIN, 13));
		myStep.setPreferredSize(new Dimension(77, 35));
		
		// Execute control button
		myExecute = new JButton("Execute");
		myExecute.setFont(new Font("Arial", Font.PLAIN, 12));
		myExecute.setPreferredSize(new Dimension(77, 35));
		
		// Delete control button
		myDelete = new JButton("Delete");
		myDelete.setFont(new Font("Arial", Font.PLAIN, 13));
		myDelete.setPreferredSize(new Dimension(77, 35));
		
		// Add buttons to buttons panel
		buttons.add(myStep);
		buttons.add(myExecute);
		buttons.add(myDelete);
		
		// Add components to main panel
		add(commandLabel);
		add(sp);
		add(buttons);
		
	}
	
	/**
	 * Simple Getter
	 * @return {@link CommandQueue#myList} (List model for {@link CommandQueue#myQueue})
	 */
	public DefaultListModel<Object> getMyList() {
		return myList;
	}
	
	/**
	 * Simple Getter
	 * @return {@link CommandQueue#myQueue} (JList object containing names of commands to be executed)
	 */
	public JList<Object> getQueue() {
		return myQueue;
	}
	
	/**
	 * Simple Getter
	 * @return {@link CommandQueue#myStep} (JButton object responsible for stepping through commands in queue)
	 */
	public JButton getStep() {
		return myStep;
	}
	
	/**
	 * Simple Getter
	 * @return {@link CommandQueue#myExecute} (JButton object responsible for executing the selected command)
	 */
	public JButton getExecute() {
		return myExecute;
	}
	
	/**
	 * Simple Getter
	 * @return {@link CommandQueue#myDelete} (JButton object responsible for deleting the selected command from the queue)
	 */
	public JButton getDelete() {
		return myDelete;
	}

	//public void addMessage(CommandMessage s) {
	//	// TODO add command message to displayed JLIST 
	//	myList.addElement(s.getCommand());
	//	myQueue.setModel(myList);
	//}

}
