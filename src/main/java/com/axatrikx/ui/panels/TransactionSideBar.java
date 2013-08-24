package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TransactionSideBar extends JPanel {

	private static final long serialVersionUID = 1944739272411175350L;
	private JList<String> transactionList;

	public TransactionSideBar() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout(0, 0));

		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.NORTH);

		JLabel lblTransactions = new JLabel("Transactions");
		panel_2.add(lblTransactions);

		transactionList = new JList<String>();
		transactionList.setFixedCellHeight(20);
		transactionList.setSelectionForeground(Color.BLACK);
		transactionList.setSelectionBackground(Color.LIGHT_GRAY);
		transactionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent ev) {
				if (ev.getValueIsAdjusting()) {
					//
				}
			}
		});
		transactionList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = -1628341935973120123L;
			String[] values = new String[] { "Last Week", "Last Month", "Last Year", "All" };

			public int getSize() {
				return values.length;
			}

			public String getElementAt(int index) {
				return values[index];
			}
		});
		panel_1.add(transactionList);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel_1.add(panel, BorderLayout.WEST);
	}
}
