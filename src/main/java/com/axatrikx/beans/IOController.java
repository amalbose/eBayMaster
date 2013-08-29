package com.axatrikx.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;

public abstract class IOController {

	/**
	 * Gets the list of sheet names;
	 * 
	 * @return
	 */
	public abstract List<String> getSheetNames();

	/**
	 * Returns ths header column as a list of string.
	 * 
	 * @return
	 */
	public abstract List<String> getHeaders();
	
	/**
	 * Returns the table data for given sheet.
	 * @param curSheet
	 * @return
	 */
	public abstract ArrayList<ArrayList<Object>> getTableData(Sheet curSheet);

	/**
	 * Returns the table data for first sheet.
	 * @param curSheet
	 * @return
	 */
	public abstract ArrayList<ArrayList<Object>> getTableData();
}
