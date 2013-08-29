package com.axatrikx.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.axatrikx.controllers.TransactionController;

public class ImportWorker extends SwingWorker<Boolean, String> {

	private ArrayList<ArrayList<Object>> tableData;
	private HashMap<String, Integer> map;
	private JTextArea messageArea;

	public ImportWorker(ArrayList<ArrayList<Object>> tableData, HashMap<String, Integer> map,
			final JTextArea messageArea) {
		this.tableData = tableData;
		this.map = map;
		this.messageArea = messageArea;
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		// TODO Auto-generated method stub

		TransactionController controller = new TransactionController();
		int count = 0;
		for (ArrayList<Object> row : tableData) {
			/*
			 * String itemName = (String) row.get(map.get("ItemName")); String buyerName = (String)
			 * row.get(map.get("BuyerName")); String location = (String) row.get(map.get("Location")); Integer cost =
			 * (Integer) row.get(map.get("Cose")); Integer price = (Integer) row.get(map.get("Price")); Float rate =
			 * (Float) row.get(map.get("Rate")); Date date = (Date) row.get(map.get("Date")); String categoryName =
			 * (String) row.get(map.get("CategoryName")); controller.insertTransaction(buyerName, location, cost, price,
			 * date, itemName, categoryName, rate);
			 */
			publish("Processing row: " + row.toString());
		}
		return true;
	}

	protected void done() {
		boolean status;
		try {
			status = get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void process(List<String> chunks) {
		for (final String string : chunks) {
			messageArea.append(string);
			messageArea.append("\n");
		}
	}

}
