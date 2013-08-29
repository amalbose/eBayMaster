package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.axatrikx.controllers.IEController;
import com.axatrikx.io.ImportWorker;
import com.axatrikx.utils.SystemUtils;

public class ImportPanel extends JPanel {

	private static final long serialVersionUID = 2415734999610676932L;
	private JTextField filePathTF;

	private IEController controller;
	private JComboBox<String> sheetNamesCB;
	private JLabel lblFileAdded;

	private JComboBox<String> itemHeaderCB;
	private JComboBox<String> categoryHeaderCB;
	private JComboBox<String> rateHeaderCB;
	private JComboBox<String> buyerHeaderCB;
	private JComboBox<String> locationHeaderCB;
	private JComboBox<String> priceHeaderCB;
	private JComboBox<String> costHeaderCB;
	private JComboBox<String> profitHeaderCB;
	private JComboBox<String> dateHeaderCB;
	private JButton importBtn;
	private JTextArea consoleArea;
	private JCheckBox chckbxMappingCompleted;

	/**
	 * Create the panel.
	 */
	public ImportPanel() {
		setPreferredSize(new Dimension(800, 400));
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		setLayout(new MigLayout("", "[grow][grow]", "[][][grow][grow]"));

		JPanel fileSelectionPanel = new JPanel();
		fileSelectionPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		add(fileSelectionPanel, "cell 0 0,alignx left,growy");
		fileSelectionPanel.setLayout(new MigLayout("", "[grow][][][350][][][][100px][]", "[grow][]"));

		JLabel lblFileToImport = new JLabel("Select file to import");
		fileSelectionPanel.add(lblFileToImport, "cell 1 1,growy");

		filePathTF = new JTextField();
		filePathTF.setPreferredSize(new Dimension(500, 20));
		fileSelectionPanel.add(filePathTF, "cell 3 1,grow");
		filePathTF.setColumns(30);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFileChooser();
			}
		});
		fileSelectionPanel.add(btnBrowse, "cell 4 1,growy");

		Component horizontalStrut = Box.createHorizontalStrut(100);
		fileSelectionPanel.add(horizontalStrut, "cell 5 1");

		JLabel lblSheet = new JLabel("Select Sheet");
		fileSelectionPanel.add(lblSheet, "cell 6 1,aligny center");

		sheetNamesCB = new JComboBox<String>();
		sheetNamesCB.setModel(new DefaultComboBoxModel<String>(new String[] { "Default" }));
		fileSelectionPanel.add(sheetNamesCB, "cell 7 1,growx,aligny center");

		JPanel notifyPanel = new JPanel();
		add(notifyPanel, "cell 0 1,growx,aligny center");
		notifyPanel.setLayout(new MigLayout("", "[][3px][][][]", "[14px]"));

		lblFileAdded = new JLabel();
		lblFileAdded.setFont(new Font("Tahoma", Font.BOLD, 11));
		notifyPanel.add(lblFileAdded, "cell 1 0,alignx left,aligny top");

		JPanel panel_1 = new JPanel();
		add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel mappingPanel = new JPanel();
		panel_1.add(mappingPanel, BorderLayout.CENTER);
		mappingPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Header Mapping",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		mappingPanel.setLayout(new MigLayout("", "[grow][150px][grow][150px][grow][150px][grow]",
				"[][][][][][][][][][][][20px][]"));

		JLabel lblMapFieldsTo = new JLabel("Map fields to headers and check 'Mapping Completed' checkbox");
		mappingPanel.add(lblMapFieldsTo, "cell 1 0 3 1");

		JLabel lblItemName = new JLabel("Item Name");
		mappingPanel.add(lblItemName, "cell 1 2");

		final JCheckBox chckbxCategory = new JCheckBox("Category");
		chckbxCategory.setSelected(true);
		chckbxCategory.setToolTipText("Select if Category is being imported");
		chckbxCategory.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				categoryHeaderCB.setEnabled(chckbxCategory.isSelected());
			}
		});
		mappingPanel.add(chckbxCategory, "cell 3 2");

		final JCheckBox chckbxRate = new JCheckBox("Rate");
		chckbxRate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				rateHeaderCB.setEnabled(chckbxRate.isSelected());
			}
		});
		mappingPanel.add(chckbxRate, "cell 5 2");

		itemHeaderCB = new JComboBox<String>();
		mappingPanel.add(itemHeaderCB, "cell 1 3,growx");

		categoryHeaderCB = new JComboBox<String>();
		mappingPanel.add(categoryHeaderCB, "cell 3 3,growx");

		rateHeaderCB = new JComboBox<String>();
		rateHeaderCB.setEnabled(false);
		mappingPanel.add(rateHeaderCB, "cell 5 3,growx");

		final JCheckBox chckbxBuyer = new JCheckBox("Buyer");
		chckbxBuyer.setSelected(true);
		chckbxBuyer.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				buyerHeaderCB.setEnabled(chckbxBuyer.isSelected());
			}
		});
		mappingPanel.add(chckbxBuyer, "cell 1 5");

		final JCheckBox chckbxLocation = new JCheckBox("Location");
		chckbxLocation.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				locationHeaderCB.setEnabled(chckbxLocation.isSelected());
			}
		});
		mappingPanel.add(chckbxLocation, "cell 3 5");

		final JCheckBox chckbxPrice = new JCheckBox("Price");
		chckbxPrice.setSelected(true);
		chckbxPrice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				priceHeaderCB.setEnabled(chckbxPrice.isSelected());
			}
		});
		mappingPanel.add(chckbxPrice, "cell 5 5");

		buyerHeaderCB = new JComboBox<String>();
		mappingPanel.add(buyerHeaderCB, "cell 1 6,growx");

		locationHeaderCB = new JComboBox<String>();
		locationHeaderCB.setEnabled(false);
		mappingPanel.add(locationHeaderCB, "cell 3 6,growx");

		priceHeaderCB = new JComboBox<String>();
		mappingPanel.add(priceHeaderCB, "cell 5 6,growx");

		final JCheckBox chckbxCost = new JCheckBox("Cost");
		chckbxCost.setSelected(true);
		chckbxCost.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				costHeaderCB.setEnabled(chckbxCost.isSelected());
			}
		});
		mappingPanel.add(chckbxCost, "cell 1 8");

		final JCheckBox chckbxProfit = new JCheckBox("Profit");
		chckbxProfit.setSelected(true);
		chckbxProfit.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				profitHeaderCB.setEnabled(chckbxProfit.isSelected());
			}
		});
		mappingPanel.add(chckbxProfit, "cell 3 8");

		final JCheckBox chckbxDate = new JCheckBox("Date");
		chckbxDate.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				dateHeaderCB.setEnabled(chckbxDate.isSelected());
			}
		});
		mappingPanel.add(chckbxDate, "cell 5 8");

		costHeaderCB = new JComboBox<String>();
		mappingPanel.add(costHeaderCB, "cell 1 9,growx");

		profitHeaderCB = new JComboBox<String>();
		mappingPanel.add(profitHeaderCB, "cell 3 9,growx");

		dateHeaderCB = new JComboBox<String>();
		mappingPanel.add(dateHeaderCB, "cell 5 9,growx");

		chckbxMappingCompleted = new JCheckBox("Mapping Completed");
		chckbxMappingCompleted.setEnabled(false);
		chckbxMappingCompleted.setSelected(true);
		chckbxMappingCompleted.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
				toggleAllCheckBoxes(chckbxMappingCompleted.isSelected());
			}
		});
		mappingPanel.add(chckbxMappingCompleted, "cell 5 10,alignx right");

		importBtn = new JButton("Start Import");
		mappingPanel.add(importBtn, "cell 5 12,alignx right");
		importBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// process import
				importData();
			}
		});
		importBtn.setEnabled(false);

		JPanel tipPanel = new JPanel();
		tipPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		tipPanel.setPreferredSize(new Dimension(400, 10));
		add(tipPanel, "cell 1 2 1 2,grow");
		tipPanel.setLayout(new BorderLayout(0, 0));

		JPanel tipHeader = new JPanel();
		tipPanel.add(tipHeader, BorderLayout.NORTH);

		JLabel lblStepsForImporting = new JLabel("Steps for Import");
		lblStepsForImporting.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStepsForImporting.setHorizontalAlignment(SwingConstants.CENTER);
		tipHeader.add(lblStepsForImporting);

		JPanel panel = new JPanel();
		add(panel, "cell 0 3,grow");
		panel.setLayout(new BorderLayout(0, 0));

		consoleArea = new JTextArea();
		consoleArea.setTabSize(4);
		consoleArea.setRows(10);
		consoleArea.setEditable(false);
		consoleArea.setWrapStyleWord(true);
		consoleArea.setLineWrap(true);
		panel.add(new JScrollPane(consoleArea), BorderLayout.CENTER);

	}

	protected void toggleAllCheckBoxes(boolean enabled) {
		itemHeaderCB.setEnabled(enabled);
		categoryHeaderCB.setEnabled(enabled);
		rateHeaderCB.setEnabled(enabled);
		buyerHeaderCB.setEnabled(enabled);
		locationHeaderCB.setEnabled(enabled);
		costHeaderCB.setEnabled(enabled);
		profitHeaderCB.setEnabled(enabled);
		priceHeaderCB.setEnabled(enabled);
		dateHeaderCB.setEnabled(enabled);
		
		// TODO enable and disable checkboxes
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

				// enable Import button
				importBtn.setEnabled(true);
				chckbxMappingCompleted.setEnabled(true);
			}
		}
	}

	private void importData() {
		// TODO Auto-generated method stub
		ImportWorker impWorker = new ImportWorker(controller.getTableData(), null, consoleArea);
		impWorker.execute();
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

		// gets sheets
		Object[] sheetNameArray = controller.getSheetNames().toArray();
		sheetNamesCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(sheetNameArray, sheetNameArray.length,
				String[].class)));
		sheetNamesCB.revalidate();
		lblFileAdded.setText("File added:     " + SystemUtils.getFileNameFromPath(filePath));
		lblFileAdded.setToolTipText(filePath);

		// set Table header model
		Object[] headerArray = controller.getHeaders().toArray();
		itemHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		categoryHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		rateHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		buyerHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		locationHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		costHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		profitHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		priceHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

		dateHeaderCB.setModel(new DefaultComboBoxModel<String>(Arrays.copyOf(headerArray, headerArray.length,
				String[].class)));

	}

	private boolean verifyFile(String filePath) {
		// TODO Check if extension is proper
		return true;
	}
}
