package com.axatrikx.beans;

public enum ApplicationPages {

	HOME_PAGE("Home Page", 0), TRANSACTION_PAGE("Transaction Page", 1), REPORT_PAGE("Report Page", 2);

	private String name;
	private int index;

	ApplicationPages(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String toString() {
		return this.name;
	}

	public int getIndex() {
		return this.index;
	}
}
