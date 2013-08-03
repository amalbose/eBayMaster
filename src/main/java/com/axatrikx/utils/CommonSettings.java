package com.axatrikx.utils;

import java.io.IOException;
import java.util.Properties;

public class CommonSettings {
	
	private static final String SETTINGS_FILE = ConfigValues.SETTINGS_FILE.toString();
	private static final String CONFIG_FOLDER = ConfigValues.CONFIG_FOLDER.toString();
	private static Properties prop;
	
	static {
		prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(SETTINGS_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getSettingValue(String key){
		return prop.getProperty(key);
	}
	
	public String getConfigFolder() {
		return prop.getProperty(CONFIG_FOLDER);
	}
}
