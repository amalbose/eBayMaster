package com.axatrikx.beans;

/**
 * 
 * @author Amal Bose B S
 *
 * The transaction item bean containing the item fields.
 */
public class Category {
	private int categoryId;
	private String categoryName;
	private float rate;
	
	private static final String CATEGORYID_COLUMN = "CATEGORYID";
	private static final String CATEGORY_COLUMN = "CATEGORYNAME";
	private static final String RATE_COLUMN = "RATE";
	
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public static String getCategoryIdColumn() {
		return CATEGORYID_COLUMN;
	}
	public static String getRateColumn() {
		return RATE_COLUMN;
	}
	public static String getCategoryNameColumn() {
		return CATEGORY_COLUMN;
	}
}
