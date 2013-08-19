package com.axatrikx.beans;

public class Buyer {
	
	private String buyerName;
	private String location;
	
	private static final String BUYER_COLUMN = "BUYERNAME";
	private static final String LOCATION_COLUMN = "LOCATION";
	
	public static final String BUYER_HEADER = "Buyer Name";
	public static final String LOCATION_HEADER = "Location";
	
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String name) {
		this.buyerName = name;
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
