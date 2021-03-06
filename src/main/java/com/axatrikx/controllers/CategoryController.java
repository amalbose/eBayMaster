package com.axatrikx.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.beans.Response;
import com.axatrikx.beans.Category;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;

/**
 * The controller class which controls the validation and saving of Transaction categories.
 * 
 * @author Amal Bose B S
 * 
 */
public class CategoryController {

	private static Logger log = Logger.getLogger(CategoryController.class);

	private static List<Category> categories;

	private static final String QUERY_CATEGORY_WITH_DETAIL_TKN = "QUERY_CATEGORY_WITH_DETAIL";
	private static final String QUERY_CATEGORY_TABLE_TKN = "QUERY_CATEGORY_TABLE";
	private static final String QUERY_ALL_CATEGORIES_TKN = "QUERY_ALL_CATEGORIES";

	/**
	 * Constructor which sets the transactionItems list.
	 * 
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	public CategoryController() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		updateCategories();
	}

	public static void updateCategories() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		categories = getAllCategories();
	}

	/**
	 * Gets the category from the category name and rate
	 * 
	 * @param categoryName
	 * @param rate
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	@SuppressWarnings("rawtypes")
	public Category getCategory(String categoryName, float rate) throws ClassNotFoundException, SQLException,
			DataBaseException, DatabaseTableCreationException {
		ArrayList<HashMap<Class, Object>> dataList = new ArrayList<HashMap<Class, Object>>();
		HashMap<Class, Object> categoryNameMap = new HashMap<Class, Object>();
		categoryNameMap.put(String.class, categoryName);
		dataList.add(categoryNameMap);

		HashMap<Class, Object> rateVal = new HashMap<Class, Object>();
		rateVal.put(Float.class, rate);
		dataList.add(rateVal);

		QueryResultTable resultTable = new PreparedDataExecutor(new DatabaseController().getConnection(), dataList,
				TransactionController.getDBSelectQuery(QUERY_CATEGORY_WITH_DETAIL_TKN).replace(
						DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName()))
				.executeQueryForResult();

		ArrayList<ArrayList<String>> result = resultTable.getResultTable();
		Category category = null;

		if (!result.isEmpty()) {
			ArrayList<String> resultRow = result.get(0);
			category = new Category();
			category.setCategoryId(Integer.parseInt(resultRow.get(0).toString()));
			category.setCategoryName(resultRow.get(1).toString());
			category.setRate(Float.parseFloat(resultRow.get(2).toString()));
		}
		return category;
	}

	/**
	 * Gets all categories
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException
	 */
	public static List<Category> getAllCategories() throws ClassNotFoundException, DataBaseException,
			DatabaseTableCreationException {

		List<Category> categoryList = new ArrayList<Category>();

		QueryResultTable resultTable = new DatabaseController().executeQueryForResult(TransactionController
				.getDBSelectQuery(QUERY_CATEGORY_TABLE_TKN).replace(DatabaseController.getDatabaseNameToken(),
						DatabaseController.getDatabaseName()));

		Object[] objectArray = resultTable.getHeaderDetails().keySet().toArray();
		String[] columnNames = Arrays.copyOf(objectArray, objectArray.length, String[].class);

		ArrayList<ArrayList<String>> tableData = resultTable.getResultTable();

		Category curCategory;
		/*
		 * Looping through items and creating a Item object.
		 */
		for (ArrayList<String> row : tableData) {
			curCategory = new Category();
			/*
			 * Loops through each column and update the values to the item object.
			 */
			int columnIndex = 0;
			for (String columnVal : row) {
				if (columnNames[columnIndex].equalsIgnoreCase(Category.getCategoryIdColumn())) {
					// Category id
					curCategory.setCategoryId(Integer.parseInt(columnVal));
				} else if (columnNames[columnIndex].equalsIgnoreCase(Category.getCategoryNameColumn())) {
					// Name
					curCategory.setCategoryName(columnVal);
				} else if (columnNames[columnIndex].equalsIgnoreCase(Category.getRateColumn())) {
					// Rate
					curCategory.setRate(Float.parseFloat(columnVal));
				} else {
					log.warn("Unexpected value found: " + columnVal + " in index: " + columnIndex);
				}
				// incrementing columnIndex
				columnIndex++;
			}
			categoryList.add(curCategory);
		}
		return categoryList;
	}

	/**
	 * Gets the category detail by category name.
	 * 
	 * @param categoryName
	 * @return
	 */
	public Category getDetailByName(String categoryName) {
		Category resultCat = null;
		for (Category category : categories) {
			if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
				resultCat = category;
				break;
			}
		}
		return resultCat;
	}

	/**
	 * Gets the transaction item categories as a list.
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
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
	@SuppressWarnings("unused")
	private Response validateData() {
		// TODO validate
		return new Response();
	}

}
