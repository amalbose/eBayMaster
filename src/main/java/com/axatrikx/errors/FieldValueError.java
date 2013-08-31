package com.axatrikx.errors;


public class FieldValueError extends Exception {

	private static final long serialVersionUID = -5533328130374074983L;

	public FieldValueError(String message, Exception e) {
		super(message, e);
	}
}
