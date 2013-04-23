package control.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * Class creates a GUI that is responsible for displaying the incoming messages from the robot.
 * The GUI constantly refreshes and listens for new messages and places them in the table when
 * new messages are received.
 */
public class RobotResponse extends JPanel{
	
	/* 
	 * GUI consists of a label and a table wrapped in JScrollPane for scrolling and 
	 * DefaultCaret for refreshing.
	 */
	private JLabel responseLabel;
	private JTextArea myResponses;
	
	//Setting up the RobotResponse GUI
	public RobotResponse() {
		
		setLayout(new FlowLayout(FlowLayout.LEADING, 5, 3));
		setPreferredSize(new Dimension(250, 150));
		
		responseLabel = new JLabel("Robot Responses");
		responseLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		myResponses = new JTextArea();
		myResponses.setFont(new Font("Arial", Font.PLAIN, 13));
		myResponses.setRows(7);
		myResponses.setColumns(25);
		myResponses.setLineWrap(true);
		myResponses.setEditable(false);
		// add in scroll bars, set auto scroll
		JScrollPane sp = new JScrollPane(myResponses);
		sp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		sp.setPreferredSize(new Dimension(232, 120));
		
		DefaultCaret caret = (DefaultCaret)myResponses.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		sp.getVerticalScrollBar().addAdjustmentListener( new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) { 
				e.getAdjustable().setValue( e.getAdjustable().getMaximum()); 
			} 
		});		
		
		add(responseLabel);
		add(sp);
	}
	
	/**
	 * @return the 7x25 table containing the robot's responses
	 */
	public JTextArea getMyResponses() {
		return myResponses;
	}

}
