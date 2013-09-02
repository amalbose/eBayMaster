package com.axatrikx.io;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.testng.log4testng.Logger;

import com.axatrikx.controllers.TransactionController;
import com.axatrikx.errors.FieldValueError;
import com.axatrikx.utils.Utils;

public class ImportWorker extends SwingWorker<Boolean, String> {

	private static final Logger log = Logger.getLogger(ImportWorker.class);

	private ArrayList<ArrayList<Object>> tableData;
	private HashMap<String, Integer> map;
	private StyledDocument doc;
	private JProgressBar progressBar;
	private JCheckBox chckbxImport;

	private int noOfRows;

	public ImportWorker(ArrayList<ArrayList<Object>> tableData, HashMap<String, Integer> map,
			final JTextPane messageArea, JProgressBar progressBar, JCheckBox chckbxImport) {
		this.tableData = tableData;
		this.map = map;
		this.doc = messageArea.getStyledDocument();
		this.noOfRows = tableData.size();
		this.progressBar = progressBar;
		this.chckbxImport = chckbxImport;
	}

	@Override
	protected Boolean doInBackground() {
		TransactionController controller = new TransactionController();
		int count = 0;
		int passCount = 0;
		boolean result;
		for (ArrayList<Object> row : tableData) {

			if (count == 0) {
				count++;
				continue;
			}
			result = false;
			try {
				String itemName;
				try {
					itemName = (String) row.get(map.get("ItemName"));
				} catch (Exception e) {
					throw new FieldValueError("Error while processing Item Name field(" + row.get(map.get("ItemName"))
							+ "). Please recheck data", e);
				}
				String buyerName = "";
				Integer buyerIndex = map.get("Buyer");
				if (buyerIndex != null) {
					try {
						buyerName = (String) row.get(buyerIndex);
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Buyer field(" + row.get(map.get("Buyer"))
								+ "). Please recheck data", e);
					}
				}
				String location = "";
				Integer locationIndex = map.get("Location");
				if (locationIndex != null) {
					try {
						location = (String) row.get(locationIndex);
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Location field("
								+ row.get(map.get("Location")) + "). Please recheck data", e);
					}
				}
				Float cost = 0.0f;
				Integer costIndex = map.get("Cost");
				if (costIndex != null) {
					try {
						cost = Float.parseFloat(((Double) row.get(costIndex)).toString());
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Cost field(" + row.get(map.get("Cost"))
								+ "). Please recheck data", e);
					}
				}
				Float price = 0.0f;
				Integer priceIndex = map.get("Price");
				if (priceIndex != null) {
					try {
						price = Float.parseFloat(((Double) row.get(priceIndex)).toString());
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Price field(" + row.get(map.get("Price"))
								+ "). Please recheck data", e);
					}
				}
				Float rate = 0.0f;
				Integer rateIndex = map.get("Rate");
				if (rateIndex != null) {
					try {
						rate = Float.parseFloat(((Double) row.get(rateIndex)).toString());
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Rate field(" + row.get(map.get("Rate"))
								+ "). Please recheck data", e);
					}
				}
				Date date = new Date();
				Integer dateIndex = map.get("Date");
				if (dateIndex != null) {
					try {
						date = Utils.getDate((String) row.get(dateIndex));
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Date field(" + row.get(map.get("Date"))
								+ "). Please recheck data", e);
					}
				}
				String categoryName = "";
				Integer categoryIndex = map.get("Category");
				if (categoryIndex != null) {
					try {
						categoryName = (String) row.get(categoryIndex);
					} catch (Exception e) {
						throw new FieldValueError("Error while processing Category field("
								+ row.get(map.get("Category")) + "). Please recheck data", e);
					}
				}
				result = controller.insertTransaction(buyerName, location, cost, price, date, itemName, categoryName,
						rate);
			} catch (Exception e) {
				log.error("Failed to add record " + e.getMessage(), e);
				publish("[RED]" + "Failed to add record" + row.toString());
				publish("[EXCEPTION]" + e.getMessage());
				count++;
				continue;
			}
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
		this.progressBar.setValue(100);
		this.chckbxImport.setSelected(true);
	}

	protected void process(List<String> chunks) {
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		for (String string : chunks) {
			if (string.startsWith("[RED]")) {
				StyleConstants.setForeground(keyWord, new Color(0x8A0808));
				string = string.replace("[RED]", "");
			} else if (string.startsWith("[EXCEPTION]")) {
				string = string.replace("[EXCEPTION]", "");
				StyleConstants.setForeground(keyWord, Color.RED);
			} else {
				StyleConstants.setForeground(keyWord, Color.BLACK);
			}
			try {
				this.progressBar.setValue(getProgressLength(chunks.size()));
				doc.insertString(doc.getLength(), string + "\n", keyWord);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	private int getProgressLength(int curSize) {
		return (int) (75.0 + (25 * curSize / this.noOfRows));
	}

}
