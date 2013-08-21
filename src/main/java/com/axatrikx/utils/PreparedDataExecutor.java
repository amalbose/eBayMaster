package com.axatrikx.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.errors.DataBaseException;

/**
 * PreparedDataExecutor helps in execution of prepared statements. The class expects a list of hashmap which contains
 * the data to be inserted. Each hashmap should contain the datatype object and the value to be entered.
 * 
 * @author Amal Bose B S
 * 
 */
public class PreparedDataExecutor {

	private static Logger log = Logger.getLogger(PreparedDataExecutor.class);

	@SuppressWarnings("rawtypes")
	private ArrayList<HashMap<Class, Object>> dataList;
	private String preparedStatement;
	private Connection dbConnection;

	/**
	 * PreparedDataExecutor constructor.
	 * 
	 * @param dbConnection
	 *            The database connection.
	 * @param dataList
	 *            The data list each item of which is a hashmap having the datatype object -> datavalue pair.
	 * @param preparedStatement
	 *            the prepared statement string.
	 */
	@SuppressWarnings("rawtypes")
	public PreparedDataExecutor(Connection dbConnection, ArrayList<HashMap<Class, Object>> dataList,
			String preparedStatement) {
		this.dataList = dataList;
		this.preparedStatement = preparedStatement;
		this.dbConnection = dbConnection;
	}

	/**
	 * Returns the prepared statement.
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PreparedStatement getPreparedStatement() {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = dbConnection.prepareStatement(this.preparedStatement);
			int index = 0;
			for (HashMap<Class, Object> data : dataList) {
				Set<Class> keySet = data.keySet();
				if (!data.isEmpty()) {
					for (Class<?> dataType : keySet) {
						if (dataType.isAssignableFrom(Integer.class)) {
							preparedStatement.setInt(index + 1, (Integer) data.get(dataType));
							log.info("Added Integer " + data.get(dataType) + " to prepared statement");
						} else if (dataType.isAssignableFrom(Float.class)) {
							preparedStatement.setFloat(index + 1, (Float) data.get(dataType));
							log.info("Added Float " + data.get(dataType) + " to prepared statement");
						} else if (dataType.isAssignableFrom(String.class)) {
							preparedStatement.setString(index + 1, (String) data.get(dataType));
							log.info("Added String " + data.get(dataType) + " to prepared statement");
						} else if (dataType.isAssignableFrom(Timestamp.class)) {
							preparedStatement.setTimestamp(index + 1, (Timestamp) data.get(dataType));
							log.info("Added Time " + data.get(dataType) + " to prepared statement");
						} else {
							// TODO throw exception.
						}
					}
				}
				index++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return preparedStatement;
	}

	/**
	 * Execute query and returns the result table in 2D arraylist format.
	 * 
	 * @param queryString
	 *            The query to be executed
	 * @return The result table in 2D arraylist format.
	 * @throws DataBaseException
	 */
	public QueryResultTable executeQueryForResult() throws DataBaseException {
		// result object
		QueryResultTable queryResult = new QueryResultTable();

		// 2D arraylist for result table
		ArrayList<ArrayList<String>> resultTable = new ArrayList<ArrayList<String>>();
		// hashmap for header details
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();

		try {
			ResultSet rs = getPreparedStatement().executeQuery();
			int colSize = rs.getMetaData().getColumnCount();

			// setting header details.
			for (int i = 1; i <= colSize; i++) {
				headers.put(rs.getMetaData().getColumnLabel(i), rs.getMetaData().getColumnTypeName(i));
			}

			if (headers.size() < 1) {
				log.warn("No columns(ColSize: " + colSize + ") were obtained from query");
			}
			queryResult.setHeaderDetails(headers);
			ArrayList<String> rowData;
			while (rs.next()) {
				rowData = new ArrayList<String>();
				for (int i = 1; i <= colSize; i++) {
					rowData.add(String.valueOf(rs.getObject(i)));
				}
				resultTable.add(rowData);
			}

			if (resultTable.size() < 1) {
				log.warn("No results(Result Count: " + resultTable.size() + " were obtained from query");
			}

		} catch (SQLException e) {
			log.error("SQLException while executing query", e);
			throw new DataBaseException(e.getMessage(), e);
		} finally {
			try {
				dbConnection.close();
			} catch (SQLException e) {
				log.error("SQLException while closing connection", e);
				throw new DataBaseException(e.getMessage(), e);
			}
		}
		queryResult.setResultTable(resultTable);
		return queryResult;
	}
}
