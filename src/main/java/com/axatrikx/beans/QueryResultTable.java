package com.axatrikx.beans;

import java.util.ArrayList;
import java.util.HashMap;

public class QueryResultTable {

	/**
	 * Linked hashmap to store header details in 'ColumnName = ColumnType' format.
	 */
	private HashMap<String, String> headerDetails;
	private ArrayList<ArrayList<String>> resultTable;
	
	public HashMap<String, String> getHeaderDetails() {
		return headerDetails;
	}
	public void setHeaderDetails(HashMap<String, String> headerDetails) {
		this.headerDetails = headerDetails;
	}
	public ArrayList<ArrayList<String>> getResultTable() {
		return resultTable;
	}
	public void setResultTable(ArrayList<ArrayList<String>> resultTable) {
		this.resultTable = resultTable;
	}
}
