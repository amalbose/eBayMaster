package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.axatrikx.ui.main.MainFrame;

public class TransactionSideBar extends JPanel {

	private static final long serialVersionUID = 1944739272411175350L;
	private JList<String> transactionList;

	private static final String LAST_WEEK = "Last Week";
	private static final String LAST_MONTH = "Last Month";
	private static final String LAST_YEAR = "Last Year";
	private static final String ALL = "All";
	
	private static final String QUERY_TRANS_DETAIL_TKN = "QUERY_TRANS_DETAIL";
	private static final String QUERY_TRANS_DETAIL_WEEK_TKN = "QUERY_TRANS_DETAIL_WEEK";
	private static final String QUERY_TRANS_DETAIL_MONTH_TKN = "QUERY_TRANS_DETAIL_MONTH";
	private static final String QUERY_TRANS_DETAIL_YEAR_TKN = "QUERY_TRANS_DETAIL_YEAR";

	public TransactionSideBar(final MainFrame parentFrame) {
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
			@SuppressWarnings("rawtypes")
			public void valueChanged(ListSelectionEvent evt) {
				if (evt.getValueIsAdjusting()) {
					JList changedList = (JList)evt.getSource();
				    if(changedList.getSelectedValue().equals(LAST_WEEK)) {
				    	parentFrame.updateTransactionPanel(QUERY_TRANS_DETAIL_WEEK_TKN);
				    } else if(changedList.getSelectedValue().equals(LAST_MONTH)) {
				    	parentFrame.updateTransactionPanel(QUERY_TRANS_DETAIL_MONTH_TKN);
				    } else if(changedList.getSelectedValue().equals(LAST_YEAR)) {
				    	parentFrame.updateTransactionPanel(QUERY_TRANS_DETAIL_YEAR_TKN);
				    } else {
				    	parentFrame.updateTransactionPanel(QUERY_TRANS_DETAIL_TKN);
				    }
				}
			}
		});
		transactionList.setModel(new AbstractListModel<String>() {
			private static final long serialVersionUID = -1628341935973120123L;
			String[] values = new String[] { LAST_WEEK, LAST_MONTH, LAST_YEAR, ALL };

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
