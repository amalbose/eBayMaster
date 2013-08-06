package com.axatrikx.beans;

/**
 * 
 * @author Amal Bose B S
 *
 * The transaction item bean containing the item fields.
 */
public class TransactionItem {
	private int itemId;
	private String itemName;
	private String itemCategory;
	private float itemRate;
	
	private static final String ITEMID_COLUMN = "ITEMID";
	private static final String ITEM_COLUMN = "ITEMNAME";
	private static final String CATEGORY_COLUMN = "CATEGORY";
	private static final String RATE_COLUMN = "RATE";
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCategory() {
		return itemCategory;
	}
	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}
	public float getItemRate() {
		return itemRate;
	}
	public void setItemRate(float itemRate) {
		this.itemRate = itemRate;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public static String getItemColumn() {
		return ITEM_COLUMN;
	}
	public static String getCategoryColumn() {
		return CATEGORY_COLUMN;
	}
	public static String getRateColumn() {
		return RATE_COLUMN;
	}
	public static String getItemIDColumn() {
		return ITEMID_COLUMN;
	}
}
