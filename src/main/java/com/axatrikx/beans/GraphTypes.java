package com.axatrikx.beans;

public enum GraphTypes {

	TRANSACTION_GRAPH("Transaction Graph", 0), PROFIT_GRAPH("Profit Graph", 1), TRENDS_GRAPH("Trends Graph", 2);

	private String name;
	private int index;

	GraphTypes(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public String toString() {
		return this.name;
	}

	public int tgetIndex() {
		return this.index;
	}
}
