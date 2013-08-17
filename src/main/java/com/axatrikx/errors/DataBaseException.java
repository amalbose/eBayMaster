package com.axatrikx.errors;

import java.sql.SQLException;

public class DataBaseException extends Exception {

	private static final long serialVersionUID = -5436851781555749991L;

	public DataBaseException(String errorMsg) {
		super(errorMsg);
	}

	public DataBaseException(String message, SQLException e) {
		super(message, e);
	}
}
