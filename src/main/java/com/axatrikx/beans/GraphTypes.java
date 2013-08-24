package com.axatrikx.beans;

public enum GraphTypes {
	
	TRANSACTION_GRAPH("Transaction Graph"), PROFIT_GRAPH("Profit Graph"), REPORT_PAGE("Trends Graph");

	private String name;

	GraphTypes(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}
}
