package com.axatrikx.beans;

import java.sql.Date;

public class Transaction {

	private TransactionItem item;
	private float cost;
	private float price;
	private Buyer buyer;
	private Date date;
	
	public TransactionItem getItem() {
		return item;
	}
	public void setItem(TransactionItem item) {
		this.item = item;
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
}
