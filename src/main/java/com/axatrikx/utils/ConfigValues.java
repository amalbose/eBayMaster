package com.axatrikx.utils;

public enum ConfigValues {

	SETTINGS_FILE("settings.properties"), 
	CONFIG_FOLDER("CONFIG_FOLDER");
	
	private String value;
	
	ConfigValues(String value){
		this.value = value;
	}
	
	public String toString(){
		return this.value;
	}
}
