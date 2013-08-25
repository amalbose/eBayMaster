package com.axatrikx.utils;

public enum ConfigValues {

	APPLICATION_ICON("images/bulb.gif"),
	DB_QUERY_FOLDER("db"),
	QUERY_CREATE_FILE("dbqueries_create_tables.db"),
	QUERY_SELECT_FILE("dbqueries_select.db"),
	QUERY_INSERT_FILE("dbqueries_insert.db"),
	QUERY_UPDATE_FILE("dbqueries_update.db"),
	QUERY_DELETE_FILE("dbqueries_delete.db"),
	SEPARATOR("//"),
	DATE_FORMAT("yyyy-MM-dd HH:mm:ss.S"),
	CALENDAR_DATE_FORMAT("EEE MMM dd HH:mm:ss z yyyy"),
	DATE_DISPLAY_FORMAT("dd MMM yyyy HH:mm:ss"),
	UI_DATE_FORMAT("dd MMM, yyyy hh:mm aaa");
	
	private String value;
	
	ConfigValues(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
