package com.axatrikx.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;

import com.axatrikx.beans.IOController;
import com.axatrikx.io.ExcelController;

public class IEController {

	private String filePath;

	private FileType fileType;
	private IOController controller;

	enum FileType {
		EXCEL, XML, CSV;
	}

	public IEController(String filePath) throws InvalidFormatException, IOException {
		this.filePath = filePath;
		processFile();
	}

	private void processFile() throws InvalidFormatException, IOException {
		// TODO Set file type
		this.fileType = FileType.EXCEL;
		this.controller = new ExcelController(this.filePath);
	}

	public List<String> getSheetNames() {
		return this.controller.getSheetNames();
	}
	
	public List<String> getHeaders() {
		return this.controller.getHeaders();
	}
	
	public ArrayList<ArrayList<Object>> getTableData(Sheet curSheet) {
		return this.controller.getTableData(curSheet);
	}
	
	public ArrayList<ArrayList<Object>> getTableData() {
		return this.controller.getTableData();
	}
}
