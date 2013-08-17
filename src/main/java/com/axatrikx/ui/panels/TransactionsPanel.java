package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.axatrikx.controllers.TransactionController;
import com.axatrikx.controllers.TransactionsTableModel;
import com.axatrikx.db.DatabaseController;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;

public class TransactionsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7194810207131313783L;

	private JTable table;
	private int selectedRow = -1;
	private int selectedColumn = -1;

	private static final String QUERY_TRANS_DETAIL_TKN = "QUERY_TRANS_DETAIL";

	/**
	 * Create the panel.
	 * @throws DatabaseTableCreationException 
	 * @throws DataBaseException 
	 * @throws ClassNotFoundException 
	 * 
	 * @throws Exception
	 */
	public TransactionsPanel() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		setLayout(new BorderLayout());
		table = new JTable() {

			private static final long serialVersionUID = 8390383435099848025L;

			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width,
						tableColumn.getPreferredWidth()));
				return component;
			}
		};
		table.setDragEnabled(true);
		table.setAutoCreateRowSorter(true);
		displayUI();
	}

	private void displayUI() throws ClassNotFoundException, DataBaseException, DatabaseTableCreationException {
		table.setModel(new TransactionsTableModel(processSelectQuery(TransactionController.getDBSelectQuery(QUERY_TRANS_DETAIL_TKN))));
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		TableColumnModel tcm = table.getColumnModel();

		decorateTable(tcm);

		// Settings the window size.
		table.setFillsViewportHeight(true);
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// Add the scroll pane to this panel.
		add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Returns the query with Database name token replaced with actual DB name.
	 * @param dbSelectQuery
	 * @return
	 */
	private String processSelectQuery(String dbSelectQuery) {
		return dbSelectQuery.replace(DatabaseController.getDatabaseNameToken(), DatabaseController.getDatabaseName());
	}

	private void decorateTable(TableColumnModel tcm) {

		hideColumn(tcm, TransactionsTableModel.getColumnNames()[0]);
		hideColumn(tcm, TransactionsTableModel.getColumnNames()[1]);

		table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()) {

			private static final long serialVersionUID = 3524303979037876874L;

			public boolean isCellEditable(EventObject e) {
				if (e instanceof MouseEvent) {
					if (((MouseEvent) e).getClickCount() == 2 && !((MouseEvent) e).isConsumed()) {
						((MouseEvent) e).consume();
						return true;
					}
				} else if (selectedRow != -1 && selectedColumn != -1) {
					return true;
				}
				return false;
			}
		});

		// context menu
		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem editItem = new JMenuItem("Edit");
		JMenuItem deleteItem = new JMenuItem("Delete");

		editItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.editCellAt(selectedRow, selectedColumn);
			}
		});
		popupMenu.add(editItem);
		popupMenu.add(deleteItem);
		table.setComponentPopupMenu(popupMenu);

		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}

			public void mouseReleased(MouseEvent me) {
				// showPopup(me);
			}

			private void showPopup(MouseEvent me) {
				// is this event a popup trigger?
				if (me.getButton() == MouseEvent.BUTTON3) {
					Point p = me.getPoint();
					selectedRow = table.rowAtPoint(p);
					selectedColumn = table.columnAtPoint(p);
				}
			}
		});
	}

	private void hideColumn(TableColumnModel tcm, String columnName) {
		int index = tcm.getColumnIndex(columnName);
		TableColumn column = tcm.getColumn(index);
		tcm.removeColumn(column);
	}
}