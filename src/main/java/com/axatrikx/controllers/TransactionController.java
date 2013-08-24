package com.axatrikx.controllers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.axatrikx.beans.Category;
import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.ConfigValues;
import com.axatrikx.utils.PreparedDataExecutor;
import com.axatrikx.utils.Utils;

public class TransactionController {

	private static Logger log = Logger.getLogger(TransactionController.class);

	private static final String DEFAULT_PROFIT_EQN = "<PRICE> - <COST> * (1 + <RATE>)";

	private static final String INSERT_TRANS_TABLE_TKN = "INSERT_TRANS_TABLE";
	private static final String INSERT_CATEGORY_TABLE_TKN = "INSERT_CATEGORY_TABLE";
	private static final String QUERY_ALL_ITEMS_TKN = "QUERY_ALL_ITEMS";
	private static final String QUERY_CATEGORY_FROM_ITEM_TKN = "QUERY_CATEGORY_FROM_ITEM";

	private static final String COST_TKN = "<COST>";
	private static final String PRICE_TKN = "<PRICE>";
	private static final String RATE_TKN = "<RATE>";

	public void getTransactionModel(String queryString) {

	}

	/**
	 * Returns the select query based on the type of query. Types include 'QUERY_TRANS_TABLE', 'QUERY_CATEGORY_TABLE',
	 * 'QUERY_CATEGORY_WITH_NAME', 'QUERY_ALL_ITEMS', 'QUERY_ALL_CATEGORIES' and 'QUERY_TRANS_DETAIL'.
	 * 
	 * @param queryType
	 *            Which query to return. Types include 'QUERY_TRANS_TABLE', 'QUERY_CATEGORY_TABLE',
	 *            'QUERY_CATEGORY_WITH_NAME', 'QUERY_ALL_ITEMS', 'QUERY_ALL_CATEGORIES' and 'QUERY_TRANS_DETAIL'.
	 * @return The query string.
	 */
	public static String getDBSelectQuery(String queryType) {
		String result = null;
		Properties queryValues = Utils.getPropertiesFromFile(ConfigValues.DB_QUERY_FOLDER.toString()
				+ ConfigValues.SEPARATOR.toString() + ConfigValues.QUERY_SELECT_FILE.toString());
		result = queryValues.getProperty(queryType);
		if (result.isEmpty()) {
			log.error("Couldnt find date for : " + queryType);
		}
		return result;
	}

	/**
	 * Returns the insert query based on the type of query. Types include 'INSERT_TRANS_TABLE', 'INSERT_CATEGORY_TABLE'.
	 * 
	 * @param queryType
	 *            Which query to return. Types include 'INSERT_TRANS_TABLE', 'INSERT_CATEGORY_TABLE'.
	 * @return The query string.
	 */
	public static String getDBInsertQuery(String tableType) {
		String result = null;
		Properties queryValues = Utils.getPropertiesFromFile(ConfigValues.DB_QUERY_FOLDER.toString()
				+ ConfigValues.SEPARATOR.toString() + ConfigValues.QUERY_INSERT_FILE.toString());
		result = queryValues.getProperty(tableType);
		if (result.isEmpty()) {
			log.error("Invalid tableType: " + tableType);
		}
		return result;
	}

	/**
	 * Returns the update query based on the type of query. Types include 'UPDATE_TRANSACTIONS'
	 * 
	 * @param tableType
	 * @return
	 */
	public static String getDBUpdateQuery(String tableType) {
		String result = null;
		Properties queryValues = Utils.getPropertiesFromFile(ConfigValues.DB_QUERY_FOLDER.toString()
				+ ConfigValues.SEPARATOR.toString() + ConfigValues.QUERY_UPDATE_FILE.toString());
		result = queryValues.getProperty(tableType);
		if (result.isEmpty()) {
			log.error("Invalid tableType: " + tableType);
		}
		return result;
	}
	
	/**
	 * Returns the delete query based on the type of query. Types include 'UPDATE_TRANSACTIONS'
	 * @param tableType
	 * @return
	 */
	public static String getDBDeleteQuery(String tableType) {
		String result = null;
		Properties queryValues = Utils.getPropertiesFromFile(ConfigValues.DB_QUERY_FOLDER.toString()
				+ ConfigValues.SEPARATOR.toString() + ConfigValues.QUERY_DELETE_FILE.toString());
		result = queryValues.getProperty(tableType);
		if (result.isEmpty()) {
			log.error("Invalid tableType: " + tableType);
		}
		return result;
	}

	/**
	 * Inserts the provided transaction to Database. If the category is new, it will also be added to Database.
	 * 
	 * @param buyerName
	 * @param location
	 * @param cost
	 * @param price
	 * @param date
	 * @param itemName
	 * @param categoryName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	@SuppressWarnings("rawtypes")
	public boolean insertTransaction(String buyerName, String location, float cost, float price, Date date,
			String itemName, String categoryName, float rate) throws ClassNotFoundException, SQLException,
			DataBaseException, DatabaseTableCreationException {

		Category category = processCategory(categoryName, rate);

		ArrayList<HashMap<Class, Object>> dataList = new ArrayList<HashMap<Class, Object>>();

		HashMap<Class, Object> itemNameVal = new HashMap<Class, Object>();
		itemNameVal.put(String.class, itemName);
		dataList.add(itemNameVal);

		HashMap<Class, Object> categoryIdVal = new HashMap<Class, Object>();
		categoryIdVal.put(Integer.class, category.getCategoryId());
		dataList.add(categoryIdVal);

		HashMap<Class, Object> buyerNameVal = new HashMap<Class, Object>();
		buyerNameVal.put(String.class, buyerName);
		dataList.add(buyerNameVal);

		HashMap<Class, Object> locationVal = new HashMap<Class, Object>();
		locationVal.put(String.class, location);
		dataList.add(locationVal);

		HashMap<Class, Object> costVal = new HashMap<Class, Object>();
		costVal.put(Float.class, cost);
		dataList.add(costVal);

		HashMap<Class, Object> priceVal = new HashMap<Class, Object>();
		priceVal.put(Float.class, price);
		dataList.add(priceVal);

		HashMap<Class, Object> profitVal = new HashMap<Class, Object>();
		profitVal.put(Float.class, calculateProfit(cost, price, rate));
		dataList.add(profitVal);

		HashMap<Class, Object> dateVal = new HashMap<Class, Object>();
		dateVal.put(Timestamp.class, new Timestamp(date.getTime()));
		dataList.add(dateVal);

		int noOfRowsAffected = new PreparedDataExecutor(new DatabaseController().getConnection(), dataList,
				getDBInsertQuery(INSERT_TRANS_TABLE_TKN).replace(DatabaseController.getDatabaseNameToken(),
						DatabaseController.getDatabaseName())).getPreparedStatement().executeUpdate();
		boolean isSuccessful = false;
		if (noOfRowsAffected > 0) {
			isSuccessful = true;
		}
		return isSuccessful;
	}

	/**
	 * Process category and inserts the category if not present and returns the Category object.
	 * 
	 * @param itemName
	 * @param categoryName
	 * @param rate
	 * @return the Category object.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	@SuppressWarnings("rawtypes")
	private Category processCategory(String categoryName, float rate) throws ClassNotFoundException, SQLException,
			DataBaseException, DatabaseTableCreationException {
		Category category = new CategoryController().getCategory(categoryName, rate);
		if (category == null) {
			log.info("Category " + categoryName + " doesn't exist. Creating item record.");

			ArrayList<HashMap<Class, Object>> dataList = new ArrayList<HashMap<Class, Object>>();

			HashMap<Class, Object> categoryVal = new HashMap<Class, Object>();
			categoryVal.put(String.class, categoryName);
			dataList.add(categoryVal);

			HashMap<Class, Object> rateVal = new HashMap<Class, Object>();
			rateVal.put(Float.class, rate);
			dataList.add(rateVal);

			int noOfRowsAffected = new PreparedDataExecutor(new DatabaseController().getConnection(), dataList,
					getDBInsertQuery(INSERT_CATEGORY_TABLE_TKN).replace(DatabaseController.getDatabaseNameToken(),
							DatabaseController.getDatabaseName())).getPreparedStatement().executeUpdate();

			if (noOfRowsAffected > 0) {
				log.info("New Category '" + categoryName + "' created");
				category = new CategoryController().getCategory(categoryName, rate);
				CategoryController.updateCategories();
			} else {
				log.error("Failed to add category " + categoryName + "'");
			}
		}
		return category;
	}

	/**
	 * Gets the transaction items as a list.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
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
	 * Gets the category name of the given itemName. If multiple cateogries are present for an item, the latest used
	 * combination will be selected.
	 * 
	 * @param itemName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	@SuppressWarnings("rawtypes")
	public String getCategoryFromItem(String itemName) {
		String categoryName = "";
		ArrayList<HashMap<Class, Object>> dataList = new ArrayList<HashMap<Class, Object>>();
		HashMap<Class, Object> itemNameVal = new HashMap<Class, Object>();
		itemNameVal.put(String.class, itemName + "%");
		dataList.add(itemNameVal);

		QueryResultTable resultTable = null;
		try {
			resultTable = new PreparedDataExecutor(new DatabaseController().getConnection(), dataList,
					TransactionController.getDBSelectQuery(QUERY_CATEGORY_FROM_ITEM_TKN).replace(
							DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName()))
					.executeQueryForResult();
		} catch (ClassNotFoundException e2) {
			log.error("Exception while creating controller", e2);
		} catch (DataBaseException e2) {
			log.error("Database exception", e2);
		} catch (DatabaseTableCreationException e2) {
			log.error("Exception while creating database tables", e2);
		}
		ArrayList<ArrayList<String>> resultTab = resultTable.getResultTable();
		if (resultTab.size() > 0) {
			categoryName = resultTab.get(0).get(0).toString();
		} else {
			log.error("No category Name obtained for " + itemName);
		}
		return categoryName;
	}

	/**
	 * Calculates profit from the profit expression.
	 * 
	 * @param cost
	 * @param price
	 * @param rate
	 * @return
	 */
	public float calculateProfit(float cost, float price, float rate) {
		return Float.parseFloat(Utils.evaluateExpression(DEFAULT_PROFIT_EQN.replace(COST_TKN, String.valueOf(cost))
				.replace(RATE_TKN, String.valueOf(rate)).replace(PRICE_TKN, String.valueOf(price))));
	}
}
