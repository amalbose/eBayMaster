package com.axatrikx.ui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;
import org.jbundle.util.jcalendarbutton.JCalendarButton;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.axatrikx.beans.Category;
import com.axatrikx.controllers.CategoryController;
import com.axatrikx.controllers.TransactionController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.ConfigValues;

public class TransactionFormPanel extends JPanel {

	private static final Logger log = Logger.getLogger(TransactionFormPanel.class);

	private static final long serialVersionUID = -202660505350614300L;
	private static final String DEFAULT_ITEM = "<Select Item>";
	private static final String DEFAULT_CATEGORY = "<Select Category>";

	/**
	 * Time duration for Saved label display.
	 */
	private static final long LABEL_HIDE_TIMEOUT = 2;

	private JLabel lblItemSaved;
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
	private JTextField profitTF;

	/**
	 * Create the panel.
	 */
	public TransactionFormPanel() {
		setBackground(Color.WHITE);

		List<String> categories = null;
		List<String> items = null;
		try {
			transactionController = new TransactionController();
			categoryController = new CategoryController();
			categories = categoryController.getCategories();
			items = transactionController.getItems();
		} catch (ClassNotFoundException e2) {
			log.error("Exception while creating controller", e2);
		} catch (DataBaseException e2) {
			log.error("Database exception", e2);
		} catch (DatabaseTableCreationException e2) {
			log.error("Exception while creating database tables", e2);
		}

		setLayout(new MigLayout("",
				"[grow][][grow][][grow][][grow][][grow][][left][left][][grow,left][][grow,left][grow][][][]", "[][][]"));

		JLabel lblAddNewTransaction = new JLabel("Add new transaction:");
		lblAddNewTransaction.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblAddNewTransaction, "cell 0 0");

		lblItemSaved = new JLabel("Transaction Saved");
		lblItemSaved.setVisible(false);

		final JLabel lblRateBetween = new JLabel("Rate between 0 and 1");

		lblRateBetween.setVisible(false);
		lblRateBetween.setForeground(Color.RED);
		add(lblRateBetween, "cell 3 0 4 1");
		lblItemSaved.setForeground(new Color(0, 128, 0));
		lblItemSaved.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblItemSaved, "cell 8 0,alignx right");

		JLabel lblTransactionItem = new JLabel("Transaction Item");
		add(lblTransactionItem, "cell 0 1");

		JLabel lblCategory = new JLabel("Category");
		add(lblCategory, "cell 2 1");

		JLabel lblRate = new JLabel("Rate (0 - 1)");
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

		NumberFormatter rateFormat = new NumberFormatter();
		rateFormat.setMinimum(new Float(0));
		rateFormat.setMaximum(new Float(1));

		rateTF = new JFormattedTextField(rateFormat);
		rateTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (Float.parseFloat(rateTF.getText()) > 1 || Float.parseFloat(rateTF.getText()) < 0) {
					showLabel(lblRateBetween);
				}
				if (!costTF.getText().trim().isEmpty() & !priceTF.getText().trim().isEmpty()) {
					profitTF.setText(String.valueOf(transactionController.calculateProfit(
							Float.parseFloat(costTF.getText()), Float.parseFloat(priceTF.getText()),
							Float.parseFloat(rateTF.getText()))));
				}
			}
		});
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
		itemNameCB.addItem(DEFAULT_ITEM);
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
		categoryCB.addItem(DEFAULT_CATEGORY);
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
						e.printStackTrace();
					}
				}
			}
		});
		add(calendarButton, "cell 11 2");

		profitTF = new JTextField("");
		profitTF.setColumns(7);
		profitTF.setEditable(false);
		add(profitTF, "cell 16 2,alignx left");

		JLabel costSymbol = new JLabel(Currency.getInstance(getLocale()).getSymbol());
		add(costSymbol, "flowx,cell 13 2,alignx left");

		NumberFormat floatFormat = NumberFormat.getNumberInstance();
		floatFormat.setMinimumFractionDigits(2);

		costTF = new JFormattedTextField(floatFormat);
		costTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if (!rateTF.getText().trim().isEmpty() & !priceTF.getText().trim().isEmpty()) {
					profitTF.setText(String.valueOf(transactionController.calculateProfit(
							Float.parseFloat(costTF.getText()), Float.parseFloat(priceTF.getText()),
							Float.parseFloat(rateTF.getText()))));
				}
			}
		});
		add(costTF, "cell 13 2,alignx left");
		costTF.setColumns(7);

		JLabel priceSymbol = new JLabel(Currency.getInstance(getLocale()).getSymbol());
		add(priceSymbol, "flowx,cell 15 2");

		priceTF = new JFormattedTextField(floatFormat);
		priceTF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!rateTF.getText().trim().isEmpty() & !costTF.getText().trim().isEmpty()) {
					profitTF.setText(String.valueOf(transactionController.calculateProfit(
							Float.parseFloat(costTF.getText()), Float.parseFloat(priceTF.getText()),
							Float.parseFloat(rateTF.getText()))));
				}
			}
		});
		add(priceTF, "cell 15 2,alignx left");
		priceTF.setColumns(7);

		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				saveTransaction();
			}

		});
		add(btnSave, "cell 17 2");
	}

	private void saveTransaction() {
		float cost = 0.0f, price = 0.0f, rate = 0.0f;
		String itemName = itemNameCB.getSelectedItem().toString();
		String buyerName = buyerNameTF.getText();
		String location = locationTF.getText();
		String category = categoryCB.getSelectedItem().toString();
		String costString = costTF.getText().toString().replace(",", "");
		String priceString = priceTF.getText().toString().replace(",", "");
		String rateString = rateTF.getText().toString().replace(",", "");
		String dateString = dateTF.getText().toString();

		if (isDataValid(itemName, category, costString, priceString, dateString, rateString)) {
			boolean isCategoryNew = false, isItemNew = false;
			try {
				isCategoryNew = categoryController.getCategories().contains(category);
				isItemNew = transactionController.getItems().contains(itemName);
			} catch (ClassNotFoundException e2) {
				log.error("Exception while creating controller", e2);
			} catch (DataBaseException e2) {
				log.error("Database exception", e2);
			} catch (DatabaseTableCreationException e2) {
				log.error("Exception while creating database tables", e2);
			}

			if (!costString.isEmpty()) {
				cost = Float.parseFloat(costString);
			}

			if (!priceString.isEmpty()) {
				price = Float.parseFloat(priceString);
			}

			if (!rateString.isEmpty()) {
				rate = Float.parseFloat(rateString);
			}
			Date date = null;
			try {
				date = new SimpleDateFormat(ConfigValues.UI_DATE_FORMAT.toString()).parse(dateString);
				boolean isSuccessful = new TransactionController().insertTransaction(buyerName, location, cost, price,
						date, itemName, category, rate);
				/*
				 * If transaction is successful, add the values to combobox if not present
				 */
				if (isSuccessful) {
					log.info("New Transaction item has been saved");

					showLabel(lblItemSaved);

					if (!isCategoryNew) {
						categoryCB.addItem(category);
						categoryCB.revalidate();
					}
					if (!isItemNew) {
						itemNameCB.addItem(itemName);
						itemNameCB.revalidate();
					}

					clearForm();
				}
				TransactionsPanel.updateTableData();
			} catch (ClassNotFoundException e1) {
				log.error("ClassNotFound Exception while getting controller", e1);
			} catch (SQLException e1) {
				log.error("SQL Exception while getting controller", e1);
			} catch (DataBaseException e1) {
				log.error("Database Exception while getting controller", e1);
			} catch (DatabaseTableCreationException e1) {
				log.error("Exception while creating database tables", e1);
			} catch (ParseException e1) {
				log.error("Exception while parsing Date", e1);
			}
		}
	}

	/**
	 * Clears the cost and price fields.
	 */
	private void clearForm() {
		costTF.setText("");
		priceTF.setText("");
		profitTF.setText("");
	}

	/**
	 * Validates the form data an returns True/False based on validation result.
	 * 
	 * @param itemName
	 * @param category
	 * @param costString
	 * @param priceString
	 * @param dateString
	 * @return
	 */
	private boolean isDataValid(String itemName, String category, String costString, String priceString,
			String dateString, String rateString) {
		if (itemName.trim().equals(DEFAULT_ITEM)) {
			JOptionPane.showMessageDialog(null,
					"Please select an Item from the list or create a new one by typing on the listbox.",
					"Invalid Item Name", JOptionPane.INFORMATION_MESSAGE);
			itemNameCB.requestFocus();
			return false;
		}
		if (category.trim().equals(DEFAULT_CATEGORY)) {
			JOptionPane.showMessageDialog(null,
					"Please select a Category from the list or create a new one by typing on the listbox.",
					"Invalid Category", JOptionPane.INFORMATION_MESSAGE);
			categoryCB.requestFocus();
			return false;
		}
		// parse rate
		try {
			Float rate = Float.parseFloat(rateString);
			if (rate < 0) {
				JOptionPane.showMessageDialog(null, "Rate should be a positive value. eg. 2.50", "Invalid Rate",
						JOptionPane.INFORMATION_MESSAGE);
				rateTF.requestFocus();
				return false;
			}
		} catch (NumberFormatException ne) {
			JOptionPane.showMessageDialog(null, "Please enter a proper value for Rate. eg. 2.50", "Invalid Rate",
					JOptionPane.INFORMATION_MESSAGE);
			rateTF.requestFocus();
			return false;
		}
		// parse date
		try {
			new SimpleDateFormat(ConfigValues.UI_DATE_FORMAT.toString()).parse(dateString);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "The date entered is invalid. Please select from the calender button.",
					"Invalid Date", JOptionPane.INFORMATION_MESSAGE);
			dateTF.requestFocus();
			return false;
		}

		// parse cost
		try {
			Float cost = Float.parseFloat(costString);
			if (cost < 0) {
				JOptionPane.showMessageDialog(null, "Actual Cost should be a positive value. eg. 250.99",
						"Invalid Actual Cost", JOptionPane.INFORMATION_MESSAGE);
				costTF.requestFocus();
				return false;
			}
		} catch (NumberFormatException ne) {
			JOptionPane.showMessageDialog(null, "Please enter a proper value for Actual Cost. eg. 250.99",
					"Invalid Actual Cost", JOptionPane.INFORMATION_MESSAGE);
			costTF.requestFocus();
			return false;
		}

		// parse price
		try {
			Float price = Float.parseFloat(priceString);
			if (price < 0) {
				JOptionPane.showMessageDialog(null, "Selling Price should be a positive value. eg. 250.99",
						"Invalid Selling Price", JOptionPane.INFORMATION_MESSAGE);
				priceTF.requestFocus();
				return false;
			}
		} catch (NumberFormatException ne) {
			JOptionPane.showMessageDialog(null, "Please enter a proper value for Selling Price. eg. 250.99",
					"Invalid Selling Price", JOptionPane.INFORMATION_MESSAGE);
			priceTF.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param labelName
	 */
	private void showLabel(JLabel labelName) {
		labelName.setVisible(true);
		hideLabel(labelName);
	}

	/**
	 * Hides the Saved label after a time duration.
	 */
	private void hideLabel(final JLabel labelName) {
		ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1);
		exec.schedule(new Runnable() {
			public void run() {
				labelName.setVisible(false);
			}
		}, LABEL_HIDE_TIMEOUT, TimeUnit.SECONDS);
	}
}
