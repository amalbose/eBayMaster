package com.axatrikx.beans;

/**
 * 
 * @author Amal Bose B S
 *
 * The transaction item bean containing the item fields.
 */
public class TransactionItem {

	private String itemName;
	private String itemDescription;
	private String itemCategory;
	private float itemRate;
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
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
	
	
}
