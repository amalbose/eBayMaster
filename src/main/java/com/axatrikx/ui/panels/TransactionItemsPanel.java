package com.axatrikx.ui.panels;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;

public class TransactionItemsPanel extends JPanel {
	private static final long serialVersionUID = -2551210721481697627L;
	private JTextField nameField;
	private JTextField categoryField;
	private JTextField rateField;

	/**
	 * Create the panel.
	 */
	public TransactionItemsPanel() {
		setLayout(new MigLayout("", "[][][grow]", "[][][][][][][grow]"));
		
		JLabel lblName = new JLabel("Name");
		add(lblName, "cell 1 1,alignx left,growy");
		
		nameField = new JTextField();
		nameField.setToolTipText("Name of Item");
		add(nameField, "cell 2 1,alignx left");
		nameField.setColumns(25);
		
		JLabel lblDescription = new JLabel("Description");
		add(lblDescription, "cell 1 2,alignx left,growy");
		
		JTextArea descArea = new JTextArea();
		descArea.setToolTipText("Item Description");
		descArea.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		descArea.setColumns(25);
		descArea.setRows(3);
		add(descArea, "cell 2 2,aligny top");
		
		JLabel lblCategory = new JLabel("Category");
		add(lblCategory, "cell 1 3,alignx left,growy");
		
		categoryField = new JTextField();
		categoryField.setToolTipText("Item Category");
		add(categoryField, "cell 2 3,alignx left");
		categoryField.setColumns(25);
		
		JLabel lblRate = new JLabel("Rate");
		add(lblRate, "cell 1 4,alignx left,growy");
		
		rateField = new JTextField();
		rateField.setToolTipText("Item Rate");
		add(rateField, "cell 2 4,alignx left,growy");
		rateField.setColumns(10);
		
		JPanel panel = new JPanel();
		add(panel, "cell 2 6,grow");
		panel.setLayout(new MigLayout("", "[][][][][][][][][][]", "[]"));
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel, "cell 0 0,alignx left");
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel.add(horizontalStrut, "cell 1 0");
		
		JButton btnSave = new JButton("Save");
		panel.add(btnSave, "cell 2 0,alignx right");

	}

}
