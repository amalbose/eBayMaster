package com.axatrikx.io;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.testng.log4testng.Logger;

import com.axatrikx.controllers.TransactionController;
import com.axatrikx.utils.Utils;

public class ImportWorker extends SwingWorker<Boolean, String> {

	private static final Logger log = Logger.getLogger(ImportWorker.class);

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
		TransactionController controller = new TransactionController();
		int count = 0;
		int passCount = 0;
		for (ArrayList<Object> row : tableData) {

			if (count == 0) {
				count++;
				continue;
			}

			String itemName = (String) row.get(map.get("ItemName"));

			String buyerName = "";
			Integer buyerIndex = map.get("Buyer");
			if (buyerIndex != null) {
				buyerName = (String) row.get(buyerIndex);
			}

			String location = "";
			Integer locationIndex = map.get("Location");
			if (locationIndex != null) {
				location = (String) row.get(locationIndex);
			}

			Float cost = 0.0f;
			Integer costIndex = map.get("Cost");
			if (costIndex != null) {
				cost = Float.parseFloat(((Double) row.get(costIndex)).toString());
			}

			Float price = 0.0f;
			Integer priceIndex = map.get("Price");
			if (priceIndex != null) {
				price = Float.parseFloat(((Double) row.get(priceIndex)).toString());
			}

			Float rate = 0.0f;
			Integer rateIndex = map.get("Rate");
			if (rateIndex != null) {
				rate = Float.parseFloat(((Double) row.get(rateIndex)).toString());
			}

			Date date = new Date();
			Integer dateIndex = map.get("Date");
			if (dateIndex != null) {
				date = Utils.getDate((String) row.get(dateIndex));
			}

			String categoryName = "";
			Integer categoryIndex = map.get("Category");
			if (categoryIndex != null) {
				categoryName = (String) row.get(categoryIndex);
			}

			boolean result = controller.insertTransaction(buyerName, location, cost, price, date, itemName,
					categoryName, rate);
			if (result) {
				passCount++;
				publish("Added record: " + row.toString());
			} else {
				log.error("Failed to add record" + row.toString());
				publish("Failed to add record" + row.toString());
			}
			count++;
		}
		publish("Completed Import. " + passCount + " records entered out of " + (count - 1));
		return true;
	}

	protected void done() {
		boolean status;
		try {
			status = get();
			messageArea.append("Completed " + status + "\n");
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
