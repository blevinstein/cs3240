package control.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author slc4ga
 */
 
 /**
 * @author 
 * Brian Levinstein(bpw7xx), 
 * Yuchen Zou (yz2ak), 
 * Neal Milstein (nrm2zf)
 * Bryan Walsh (bpl4ab)
 * Alex Weaver (baw4ux)
 */

/**
 * Creates a GUI to displaying the following variables that govern the robot's movement 
 * and telemetry data: gps coordinates, light intensity, sound intensity, touch sensitivity,
 * ultrasonic detection, connection check, motor a/b/c speed
 */
public class ProgramVariables extends JPanel{
	
	//Set up GUI elements for ProgramVariables
	private JLabel variableLabel;
	private JTable myTable;
	private Object[][] myVariables;
	private JButton requestUpdate;
	
	/**
	 * Method creates the ProgramVariables GUI. The Table object myTable is filled using a 2-D 
	 * array myVarriables. It is then wrapped with JScrollPane to give it scrolling capabilities.
	 * A JButton requestUpdate is implemented in the GUI to allow refreshing of updated program variables.
	 */
	public ProgramVariables() {
		
		setLayout(new FlowLayout(FlowLayout.LEADING, 8, 5));
		setPreferredSize(new Dimension(250, 410));
		
		variableLabel = new JLabel("Program Variables");
		variableLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		String[] cols = {"Variable", "Value"};
		Object[][] variables = {{"Location", new Integer(5)},{"Light", "50%"}, 
				{"Sound", "15%"}, {"Touch", new Boolean(false)}, {"Claw Position", new Double(.5)},
				{"Orientation", "N"}, {"Speed", new Integer(5)}, {"Ultrasonic", new Integer(12)}, 
				{"Connection", new Boolean(true)}, {"Motor A", new Integer(37)},
				{"Motor B", new Integer(89)}, {"Motor C", new Integer(45)}};
		myVariables = new Object[variables.length][variables[0].length];
		for(int x = 0; x < variables.length; x++) {
			for(int y = 0; y < variables[0].length; y++) {
				myVariables[x][y] = variables[x][y];
			}
		}
		DefaultTableModel tableModel = new DefaultTableModel(myVariables, cols);
		myTable = new JTable(tableModel);
		myTable.setFont(new Font("Arial", Font.PLAIN, 13));
		myTable.setPreferredSize(new Dimension(125, 310));
		
		for(int i = 0; i < myVariables[0].length; i++){
			TableColumn col = myTable.getColumnModel().getColumn(i);
			col.setPreferredWidth(20);
		}
		myTable.setRowHeight(26);
		
		JScrollPane sp = new JScrollPane(myTable);
		sp.setPreferredSize(new Dimension(235, 335));
		sp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
		
		requestUpdate = new JButton("Request Update");
		requestUpdate.setFont(new Font("Arial", Font.PLAIN, 13));
		requestUpdate.setPreferredSize(new Dimension(235, 35));
		
		add(variableLabel);
		add(sp);
		add(requestUpdate);
		
	}
	
	/**
	 * @return the label of the GUI
	 */
	public JLabel getVariableLabel() {
		return variableLabel;
	}
	
	/**
	 * @return the table GUI element
	 */
	public JTable getMyTable() {
		return myTable;
	}

	/**
	 * @return the 2D array containing all the program variables and their values
	 */
	public Object[] getMyVariables() {
		return myVariables;
	}
	
	/**
	 * @return the button GUI element
	 */
	public JButton getRequestUpdate() {
		return requestUpdate;
	}

	/**
	 * Updates the values in myVariables and refreshes the JTable object.
	 * @param an array that contains updated robot variable values.
	 */
	public void update(ArrayList<Object> splits) {
		for(int x = 0; x < myVariables.length; x++) {
			//model.setValueAt(splits.get(x), x, 1);
			myVariables[x][1] = splits.get(x);
		}
		DefaultTableModel model = new DefaultTableModel(myVariables, new String[] {"Variable", "Value"} );
		myTable.setModel(model);
	}

}
