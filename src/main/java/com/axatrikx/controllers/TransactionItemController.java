package com.axatrikx.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.beans.Response;
import com.axatrikx.beans.TransactionItem;
import com.axatrikx.db.DatabaseController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.PreparedDataExecutor;

/**
 * The controller class which controls the validation and saving of Transaction items.
 * 
 * @author Amal Bose B S
 * 
 */
public class TransactionItemController {

	private static Logger log = Logger.getLogger(TransactionItemController.class);

	private List<TransactionItem> transactionItems;

	private static final String QUERY_ITEM_WITH_NAME_TKN = "QUERY_ITEM_WITH_NAME";
	private static final String QUERY_ITEMS_TABLE_TKN = "QUERY_ITEMS_TABLE";
	private static final String QUERY_ALL_ITEMS_TKN = "QUERY_ALL_ITEMS";
	private static final String QUERY_ALL_CATEGORIES_TKN = "QUERY_ALL_CATEGORIES";

	/**
	 * Constructor which sets the transactionItems list.
	 * 
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	public TransactionItemController() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		this.transactionItems = getAllTransactionItems();
	}

	/**
	 * Saves the transaction item to file if data is valid.
	 * 
	 * @return Returns Response object based on whether item is saved or not.
	 */
	public Response saveTransactionItem() {
		Response response = validateData();
		if (response.isValid()) {
			saveItemToFile();
		}
		return response;
	}

	private void saveItemToFile() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("rawtypes")
	public TransactionItem getItem(String itemName) throws ClassNotFoundException, SQLException, DataBaseException,
			DatabaseTableCreationException {
		String query = TransactionController.getDBSelectQuery(QUERY_ITEM_WITH_NAME_TKN).replace(
				DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName());
		System.out.println(query);

		ArrayList<HashMap<Class, Object>> dataList = new ArrayList<HashMap<Class, Object>>();

		HashMap<Class, Object> itemNameMap = new HashMap<Class, Object>();
		itemNameMap.put(String.class, itemName);
		dataList.add(itemNameMap);

		QueryResultTable resultTable = new PreparedDataExecutor(new DatabaseController().getConnection(), dataList,
				TransactionController.getDBSelectQuery(QUERY_ITEM_WITH_NAME_TKN).replace(
						DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName()))
				.executeQueryForResult();

		ArrayList<ArrayList<String>> result = resultTable.getResultTable();
		TransactionItem item = null;

		if (!result.isEmpty()) {
			ArrayList<String> resultRow = result.get(0);
			item = new TransactionItem();
			item.setItemId(Integer.parseInt(resultRow.get(0).toString()));
			item.setItemName(resultRow.get(1).toString());
			item.setItemCategory(resultRow.get(2).toString());
			item.setItemRate(Float.parseFloat(resultRow.get(3).toString()));
		}
		return item;
	}

	private List<TransactionItem> getAllTransactionItems() throws ClassNotFoundException, DataBaseException,
			DatabaseTableCreationException {

		List<TransactionItem> itemsList = new ArrayList<TransactionItem>();

		QueryResultTable resultTable = new DatabaseController().executeQueryForResult(TransactionController
				.getDBSelectQuery(QUERY_ITEMS_TABLE_TKN).replace(DatabaseController.getDatabaseNameToken(),
						DatabaseController.getDatabaseName()));

		Object[] objectArray = resultTable.getHeaderDetails().keySet().toArray();
		String[] columnNames = Arrays.copyOf(objectArray, objectArray.length, String[].class);

		ArrayList<ArrayList<String>> tableData = resultTable.getResultTable();

		TransactionItem curItem;
		/*
		 * Looping through items and creating a Item object.
		 */
		for (ArrayList<String> row : tableData) {
			curItem = new TransactionItem();
			/*
			 * Loops through each column and update the values to the item object.
			 */
			int columnIndex = 0;
			for (String columnVal : row) {
				if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getCategoryColumn())) {
					// Category
					curItem.setItemCategory(columnVal);
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getItemColumn())) {
					// Name
					curItem.setItemName(columnVal);
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getRateColumn())) {
					// Rate
					curItem.setItemRate(Float.parseFloat(columnVal));
				} else if (columnNames[columnIndex].equalsIgnoreCase(TransactionItem.getItemIDColumn())) {
					// Item ID
					curItem.setItemId(Integer.parseInt(columnVal));
				} else {
					log.warn("Unexpected value found: " + columnVal + " in index: " + columnIndex);
				}
				// incrementing columnIndex
				columnIndex++;
			}
			itemsList.add(curItem);
		}
		return itemsList;
	}

	/**
	 * Gets the transactionItem detail by name.
	 * 
	 * @param transactionItemName
	 * @return
	 */
	public TransactionItem getDetailByName(String transactionItemName) {
		TransactionItem resultItem = null;
		for (TransactionItem item : this.transactionItems) {
			if (item.getItemName().equalsIgnoreCase(transactionItemName)) {
				resultItem = item;
				break;
			}
		}
		return resultItem;
	}

	/**
	 * Gets the transaction item by category.
	 * 
	 * @param category
	 * @return
	 */
	public TransactionItem getDetailByCategory(String category) {
		TransactionItem resultItem = null;
		for (TransactionItem item : this.transactionItems) {
			if (item.getItemCategory().equalsIgnoreCase(category)) {
				resultItem = item;
				break;
			}
		}
		return resultItem;
	}

	/**
	 * Gets the transaction item categories as a list.
	 * 
	 * @return Categories as a list.
	 * @throws DataBaseException
	 * @throws ClassNotFoundException
	 * @throws DatabaseTableCreationException
	 */
	public List<String> getItems() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<ArrayList<String>> resultTable = new DatabaseController().executeQueryForResult(
				TransactionController.getDBSelectQuery(QUERY_ALL_ITEMS_TKN).replace(
						DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName()))
				.getResultTable();
		for (ArrayList<String> row : resultTable) {
			res.add(row.get(0));
		}
		return res;
	}

	/**
	 * Gets the transaction item categories as a list.
	 * 
	 * @return Categories as a list.
	 * @throws DataBaseException
	 * @throws ClassNotFoundException
	 * @throws DatabaseTableCreationException
	 */
	public List<String> getCategories() throws ClassNotFoundException, DataBaseException,
			DatabaseTableCreationException {
		ArrayList<String> res = new ArrayList<String>();
		ArrayList<ArrayList<String>> resultTable = new DatabaseController().executeQueryForResult(
				TransactionController.getDBSelectQuery(QUERY_ALL_CATEGORIES_TKN).replace(
						DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName()))
				.getResultTable();
		for (ArrayList<String> row : resultTable) {
			res.add(row.get(0));
		}
		return res;
	}

	/**
	 * Validates the transaction data
	 */
	private Response validateData() {
		// TODO validate
		return new Response();
	}

}
