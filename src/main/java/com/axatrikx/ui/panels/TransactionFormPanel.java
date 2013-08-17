package com.axatrikx.ui.panels;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jbundle.util.jcalendarbutton.JCalendarButton;

import com.axatrikx.controllers.TransactionController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.ConfigValues;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransactionFormPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -202660505350614300L;
	private JTextField itemNameTF;
	private JTextField rateTF;
	private JTextField buyerNameTF;
	private JTextField locationTF;
	private JTextField costTF;
	private JTextField priceTF;
	private JTextField dateTF;

	/**
	 * Create the panel.
	 */
	public TransactionFormPanel(List<String> categories) {
		setLayout(new MigLayout("", "[][][][][][][][][][][][][][][][][][][]", "[][][]"));

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
		add(lblProfit, "cell 15 1,alignx left");

		itemNameTF = new JTextField();
		add(itemNameTF, "cell 0 2,alignx left");
		itemNameTF.setColumns(10);

		final JComboBox<String> categoryCB = new JComboBox<String>();
		categoryCB.setEditable(true);
		for (String category : categories) {
			categoryCB.addItem(category);
		}
		add(categoryCB, "cell 2 2,growx");

		rateTF = new JTextField();
		add(rateTF, "cell 4 2,alignx left");
		rateTF.setColumns(7);

		buyerNameTF = new JTextField();
		add(buyerNameTF, "cell 6 2,growx");
		buyerNameTF.setColumns(10);

		locationTF = new JTextField();
		add(locationTF, "cell 8 2,alignx left");
		locationTF.setColumns(10);

		dateTF = new JTextField();
		add(dateTF, "cell 10 2,alignx left");
		dateTF.setColumns(15);

		JCalendarButton calendarButton = new JCalendarButton();
		calendarButton.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue() instanceof Date) {
					DateFormat defaultDf = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);
					try {
						dateTF.setText(defaultDf.format(new SimpleDateFormat(ConfigValues.CALENDAR_DATE_FORMAT
								.toString()).parse(evt.getNewValue().toString())));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		add(calendarButton, "cell 11 2");

		costTF = new JTextField();
		add(costTF, "cell 12 2,alignx left");
		costTF.setColumns(7);

		priceTF = new JTextField();
		add(priceTF, "cell 14 2,alignx left");
		priceTF.setColumns(7);

		JLabel lblProfitvalue = new JLabel("0");
		add(lblProfitvalue, "cell 15 2,alignx left");

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String itemName = itemNameTF.getText();
				String buyerName = buyerNameTF.getText();
				String location = locationTF.getText();
				String category = categoryCB.getSelectedItem().toString();
				float cost = Float.parseFloat(costTF.getText().toString());
				float price = Float.parseFloat(priceTF.getText().toString());
				Date date = null;
				try {
					date = new SimpleDateFormat(ConfigValues.UI_DATE_FORMAT.toString()).parse(dateTF.getText().toString());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					new TransactionController().insertTransaction(buyerName, location, cost, price, date, itemName,
							category);
					TransactionsPanel.updateTableData();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataBaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DatabaseTableCreationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(btnSave, "cell 16 2");
	}
}
