package com.axatrikx.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CommonSettings {

	private static Logger log = Logger.getLogger(CommonSettings.class);

	private static final String SETTINGS_FILE = ConfigValues.SETTINGS_FILE.toString();
	private static final String CONFIG_FOLDER = ConfigValues.CONFIG_FOLDER.toString();

	private static Properties prop;

	static {
		prop = new Properties();
		try {
			prop.load(CommonSettings.class.getClassLoader().getResourceAsStream(SETTINGS_FILE));
		} catch (IOException e) {
			log.error("IO Exception while loading " + SETTINGS_FILE, e);
		}
	}

	public static String getSettingValue(String key) {
		return prop.getProperty(key);
	}

	public static String getConfigFolder() {
		return prop.getProperty(CONFIG_FOLDER);
	}

	public static Properties getPropertiesFromFile(String fileName) {
		Properties props = new Properties();
		try {
			props.load(CommonSettings.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			log.error("IO Exception while loading " + fileName, e);
		}
		return props;
	}

	/**
	 * Returns the query based on the query Type.<br/>
	 * Query types include QUERY_TRANS_TABLE, QUERY_ITEMS_TABLE and QUERY_TRANS_DETAIL
	 * 
	 * @param queryType
	 *            Query types include QUERY_TRANS_TABLE, QUERY_ITEMS_TABLE and QUERY_TRANS_DETAIL
	 * @return The query string based on query type.
	 */
	public static String getDBInsertQuery(String queryType) {
		Properties props = new Properties();
		String fileName = ConfigValues.QUERY_FOLDER.toString() + ConfigValues.SEPARATOR.toString()
				+ ConfigValues.QUERY_SELECT_FILE.toString();
		try {
			props.load(CommonSettings.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			log.error("IO Exception while loading " + fileName, e);
		}
		return props.getProperty(queryType);
	}

	public static void main(String[] args) {
		System.out.println(getConfigFolder());
	}
}