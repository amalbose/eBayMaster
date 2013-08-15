package com.axatrikx.ui.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.axatrikx.controllers.TransactionController;
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
		table = new JTable(){
			 @Override
	         public Component prepareRenderer(TableCellRenderer renderer, int row,
	                 int column) {
	            Component component = super.prepareRenderer(renderer, row, column);
	            int rendererWidth = component.getPreferredSize().width;
	            TableColumn tableColumn = getColumnModel().getColumn(column);
	            tableColumn.setPreferredWidth(Math.max(rendererWidth +
	                    getIntercellSpacing().width,
	                    tableColumn.getPreferredWidth()));
	            return  component;
	         }
		};
		displayUI();
	}

	private void displayUI() throws Exception {
		// Getting query for model.
		table.setModel(new TransactionsTableModel(TransactionController.getDBSelectQuery(QUERY_TRANS_DETAIL_TKN)));
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setWidth(160);
		hideColumn(tcm, TransactionsTableModel.getColumnNames()[0]);
		hideColumn(tcm, TransactionsTableModel.getColumnNames()[1]);
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

	private void hideColumn(TableColumnModel tcm, String columnName) {
		int index = tcm.getColumnIndex(columnName);
		TableColumn column = tcm.getColumn(index);
		tcm.removeColumn(column);
	}
}
