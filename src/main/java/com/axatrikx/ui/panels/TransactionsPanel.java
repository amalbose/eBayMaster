package com.axatrikx.ui.panels;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.axatrikx.controllers.TransactionsTableModel;

public class TransactionsPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 * 
	 * @throws Exception
	 */
	public TransactionsPanel() throws Exception {

		/*
		 * String query = "Select * from EBAYMASTERDB.TRANSACTIONS"; try { table = new JTable(new
		 * TransactionsTableModel(query)); } catch (Exception e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		table = new JTable();
		table.setModel(new TransactionsTableModel(
				"SELECT T.TRANSACTIONID, T.ITEMID, T.BUYERNAME, T.LOCATION, T.COST, T.PRICE, T.PROFIT, T.DATE, I.ITEMNAME, I.CATEGORY, I.RATE FROM EBAYMASTERDB.TRANSACTIONS AS T, EBAYMASTERDB.ITEMS AS I WHERE I.ITEMID = T.ITEMID"));
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		int colCount = table.getColumnModel().getColumnCount();
		for (int i = 0; i < colCount; i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(250);
		}
		scrollPane.setPreferredSize(new Dimension(1024, 400));
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TableDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		TransactionsPanel newContentPane = null;
		try {
			newContentPane = new TransactionsPanel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

}
