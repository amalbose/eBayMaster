package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.axatrikx.controllers.CategoryController;
import com.axatrikx.controllers.TransactionController;
import com.axatrikx.controllers.TransactionsTableModel;
import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;

public class TransactionSideBar extends JPanel {

	private static final long serialVersionUID = 1944739272411175350L;

	/**
	 * Create the panel.
	 */
	
	private JTree tree;
	public TransactionSideBar() {
		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		tree = new JTree();
		try {
			tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode() {
					{
						DefaultMutableTreeNode periodic;
						periodic = new DefaultMutableTreeNode("Periodic");
						periodic.add(new DefaultMutableTreeNode("Last Week"));
						periodic.add(new DefaultMutableTreeNode("Last Month"));
						periodic.add(new DefaultMutableTreeNode("Last Year"));
						periodic.add(new DefaultMutableTreeNode("All"));
						add(periodic);
						
						DefaultMutableTreeNode categoryBased;
						categoryBased = new DefaultMutableTreeNode("Category");
						List<String> categories = new CategoryController().getCategories();
						for(String category : categories){
							categoryBased.add(new DefaultMutableTreeNode(category));
						}
						add(categoryBased);
						
					}
				}
			));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataBaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseTableCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tree.setAutoscrolls(true);
		tree.setShowsRootHandles(true);
		tree.setLargeModel(true);
		scrollPane.setViewportView(tree);
	}
	
	public void updateTree(){
	}

}
