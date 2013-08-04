package com.axatrikx.utils;

public enum ConfigValues {

	SETTINGS_FILE("settings.properties"), 
	CONFIG_FOLDER("CONFIG_FOLDER"),
	QUERY_FOLDER("db"),
	QUERY_CREATE_FILE("dbqueries_create_tables.db"),
	SEPARATOR("//");
	
	private String value;
	
	ConfigValues(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
