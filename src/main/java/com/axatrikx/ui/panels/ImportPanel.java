package com.axatrikx.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

public class ImportPanel extends JPanel {

	private static final long serialVersionUID = 2415734999610676932L;
	private JTextField filePathTF;

	/**
	 * Create the panel.
	 */
	public ImportPanel() {
		setLayout(new MigLayout("", "[grow][]", "[][grow]"));

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		add(panel, "cell 0 0,alignx left,growy");
		panel.setLayout(new MigLayout("", "[grow][][][284.00][][][][][]", "[grow][]"));

		JLabel lblFileToImport = new JLabel("Select file to import");
		panel.add(lblFileToImport, "cell 1 1,growy");

		filePathTF = new JTextField();
		filePathTF.setPreferredSize(new Dimension(500, 20));
		filePathTF.setMaximumSize(new Dimension(400, 2147483647));
		panel.add(filePathTF, "cell 3 1,grow");
		filePathTF.setColumns(30);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileChooser();
			}
		});
		panel.add(btnBrowse, "cell 4 1,growy");

		Component horizontalStrut = Box.createHorizontalStrut(100);
		panel.add(horizontalStrut, "cell 5 1");

		JLabel lblSheet = new JLabel("Sheet");
		panel.add(lblSheet, "cell 6 1,aligny center");

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Sheet 1", "Sheet 2", "Sheet 3" }));
		panel.add(comboBox, "cell 7 1,growx,aligny center");

		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[][][][]", "[][]"));

		JLabel lblItemName = new JLabel("Item Name");
		panel_1.add(lblItemName, "cell 0 1");

		JLabel lblCategory = new JLabel("Category");
		panel_1.add(lblCategory, "cell 3 1");

	}

	private void openFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter((new javax.swing.filechooser.FileFilter() {
			private String fileName;

			@Override
			public String getDescription() {
				return "XML or CSV Documents (*.xml and *.csv)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					fileName = f.getName().toLowerCase();
					return fileName.endsWith(".xml") || fileName.endsWith(".csv");
				}
			}
		}));
		fileChooser.setFileFilter((new javax.swing.filechooser.FileFilter() {
			private String fileName;

			@Override
			public String getDescription() {
				return "Excel Documents (*.xls and *.xlsx)";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					fileName = f.getName().toLowerCase();
					return fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
				}
			}
		}));
		fileChooser.setAcceptAllFileFilterUsed(false);
		if (fileChooser.showOpenDialog(this) == 0) {
			String filePath = fileChooser.getSelectedFile().getAbsolutePath();
			filePathTF.setText(filePath);
		}
	}
}
