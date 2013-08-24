package com.axatrikx.beans;

public enum ApplicationPages {

	HOME_PAGE("Home Page"), TRANSACTION_PAGE("Transaction Page"), REPORT_PAGE("Report Page");

	private String name;

	ApplicationPages(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
