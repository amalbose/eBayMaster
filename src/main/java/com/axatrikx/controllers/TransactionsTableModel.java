package com.axatrikx.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.axatrikx.beans.Buyer;
import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.beans.Transaction;
import com.axatrikx.beans.TransactionItem;
import com.axatrikx.db.DatabaseController;
import com.axatrikx.utils.ConfigValues;

public class TransactionsTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6932679990291788679L;

	private static Logger log = Logger.getLogger(TransactionsTableModel.class);

	private static String[] columnNames;
	private List<Transaction> transactions;
	private String queryString;

	public TransactionsTableModel(String queryString) throws Exception {
		this.queryString = queryString;
		getLatestData();
	}

	/**
	 * Query database to get the latest data.
	 * 
	 * @throws Exception
	 */
	private void getLatestData() throws Exception {
		QueryResultTable resultTable = new DatabaseController().executeQueryForResult(this.queryString);
		// process data
		Object[] objectArray = resultTable.getHeaderDetails().keySet().toArray();
		columnNames = Arrays.copyOf(objectArray, objectArray.length, String[].class);

		transactions = getTransactions(resultTable.getResultTable());
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return transactions.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		Object resultVal = null;
		/*
		 * Checking for the column name and returns the value.
		 */
		if (columnNames[col].equalsIgnoreCase(Transaction.getTransactionIDColumn())) {
			// Transaction ID
			resultVal = transactions.get(row).getTransactionId();
		} else if (columnNames[col].equalsIgnoreCase(TransactionItem.getItemIDColumn())) {
			// Transaction Item ID
			resultVal = transactions.get(row).getItem().getItemId();
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
		} else if (columnNames[col].equalsIgnoreCase(TransactionItem.getItemColumn())) {
			// Item Name
			resultVal = transactions.get(row).getItem().getItemName();
		} else if (columnNames[col].equalsIgnoreCase(TransactionItem.getCategoryColumn())) {
			// Item Category
			resultVal = transactions.get(row).getItem().getItemCategory();
		} else if (columnNames[col].equalsIgnoreCase(TransactionItem.getRateColumn())) {
			// Item Category
			resultVal = transactions.get(row).getItem().getItemRate();
		} else {
			log.warn("Unexpected value found: " + col + " in row: " + row);
		}
		return resultVal;
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		boolean isEditable = true;
		if (col < 2 || col > 7) { // TODO to be removed once updation of Items table is completed
			isEditable = false;
		}
		return isEditable;
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		System.out.println(getValueAt(row, 0) + " " + columnNames[col]);
		String table = "EBAYMASTERDB.TRANSACTIONS"; // TODO change
		if (table.equalsIgnoreCase("EBAYMASTERDB.TRANSACTIONS")) {
			if(getColumnClass(col).equals(String.class)) {
				value = "'"+value+"'";
			}
			String query = "UPDATE " + table + " SET " + columnNames[col] + " = " + value + " WHERE " + columnNames[0]
					+ " = " + getValueAt(row, 0);
			try {
				new DatabaseController().executeQuery(query);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fireTableCellUpdated(row, col);
		try {
			getLatestData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Transaction> getTransactions(ArrayList<ArrayList<String>> tableData) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction curTransaction; // temp transaction to store the current transaction.
		TransactionItem curItem; // temp transaction item
		Buyer curBuyer; // temp buyer

		/*
		 * Looping through transaction and creating a Transaction object.
		 */
		for (ArrayList<String> rawTransaction : tableData) {
			curTransaction = new Transaction();
			/*
			 * Transaction Item and Buyer are set and their member values are updated based on the column names.
			 */
			curItem = new TransactionItem();
			curBuyer = new Buyer();
			// setting item and buyer for the current transaction.
			curTransaction.setBuyer(curBuyer);
			curTransaction.setItem(curItem);
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
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getItemIDColumn())) {
					// Transaction Item ID
					curTransaction.getItem().setItemId(Integer.parseInt(columnValue));
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
					}
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getItemColumn())) {
					// Item Name
					curTransaction.getItem().setItemName(columnValue);
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getCategoryColumn())) {
					// Item Category
					curTransaction.getItem().setItemCategory(columnValue);
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getRateColumn())) {
					// Item Category
					curTransaction.getItem().setItemRate(Float.parseFloat(columnValue));
				} else {
					log.warn("Unexpected value found: " + columnValue + " in index: " + columnIndex);
				}
				// incrementing columnIndex
				columnIndex++;
				// add current transaction to the list.
			}
			transactions.add(curTransaction);
		}
		System.out.println(transactions.size());
		return transactions;
	}

	public static String[] getColumnNames() {
		return columnNames;
	}

}
