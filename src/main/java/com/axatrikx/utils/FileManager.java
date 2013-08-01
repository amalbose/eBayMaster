package com.axatrikx.utils;

import com.axatrikx.beans.Response;

public class FileManager {

	private String filePath;
	private String fileContent;

	public FileManager(String filePath) {
		this.filePath = filePath;
	}

	public Response writeToFile() {
		checkForFile();
		isContentSet();

		return new Response();
	}

	/**
	 * Checks if fileContent is set. Return false if content is empty string.
	 * 
	 * @return True if fileContent is set. False otherwise.
	 */
	private boolean isContentSet() {
		return !this.fileContent.isEmpty();
	}

	/**
	 * Checks if file is present. If not, creates the file including the
	 * directory path.
	 */
	private void checkForFile() {
		// TODO Auto-generated method stub

	}
}
