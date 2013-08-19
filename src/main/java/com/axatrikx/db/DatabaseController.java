package com.axatrikx.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.axatrikx.beans.QueryResultTable;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.utils.CommonSettings;
import com.axatrikx.utils.ConfigValues;

public class DatabaseController {

	private static Logger log = Logger.getLogger(DatabaseController.class);

	private static final String SQL_FILE_PATH = ConfigValues.QUERY_FOLDER.toString()
			+ ConfigValues.SEPARATOR.toString() + ConfigValues.QUERY_CREATE_FILE.toString();

	private static final String DATABASE_TOKEN = "<DATABASE_NAME>";

	private static final String DATABASE_NAME = "EBAYMASTERDB";
	private static final String USERNAME = "ebayAdmin";
	private static final String PASSWORD = "ebayAdmin";

	private static final String DRIVER_STRING = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_URL = "jdbc:derby:" + DATABASE_NAME + ";user=" + USERNAME + ";password="
			+ PASSWORD + ";create=true";

	private Connection con;

	/**
	 * {@link DatabaseController} is the Constructor which initializes the database.
	 * 
	 * @throws ClassNotFoundException
	 * @throws DataBaseException
	 * @throws DatabaseTableCreationException 
	 * 
	 * @throws Exception
	 */
	public DatabaseController() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		try {
			Class.forName(DRIVER_STRING);
			initialize();
		} catch (ClassNotFoundException e) {
			log.error("ClassNotFoundException for " + DRIVER_STRING, e);
			throw new ClassNotFoundException("ClassNotFoundException for " + DRIVER_STRING);
		}
	}

	/**
	 * Initializes the Database. Creates the Database and tables if not present.
	 * @throws DatabaseTableCreationException 
	 * 
	 * @throws Exception
	 */
	public void initialize() throws DataBaseException, DatabaseTableCreationException {
		setUpDataBase();
	}

	/**
	 * Creates database and its tables if not present.
	 * @throws DatabaseTableCreationException 
	 * 
	 * @throws Exception
	 */
	private void setUpDataBase() throws DataBaseException, DatabaseTableCreationException {
		try {
			con = DriverManager.getConnection(JDBC_URL);
			// create table
			createTable(con, SQL_FILE_PATH);
		} catch (SQLException e) {
			log.error("SQLException while getting Connection: " + JDBC_URL, e);
			throw new DataBaseException(e.getMessage(), e);
		}
	}

	/**
	 * Executes the query from the provided file for creating tables if not present. File should have data(each line) in
	 * this format: <TABLE_NAME>=<QUERY_FOR_CREATING_TABLE>
	 * 
	 * @param con
	 *            Connection string
	 * @param queryFile
	 *            the query file path relative to resource folder.
	 * @return True/False based on the success of query executed.
	 * @throws DatabaseTableCreationException 
	 */
	private boolean createTable(Connection con, String queryFile) throws DatabaseTableCreationException {
		boolean result = false;
		Properties tableProps = CommonSettings.getPropertiesFromFile(queryFile);
		try {
			Set<Object> tables = tableProps.keySet();
			String[] types = { "TABLE" };
			for (Object tableName : tables) {
				DatabaseMetaData dmd = con.getMetaData();
				ResultSet rs = dmd.getTables(null, DATABASE_NAME, tableName.toString(), types);
				if (!rs.next()) {
					log.info("Table - '" + tableName + "' does not exist in database - " + DATABASE_NAME
							+ ". Creating the table.");
					con.createStatement().execute(
							tableProps.getProperty(tableName.toString()).replace(DATABASE_TOKEN, DATABASE_NAME));
				}
			}
			result = true;
		} catch (SQLException e) {
			log.error("SQL Exception while executing query", e);
			throw new DatabaseTableCreationException(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Executes the provided query.
	 * 
	 * @param queryString
	 * @return
	 */
	public boolean executeQuery(String queryString) throws DataBaseException {
		boolean result = false;
		try {
			this.con.createStatement().execute(queryString);
			result = true;
		} catch (SQLException e) {
			log.error("SQLException while executing query: '" + queryString + "'", e);
			throw new DataBaseException(e.getMessage(), e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("SQLException while closing connection", e);
				throw new DataBaseException(e.getMessage(), e);
			}
		}
		return result;
	}

	/**
	 * Execute query and returns the result table in 2D arraylist format.
	 * 
	 * @param queryString
	 *            The query to be executed
	 * @return The result table in 2D arraylist format.
	 * @throws DataBaseException
	 */
	public QueryResultTable executeQueryForResult(String queryString) throws DataBaseException {
		// result object
		QueryResultTable queryResult = new QueryResultTable();

		// 2D arraylist for result table
		ArrayList<ArrayList<String>> resultTable = new ArrayList<ArrayList<String>>();
		// hashmap for header details
		LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();

		try {
			ResultSet rs = this.con.createStatement().executeQuery(queryString);
			int colSize = rs.getMetaData().getColumnCount();

			// setting header details.
			for (int i = 1; i <= colSize; i++) {
				ResultSetMetaData metadata = rs.getMetaData();
				headers.put(metadata.getColumnName(i), metadata.getColumnTypeName(i));
			}

			if (headers.size() < 1) {
				log.warn("No columns(ColSize: " + colSize + ") were obtained from query : " + queryString);
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
				log.warn("No results(Result Count: " + resultTable.size() + ") were obtained from query : "
						+ queryString);
			}

		} catch (SQLException e) {
			log.error("SQLException while executing query: '" + queryString + "'", e);
			throw new DataBaseException(e.getMessage(), e);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("SQLException while closing connection", e);
				throw new DataBaseException(e.getMessage(), e);
			}
		}
		System.out.println(headers);
		queryResult.setResultTable(resultTable);
		System.out.println(resultTable);
		return queryResult;
	}

	/**
	 * Gets the column names requested in the provided query.
	 * 
	 * @param query
	 *            The SQL query. It should be simple query (without columnName AS structure)
	 * @return The list of column names requested by the query.
	 */
	public static List<String> getQueryColumns(String query) {
		List<String> columns;
		String columnString = query.replace("SELECT", "").trim().split("FROM")[0].trim();
		columns = Arrays.asList(columnString.split("\\s*,\\s*"));
		return columns;
	}

	/**
	 * Returns the connection object.
	 * @return
	 */
	public Connection getConnection(){
		return this.con;
	}
	
	/**
	 * Returns the database name.
	 * 
	 * @return
	 */
	public static String getDatabaseName() {
		return DATABASE_NAME;
	}
	
	/**
	 * Returns the database name token used in DB property files.
	 * @return
	 */
	public static String getDatabaseNameToken() {
		return DATABASE_TOKEN;
	}
}
