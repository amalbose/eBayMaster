package com.axatrikx.ui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.axatrikx.controllers.IEController;

import net.miginfocom.swing.MigLayout;

public class ImportPanel extends JPanel {

	private static final long serialVersionUID = 2415734999610676932L;
	private JTextField filePathTF;

	private IEController controller;
	private JComboBox<String> sheetNamesCB;
	private JLabel lblFileAdded;

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

		sheetNamesCB = new JComboBox<String>();
		sheetNamesCB.setModel(new DefaultComboBoxModel<String>(new String[] { "Sheet 1", "Sheet 2", "Sheet 3" }));
		panel.add(sheetNamesCB, "cell 7 1,growx,aligny center");

		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[][][][]", "[][]"));
		
		lblFileAdded = new JLabel();
		panel_1.add(lblFileAdded, "cell 1 0");

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
			if (verifyFile(filePath)) {
				processFile(filePath);
			}
		}
	}

	/**
	 * Processes the file to get info.
	 * 
	 * @param filePath
	 */
	private void processFile(String filePath) {
		filePathTF.setText(filePath);
		try {
			controller = new IEController(filePath);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//gets sheets
		Object[] objectArray = controller.getSheetNames().toArray();
		System.out.println(controller.getSheetNames());
		getSheetNamesCB().setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(objectArray, objectArray.length, String[].class)));
		getSheetNamesCB().revalidate();
		getLblFileAdded().setText("File added: " + filePath);
	}

	private boolean verifyFile(String filePath) {
		// TODO Check if extension is proper
		return true;
	}

	private JComboBox<String> getSheetNamesCB() {
		return sheetNamesCB;
	}
	protected JLabel getLblFileAdded() {
		return lblFileAdded;
	}
}
