package com.axatrikx.beans;

import java.util.List;

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

}
