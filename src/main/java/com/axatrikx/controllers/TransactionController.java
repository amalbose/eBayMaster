package com.axatrikx.controllers;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.axatrikx.db.DatabaseController;
import com.axatrikx.utils.CommonSettings;
import com.axatrikx.utils.ConfigValues;

public class TransactionController {

	private static Logger log = Logger.getLogger(TransactionController.class);

	private static final String QUERY_TRANS_TABLE_TKN = "QUERY_TRANS_TABLE";
	private static final String QUERY_ITEMS_TABLE_TKN = "QUERY_ITEMS_TABLE";
	private static final String QUERY_TRANS_DETAIL_TKN = "QUERY_TRANS_DETAIL";

	public void getTransactionModel(String queryString) {

	}

	/**
	 * Returns the query based on the type of query. Types include 'QUERY_TRANS_TABLE', 'QUERY_ITEMS_TABLE' and
	 * 'QUERY_TRANS_DETAIL'.
	 * 
	 * @param queryType
	 *            Which query to return. Types include 'QUERY_TRANS_TABLE', 'QUERY_ITEMS_TABLE' and
	 *            'QUERY_TRANS_DETAIL'.
	 * @return The query string.
	 */
	public static String getDBSelectQuery(String queryType) {
		String result = null;
		Properties queryValues = CommonSettings.getPropertiesFromFile(ConfigValues.QUERY_FOLDER.toString()
				+ ConfigValues.SEPARATOR.toString() + ConfigValues.QUERY_SELECT_FILE.toString());
		if (QUERY_ITEMS_TABLE_TKN.equals(queryType) || QUERY_TRANS_DETAIL_TKN.equals(queryType)
				|| QUERY_TRANS_TABLE_TKN.equals(queryType)) {
			result = queryValues.getProperty(queryType);
		} else {
			log.error("Invalid queryType: " + queryType);
		}
		return result;
	}
}
