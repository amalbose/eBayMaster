package com.axatrikx.utils;

public enum ConfigValues {

	CONFIG_FOLDER("CONFIG_FOLDER"),
	QUERY_FOLDER("db"),
	SETTINGS_FILE("settings.properties"), 
	QUERY_CREATE_FILE("dbqueries_create_tables.db"),
	QUERY_SELECT_FILE("dbqueries_select.db"),
	SEPARATOR("//");
	
	private String value;
	
	ConfigValues(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
