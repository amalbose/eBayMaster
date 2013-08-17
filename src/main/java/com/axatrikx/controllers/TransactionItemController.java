package com.axatrikx.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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

	private static final String QUERY_ITEM_WITH_NAME_TKN = "QUERY_ITEM_WITH_NAME";

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

		ArrayList<String> resultRow = result.get(0);
		
		TransactionItem item = new TransactionItem();
		item.setItemId(Integer.parseInt(resultRow.get(0).toString()));
		item.setItemName(resultRow.get(1).toString());
		item.setItemCategory(resultRow.get(2).toString());
		item.setItemRate(Float.parseFloat(resultRow.get(3).toString()));
		return item;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException, DataBaseException,
			DatabaseTableCreationException {
		new TransactionItemController().getItem("Item1");
	}

	/**
	 * Validates the transaction data
	 */
	private Response validateData() {
		// TODO validate
		return new Response();
	}

}
