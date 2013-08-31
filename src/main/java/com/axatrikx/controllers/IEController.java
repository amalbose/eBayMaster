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
	
	public List<String> getLocationKeywords(){
		List<String> locationKeywords = new ArrayList<String>();
		locationKeywords.add("location");
		locationKeywords.add("Place");
		locationKeywords.add("Country");
		return locationKeywords;
	}
	
	public List<String> getBuyerKeywords(){
		List<String> buyerKeywords = new ArrayList<String>();
		buyerKeywords.add("buyer");
		buyerKeywords.add("name");
		buyerKeywords.add("client");
		buyerKeywords.add("person");
		return buyerKeywords;
	}

	public List<String> getRateKeywords() {
		List<String> rateKeywords = new ArrayList<String>();
		rateKeywords.add("rate");
		rateKeywords.add("ebay");
		rateKeywords.add("percent");
		rateKeywords.add("reduction");
		return rateKeywords;
	}

	public List<String> getCostKeywords() {
		List<String> costKeywords = new ArrayList<String>();
		costKeywords.add("cost");
		costKeywords.add("amount");
		costKeywords.add("pay");
		return costKeywords;
	}

	public List<String> getPriceKeywords() {
		List<String> priceKeywords = new ArrayList<String>();
		priceKeywords.add("price");
		priceKeywords.add("sell");
		return priceKeywords;
	}

	public List<String> getProfitKeywords() {
		List<String> profitKeywords = new ArrayList<String>();
		profitKeywords.add("profit");
		profitKeywords.add("gain");
		return profitKeywords;
	}

	public List<String> getDateKeywords() {
		List<String> dateKeyword = new ArrayList<String>();
		dateKeyword.add("date");
		dateKeyword.add("day");
		dateKeyword.add("time");
		return dateKeyword;
	}

	public List<String> getCategoryKeywords() {
		List<String> categoryKeyword = new ArrayList<String>();
		categoryKeyword.add("category");
		categoryKeyword.add("type");
		return categoryKeyword;
	}
}
