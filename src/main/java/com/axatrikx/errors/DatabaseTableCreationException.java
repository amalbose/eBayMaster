package com.axatrikx.errors;

import java.sql.SQLException;

public class DatabaseTableCreationException extends Exception {

	private static final long serialVersionUID = 331061481832730536L;

	public DatabaseTableCreationException(String message, SQLException e) {
		super(message, e);
	}
}
