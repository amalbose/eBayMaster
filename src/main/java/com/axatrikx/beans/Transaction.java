package com.axatrikx.beans;

import java.util.Date;

/**
 * Transaction bean class handling all transaction details.
 * @author Amal Bose B S
 *
 */
public class Transaction {

	private int transactionId;
	private float cost;
	private float price;
	private Buyer buyer;
	private Date date;
	private float profit;
	private String itemName;
	private Category category;
	
	private static final String TRANSACTIONID_COLUMN = "TRANSACTIONID";
	private static final String COST_COLUMN = "COST";
	private static final String PRICE_COLUMN = "PRICE";
	private static final String PROFIT_COLUMN = "PROFIT";
	private static final String DATE_COLUMN = "DATE";
	private static final String ITEMNAME_COLUMN = "ITEMNAME";
	
	// header Names
	public static final String COST_HEADER = "Cost";
	public static final String PRICE_HEADER = "Price";
	public static final String PROFIT_HEADER = "Profit";
	public static final String DATE_HEADER = "Date";
	public static final String ITEMNAME_HEADER = "Item Name";
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public void setProfit(float profit) {
		this.profit = profit;
	}
	public float getProfit(){
		return profit;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public static String getCostColumn() {
		return COST_COLUMN;
	}
	public static String getPriceColumn() {
		return PRICE_COLUMN;
	}
	public static String getProfitColumn() {
		return PROFIT_COLUMN;
	}
	public static String getDateColumn() {
		return DATE_COLUMN;
	}
	public static String getTransactionIDColumn() {
		return TRANSACTIONID_COLUMN;
	}
	public static String getItemNameColumn() {
		return ITEMNAME_COLUMN;
	}
}
