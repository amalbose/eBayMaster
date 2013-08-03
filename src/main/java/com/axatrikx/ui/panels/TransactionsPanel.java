package com.axatrikx.ui.panels;

import javax.swing.JPanel;
import javax.swing.JTable;

public class TransactionsPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public TransactionsPanel() {
		
		table = new JTable();
		add(table);

	}

}
