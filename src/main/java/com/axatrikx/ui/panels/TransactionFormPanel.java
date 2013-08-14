package com.axatrikx.ui.panels;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class TransactionFormPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;

	/**
	 * Create the panel.
	 */
	public TransactionFormPanel(List<String> categories) {
		setSize(new Dimension(800, 100));
		setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow][][]", "[][][]"));
		
		JLabel lblTransactionItem = new JLabel("Transaction Item");
		add(lblTransactionItem, "cell 0 1");
		
		JLabel lblCategory = new JLabel("Category");
		add(lblCategory, "cell 2 1");
		
		JLabel lblRate = new JLabel("Rate");
		add(lblRate, "cell 4 1");
		
		JLabel lblBuyer = new JLabel("Buyer");
		add(lblBuyer, "cell 6 1");
		
		JLabel lblLocation = new JLabel("Location");
		add(lblLocation, "cell 8 1");
		
		JLabel lblDate = new JLabel("Date");
		add(lblDate, "cell 10 1");
		
		JLabel lblCost = new JLabel("Cost");
		add(lblCost, "cell 12 1");
		
		JLabel lblPrice = new JLabel("Price");
		add(lblPrice, "cell 14 1");
		
		JLabel lblProfit = new JLabel("Profit");
		add(lblProfit, "cell 16 1");
		
		textField = new JTextField();
		add(textField, "cell 0 2,alignx left");
		textField.setColumns(10);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setEditable(true);
		for(String category : categories) {
			comboBox.addItem(category);
		}
		add(comboBox, "cell 2 2,growx");
		
		textField_1 = new JTextField();
		add(textField_1, "cell 4 2,alignx left");
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		add(textField_2, "cell 6 2,growx");
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		add(textField_3, "cell 8 2,alignx left");
		textField_3.setColumns(10);
		
		textField_6 = new JTextField();
		add(textField_6, "cell 10 2,alignx left");
		textField_6.setColumns(10);
		
		textField_4 = new JTextField();
		add(textField_4, "cell 12 2,alignx left");
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		add(textField_5, "cell 14 2,growx");
		textField_5.setColumns(10);
		
		JLabel lblProfitvalue = new JLabel("0");
		add(lblProfitvalue, "cell 16 2");

	}

}
