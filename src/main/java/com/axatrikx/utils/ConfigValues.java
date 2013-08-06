package com.axatrikx.utils;

public enum ConfigValues {

	CONFIG_FOLDER("CONFIG_FOLDER"),
	QUERY_FOLDER("db"),
	SETTINGS_FILE("settings.properties"), 
	QUERY_CREATE_FILE("dbqueries_create_tables.db"),
	QUERY_SELECT_FILE("dbqueries_select.db"),
	SEPARATOR("//"),
	DATE_FORMAT("yyyy-MM-dd HH:mm:ss.S");
	
	private String value;
	
	ConfigValues(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
