package com.axatrikx.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	private String[] columnNames;
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
		DatabaseController dbController = new DatabaseController();
		QueryResultTable resultTable = dbController.executeQueryForResult(this.queryString);
		// process data
		this.columnNames = (String[]) resultTable.getHeaderDetails().keySet().toArray();

		resultTable.getResultTable();
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
		return transactions.get(row).getBuyer(); // TODO Correct
		// return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	public boolean isCellEditable(int row, int col) {
		// Note that the data/cell address is constant,
		// no matter where the cell appears onscreen.
		if (col < 2) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void setValueAt(Object value, int row, int col) {
		// data[row][col] = (String) value;
		// TODO modify the value and update DB
		fireTableCellUpdated(row, col);
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
				} else {
					log.warn("Unexpected value found: " + columnValue + " in index: " + columnIndex);
				}

				// incrementing columnIndex
				columnIndex++;
				// add current transaction to the list.
				transactions.add(curTransaction);
			}
		}

		return transactions;
	}

}
