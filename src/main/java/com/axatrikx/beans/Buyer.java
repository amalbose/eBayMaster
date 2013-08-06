package com.axatrikx.beans;

public class Buyer {
	
	private String name;
	private String location;
	
	private static final String BUYER_COLUMN = "BUYERNAME";
	private static final String LOCATION_COLUMN = "LOCATION";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public static String getBuyerColumn() {
		return BUYER_COLUMN;
	}
	public static String getLocationColumn() {
		return LOCATION_COLUMN;
	}
}
