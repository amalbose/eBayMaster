package com.axatrikx.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.axatrikx.beans.Buyer;
import com.axatrikx.beans.Category;
import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.beans.Transaction;
import com.axatrikx.db.DatabaseController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.ConfigValues;
import com.axatrikx.utils.Utils;

public class TransactionsTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 6932679990291788679L;

	private static final int TRANSACTIONID_ROW = 0;

	private static final String TRANSACTIONS_TABLE = "EBAYMASTERDB.TRANSACTIONS";

	private static Logger log = Logger.getLogger(TransactionsTableModel.class);

	private static final String COLUMN_NAME_TKN = "<COLUMN_NAME>";
	private static final String COLUMN_VALUE_TKN = "<COLUMN_VALUE>";
	private static final String COLUMN_KEY_TKN = "<COLUMN_KEY>";
	private static final String COLUMN_KEY_VALUE_TKN = "<COLUMN_KEY_VALUE>";

	private static final String UPDATE_TRANSACTIONS_TKN = "UPDATE_TRANSACTIONS";

	private static String[] columnNames;
	private List<Transaction> transactions;
	private String queryString;

	public TransactionsTableModel(String queryString) throws ClassNotFoundException, DataBaseException,
			DatabaseTableCreationException {
		this.queryString = queryString;
		getLatestData();
	}

	/**
	 * Query database to get the latest data.
	 * 
	 * @throws DatabaseTableCreationException
	 * @throws DataBaseException
	 * @throws ClassNotFoundException
	 * 
	 * @throws Exception
	 */
	private void getLatestData() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		QueryResultTable resultTable = new DatabaseController().executeQueryForResult(this.queryString);
		// process data
		Object[] objectArray = resultTable.getHeaderDetails().keySet().toArray();
		columnNames = Arrays.copyOf(objectArray, objectArray.length, String[].class);
		transactions = getTransactions(resultTable.getResultTable());
	}

	public void updateLatestData() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		getLatestData();
		fireTableDataChanged();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return transactions.size();
	}

	public String getColumnName(int col) {
		return getHeaderName(columnNames[col]);
	}

	private String getHeaderName(String columnName) {
		String headerName = columnName;

		if (columnName.equalsIgnoreCase(Buyer.getBuyerColumn())) {
			// Buyer name
			headerName = Buyer.BUYER_HEADER;
		} else if (columnName.equalsIgnoreCase(Buyer.getLocationColumn())) {
			// Location
			headerName = Buyer.LOCATION_HEADER;
		} else if (columnName.equalsIgnoreCase(Transaction.getCostColumn())) {
			// Cost
			headerName = Transaction.COST_HEADER;
		} else if (columnName.equalsIgnoreCase(Transaction.getPriceColumn())) {
			// Price
			headerName = Transaction.PRICE_HEADER;
		} else if (columnName.equalsIgnoreCase(Transaction.getProfitColumn())) {
			// Profit
			headerName = Transaction.PROFIT_HEADER;
		} else if (columnName.equalsIgnoreCase(Transaction.getDateColumn())) {
			// Date
			headerName = Transaction.DATE_HEADER;
		} else if (columnName.equalsIgnoreCase(Transaction.getItemNameColumn())) {
			// Item Name
			headerName = Transaction.ITEMNAME_HEADER;
		} else if (columnName.equalsIgnoreCase(Category.getCategoryNameColumn())) {
			// Category
			headerName = Category.CATEGORY_HEADER;
		} else if (columnName.equalsIgnoreCase(Category.getRateColumn())) {
			// Rate
			headerName = Category.RATE_HEADER;
		}
		return headerName;
	}

	public Object getValueAt(int row, int col) {
		Object resultVal = null;
		/*
		 * Checking for the column name and returns the value.
		 */
		if (columnNames[col].equalsIgnoreCase(Transaction.getTransactionIDColumn())) {
			// Transaction ID
			resultVal = transactions.get(row).getTransactionId();
		} else if (columnNames[col].equalsIgnoreCase(Category.getCategoryIdColumn())) {
			// Category ID
			resultVal = transactions.get(row).getCategory().getCategoryId();
		} else if (columnNames[col].equalsIgnoreCase(Buyer.getBuyerColumn())) {
			// Buyer name
			resultVal = transactions.get(row).getBuyer().getBuyerName();
		} else if (columnNames[col].equalsIgnoreCase(Buyer.getLocationColumn())) {
			// Location
			resultVal = transactions.get(row).getBuyer().getLocation();
		} else if (columnNames[col].equalsIgnoreCase(Transaction.getCostColumn())) {
			// Cost
			resultVal = transactions.get(row).getCost();
		} else if (columnNames[col].equalsIgnoreCase(Transaction.getPriceColumn())) {
			// Price
			resultVal = transactions.get(row).getPrice();
		} else if (columnNames[col].equalsIgnoreCase(Transaction.getProfitColumn())) {
			// Profit
			resultVal = transactions.get(row).getProfit();
		} else if (columnNames[col].equalsIgnoreCase(Transaction.getDateColumn())) {
			// Date
			resultVal = transactions.get(row).getDate();
		} else if (columnNames[col].equalsIgnoreCase(Transaction.getItemNameColumn())) {
			// Item Name
			resultVal = transactions.get(row).getItemName();
		} else if (columnNames[col].equalsIgnoreCase(Category.getCategoryNameColumn())) {
			// Category
			resultVal = transactions.get(row).getCategory().getCategoryName();
		} else if (columnNames[col].equalsIgnoreCase(Category.getRateColumn())) {
			// Rate
			resultVal = transactions.get(row).getCategory().getRate();
		} else {
			log.warn("Unexpected value found: " + columnNames[col] + " in row: " + row);
		}
		return resultVal;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		boolean isEditable = false;
		int[] indexes = getColumnIndexes(TRANSACTIONS_TABLE);
		int size = indexes.length;
		for (int i = 0; i < size; i++) {
			if (col == indexes[i]) {
				isEditable = true;
				break;
			}
		}
		return isEditable;
	}

	public void setValueAt(Object value, int row, int col) {
		if (getColumnClass(col).equals(String.class)) {
			value = "'" + value + "'";
		} else if (getColumnClass(col).equals(Date.class)) {
			try {
				value = "'"
						+ Utils.changeDateFormat(ConfigValues.CALENDAR_DATE_FORMAT.toString(),
								ConfigValues.DATE_FORMAT.toString(), value.toString()) + "'";
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Invalid Date", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		String query = TransactionController.getDBUpdateQuery(UPDATE_TRANSACTIONS_TKN)
				.replace(DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName())
				.replace(COLUMN_NAME_TKN, columnNames[col]).replace(COLUMN_VALUE_TKN, value.toString())
				.replace(COLUMN_KEY_TKN, columnNames[0])
				.replace(COLUMN_KEY_VALUE_TKN, getValueAt(row, TRANSACTIONID_ROW).toString());
		try {
			new DatabaseController().executeQuery(query);
			fireTableCellUpdated(row, col);
			getLatestData();
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
		} catch (DatabaseTableCreationException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Gets the column indexes of the given table.
	 * 
	 * @param columnIndex
	 * @return
	 */
	public int[] getColumnIndexes(String tableName) {
		return new int[] { 0, 1, 5, 6, 7, 8, 9, 10 };
	}

	/**
	 * Parse the tableData in 2D arraylist format to list of transaction objects.
	 * 
	 * @param tableData
	 * @return
	 */
	private List<Transaction> getTransactions(ArrayList<ArrayList<String>> tableData) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction curTransaction; // temp transaction to store the current transaction.
		Category curCategory; // temp transaction item
		Buyer curBuyer; // temp buyer

		/*
		 * Looping through transaction and creating a Transaction object.
		 */
		for (ArrayList<String> rawTransaction : tableData) {
			curTransaction = new Transaction();
			/*
			 * Transaction Item and Buyer are set and their member values are updated based on the column names.
			 */
			curCategory = new Category();
			curBuyer = new Buyer();
			// setting item and buyer for the current transaction.
			curTransaction.setBuyer(curBuyer);
			curTransaction.setCategory(curCategory);
			/*
			 * Loops through each column and update the values to the Transaction object.
			 */
			int columnIndex = 0;
			for (String columnValue : rawTransaction) {
				/*
				 * Checking for each column and updating the corresponding values.
				 */
				if (columnNames[columnIndex].equalsIgnoreCase(Transaction.getTransactionIDColumn())) {
					// Transaction ID
					curTransaction.setTransactionId(Integer.parseInt(columnValue));
				} else if (columnNames[columnIndex].equalsIgnoreCase(Category.getCategoryIdColumn())) {
					// Category ID
					curTransaction.getCategory().setCategoryId(Integer.parseInt(columnValue));
				} else if (columnNames[columnIndex].equalsIgnoreCase(Buyer.getBuyerColumn())) {
					// Buyer name
					curTransaction.getBuyer().setBuyerName(columnValue);
				} else if (columnNames[columnIndex].equalsIgnoreCase(Buyer.getLocationColumn())) {
					// Location
					curTransaction.getBuyer().setLocation(columnValue);
				} else if (columnNames[columnIndex].equalsIgnoreCase(Transaction.getCostColumn())) {
					// Cost
					curTransaction.setCost(Float.parseFloat(columnValue));
				} else if (columnNames[columnIndex].equalsIgnoreCase(Transaction.getPriceColumn())) {
					// Price
					curTransaction.setPrice(Float.parseFloat(columnValue));
				} else if (columnNames[columnIndex].equalsIgnoreCase(Transaction.getProfitColumn())) {
					// Profit
					curTransaction.setProfit(Float.parseFloat(columnValue));
				} else if (columnNames[columnIndex].equalsIgnoreCase(Transaction.getDateColumn())) {
					// Date
					try {
						curTransaction.setDate(new SimpleDateFormat(ConfigValues.DATE_FORMAT.toString())
								.parse(columnValue));
					} catch (ParseException e) {
						log.error("Error while parsing date - " + columnValue, e);
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error while parsing date : "
								+ e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
					}
				} else if (columnNames[columnIndex].equalsIgnoreCase(Transaction.getItemNameColumn())) {
					// Item Name
					curTransaction.setItemName(columnValue);
				} else if (columnNames[columnIndex].equalsIgnoreCase(Category.getCategoryNameColumn())) {
					// Item Category
					curTransaction.getCategory().setCategoryName(columnValue);
				} else if (columnNames[columnIndex].equalsIgnoreCase(Category.getRateColumn())) {
					// Item Category
					curTransaction.getCategory().setRate(Float.parseFloat(columnValue));
				} else {
					log.warn("Unexpected value found: " + columnValue + " in index: " + columnIndex);
				}
				// incrementing columnIndex
				columnIndex++;
				// add current transaction to the list.
			}
			transactions.add(curTransaction);
		}
		return transactions;
	}

	/**
	 * Returns the column names as array.
	 * 
	 * @return
	 */
	public static String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * Returns the index of given column.
	 * 
	 * @param columnName
	 * @return
	 */
	public static int getColumnIndex(String columnName) {
		int index = -1;
		for (int i = 0; i < columnNames.length; i++) {
			if (columnName.equalsIgnoreCase(columnNames[i])) {
				index = i;
				break;
			}
		}
		if (index == -1) {
			log.error("Could not find index for columnName " + columnName);
		}
		return index;
	}

}
