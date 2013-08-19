package com.axatrikx.ui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

import org.jbundle.util.jcalendarbutton.JCalendarButton;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.axatrikx.beans.Category;
import com.axatrikx.controllers.TransactionController;
import com.axatrikx.controllers.CategoryController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.ConfigValues;

public class TransactionFormPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -202660505350614300L;
	private JFormattedTextField rateTF;
	private JTextField buyerNameTF;
	private JTextField locationTF;
	private JFormattedTextField costTF;
	private JFormattedTextField priceTF;
	private JTextField dateTF;
	private JComboBox<String> itemNameCB;
	private JComboBox<String> categoryCB;
	private CategoryController categoryController;
	private TransactionController transactionController;

	/**
	 * Create the panel.
	 */
	public TransactionFormPanel() {
		setBackground(Color.WHITE);

		try {
			transactionController = new TransactionController();
			categoryController = new CategoryController();
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
			categories = categoryController.getCategories();
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
			items = transactionController.getItems();
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

		setLayout(new MigLayout("",
				"[grow][][grow][][grow][][grow][][grow][][left][left][][grow,left][][grow,left][grow][][][]", "[][][]"));

		JLabel lblAddNewTransaction = new JLabel("Add new transaction:");
		lblAddNewTransaction.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblAddNewTransaction, "cell 0 0");

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

		JLabel lblCost = new JLabel("Actual Cost");
		add(lblCost, "cell 13 1,alignx left");

		JLabel lblPrice = new JLabel("Selling Price");
		add(lblPrice, "cell 15 1,alignx left");

		JLabel lblProfit = new JLabel("Profit");
		add(lblProfit, "cell 16 1,alignx left");

		NumberFormat floatFormat = NumberFormat.getNumberInstance();
		floatFormat.setMinimumFractionDigits(2);

		rateTF = new JFormattedTextField(floatFormat);
		add(rateTF, "cell 4 2,growx");
		rateTF.setColumns(7);

		buyerNameTF = new JTextField();
		add(buyerNameTF, "cell 6 2,growx");
		buyerNameTF.setColumns(10);

		locationTF = new JTextField();
		add(locationTF, "cell 8 2,growx");
		locationTF.setColumns(10);

		dateTF = new JTextField();
		add(dateTF, "cell 10 2,alignx left");
		dateTF.setColumns(17);
		SimpleDateFormat sdf = new SimpleDateFormat(ConfigValues.UI_DATE_FORMAT.toString());
		dateTF.setText(sdf.format(new Date().getTime()));

		itemNameCB = new JComboBox<String>();
		itemNameCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) {
					String category = transactionController.getCategoryFromItem(itemNameCB.getEditor().getItem()
							.toString());
					if (!category.isEmpty()) {
						categoryCB.setSelectedItem(category);
					}
				}
			}
		});
		itemNameCB.setEditable(true);
		itemNameCB.addItem("<Select Item>");
		if (items != null) {
			for (String item : items) {
				itemNameCB.addItem(item);
			}
		}
		AutoCompleteDecorator.decorate(itemNameCB);
		add(itemNameCB, "cell 0 2,growx");

		categoryCB = new JComboBox<String>();
		categoryCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == 1) {
					Category category = categoryController.getDetailByName(categoryCB.getEditor().getItem().toString());
					if (category != null) {
						rateTF.setText(String.valueOf(category.getRate()));
					}
				}
			}
		});
		categoryCB.setEditable(true);
		categoryCB.addItem("<Select Category>");
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

		JLabel costSymbol = new JLabel(Currency.getInstance(getLocale()).getSymbol());
		add(costSymbol, "flowx,cell 13 2,alignx left");

		costTF = new JFormattedTextField(floatFormat);
		add(costTF, "cell 13 2,alignx left");
		costTF.setColumns(7);

		JLabel priceSymbol = new JLabel(Currency.getInstance(getLocale()).getSymbol());
		add(priceSymbol, "flowx,cell 15 2");

		priceTF = new JFormattedTextField(floatFormat);
		add(priceTF, "cell 15 2,alignx left");
		priceTF.setColumns(7);

		JTextField profitTF = new JTextField("");
		profitTF.setColumns(7);
		profitTF.setEditable(false);
		add(profitTF, "cell 16 2,alignx left");

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				float cost = 0.0f, price = 0.0f, rate = 0.0f;
				String itemName = itemNameCB.getSelectedItem().toString();
				String buyerName = buyerNameTF.getText();
				String location = locationTF.getText();
				String category = categoryCB.getSelectedItem().toString();
				boolean isCategoryNew = false, isItemNew = false;
				try {
					isCategoryNew = categoryController.getCategories().contains(category);
					isItemNew = transactionController.getItems().contains(itemName);
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
				String costString = costTF.getText().toString();
				if (!costString.isEmpty()) {
					cost = Float.parseFloat(costString);
				}

				String priceString = priceTF.getText().toString();
				if (!priceString.isEmpty()) {
					price = Float.parseFloat(priceTF.getText().toString());
				}

				String rateString = rateTF.getText().toString();
				if (!rateString.isEmpty()) {
					rate = Float.parseFloat(rateTF.getText().toString());
				}
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
		add(btnSave, "cell 17 2");
	}
}
