package com.axatrikx.ui.panels;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.axatrikx.controllers.TransactionsTableModel;
import com.axatrikx.utils.CommonSettings;

public class TransactionsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7194810207131313783L;

	private JTable table;

	private static final String QUERY_TRANS_DETAIL_TKN = "QUERY_TRANS_DETAIL";
	private static final String DEFAULT_WINDOW_WIDTH_TKN = "DEFAULT_WINDOW_WIDTH";
	private static final String DEFAULT_WINDOW_HEIGHT_TKN = "DEFAULT_WINDOW_HEIGHT";

	/**
	 * Create the panel.
	 * 
	 * @throws Exception
	 */
	public TransactionsPanel() throws Exception {
		table = new JTable();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		displayUI();
	}

	private void displayUI() throws Exception {
		// Getting query for model.
		table.setModel(new TransactionsTableModel(CommonSettings.getDBSelectQuery(QUERY_TRANS_DETAIL_TKN)));
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// Settings the window size.
		table.setPreferredScrollableViewportSize(new Dimension(Integer.parseInt(CommonSettings
				.getSettingValue(DEFAULT_WINDOW_WIDTH_TKN)), Integer.parseInt(CommonSettings
				.getSettingValue(DEFAULT_WINDOW_HEIGHT_TKN))));
		table.setFillsViewportHeight(true);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the scroll pane to this panel.
		add(scrollPane);
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
