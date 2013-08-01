package com.axatrikx.beans;

public class Response {
	
	private boolean isValid;
	private String message;
	
	/**
	 * Whether request is valid or not.
	 * @return True if valid, False otherwise.
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * Set the validity.
	 * @param isValid
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	/**
	 * Returns the message.
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
