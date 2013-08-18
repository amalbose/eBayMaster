package com.axatrikx.ui.panels;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jbundle.util.jcalendarbutton.JCalendarButton;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.axatrikx.beans.TransactionItem;
import com.axatrikx.controllers.TransactionController;
import com.axatrikx.controllers.TransactionItemController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.ConfigValues;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TransactionFormPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -202660505350614300L;
	private JTextField rateTF;
	private JTextField buyerNameTF;
	private JTextField locationTF;
	private JTextField costTF;
	private JTextField priceTF;
	private JTextField dateTF;
	private JComboBox<String> itemNameCB;
	private JComboBox<String> categoryCB;
	private TransactionItemController itemController;

	/**
	 * Create the panel.
	 */
	public TransactionFormPanel() {

		try {
			itemController = new TransactionItemController();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DataBaseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DatabaseTableCreationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		List<String> categories = null;
		try {
			categories = itemController.getCategories();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DataBaseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DatabaseTableCreationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		List<String> items = null;
		try {
			items = itemController.getItems();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DataBaseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (DatabaseTableCreationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

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

		
		itemNameCB = new JComboBox<String>();
		itemNameCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) {
					TransactionItem item = itemController.getDetailByName(itemNameCB.getEditor().getItem().toString());
					if (item != null) {
						categoryCB.setSelectedItem(item.getItemCategory());
					}
				}
			}
		});
		itemNameCB.setEditable(true);
		itemNameCB.addItem("");
		if (items != null) {
			for (String item : items) {
				itemNameCB.addItem(item);
			}
		}
		AutoCompleteDecorator.decorate(itemNameCB);
		add(itemNameCB, "cell 0 2,alignx left");

		categoryCB = new JComboBox<String>();
		categoryCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) {
					TransactionItem item = itemController.getDetailByCategory(categoryCB.getEditor().getItem().toString());
					if (item != null) {
						rateTF.setText(String.valueOf(item.getItemRate()));
					}
				}
			}
		});
		categoryCB.setEditable(true);
		categoryCB.addItem("");
		if (categories != null) {
			for (String category : categories) {
				categoryCB.addItem(category);
			}
		}
		AutoCompleteDecorator.decorate(categoryCB);
		add(categoryCB, "cell 2 2,growx");

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
				String itemName = itemNameCB.getSelectedItem().toString();
				String buyerName = buyerNameTF.getText();
				String location = locationTF.getText();
				String category = categoryCB.getSelectedItem().toString();
				boolean isCategoryNew = false, isItemNew = false;
				try {
					isCategoryNew = itemController.getCategories().contains(category);
					isItemNew = itemController.getItems().contains(itemName);
				} catch (ClassNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (DataBaseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (DatabaseTableCreationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				float cost = Float.parseFloat(costTF.getText().toString());
				float price = Float.parseFloat(priceTF.getText().toString());
				float rate = Float.parseFloat(rateTF.getText().toString());
				Date date = null;
				try {
					date = new SimpleDateFormat(ConfigValues.UI_DATE_FORMAT.toString()).parse(dateTF.getText()
							.toString());
					boolean isSuccessful = new TransactionController().insertTransaction(buyerName, location, cost,
							price, date, itemName, category, rate);

					/*
					 * If transaction is successful, add the values to combobox if not present
					 */
					if (isSuccessful) {
						if (!isCategoryNew) {
							categoryCB.revalidate();
							categoryCB.addItem(category);
						}
						if (!isItemNew) {
							itemNameCB.revalidate();
							itemNameCB.addItem(itemName);
						}
					}
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
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		add(btnSave, "cell 16 2");
	}
}
