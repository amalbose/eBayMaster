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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.axatrikx.controllers.IEController;
import com.axatrikx.io.ImportWorker;
import com.axatrikx.utils.SystemUtils;

import net.miginfocom.swing.MigLayout;

import java.awt.Font;

import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.EtchedBorder;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JTextArea;

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

	/**
	 * Create the panel.
	 */
	public ImportPanel() {
		setPreferredSize(new Dimension(800, 400));
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		setLayout(new MigLayout("", "[grow][grow][]", "[][][grow][grow][]"));

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

		JPanel mappingPanel = new JPanel();
		add(mappingPanel, "cell 0 2,grow");
		mappingPanel.setLayout(new MigLayout("", "[50px][150px][50px][150px][50px][150px][]", "[][][][][][][][][][20px][]"));

		JLabel lblItemName = new JLabel("Item Name");
		mappingPanel.add(lblItemName, "cell 1 1");

		final JCheckBox chckbxCategory = new JCheckBox("Category");
		chckbxCategory.setSelected(true);
		chckbxCategory.setToolTipText("Select if Category is being imported");
		chckbxCategory.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				categoryHeaderCB.setEnabled(chckbxCategory.isSelected());
			}
		});
		mappingPanel.add(chckbxCategory, "cell 3 1");

		final JCheckBox chckbxRate = new JCheckBox("Rate");
		chckbxRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				rateHeaderCB.setEnabled(chckbxRate.isSelected());
			}
		});
		mappingPanel.add(chckbxRate, "cell 5 1");

		itemHeaderCB = new JComboBox<String>();
		mappingPanel.add(itemHeaderCB, "cell 1 2,growx");

		categoryHeaderCB = new JComboBox<String>();
		mappingPanel.add(categoryHeaderCB, "cell 3 2,growx");

		rateHeaderCB = new JComboBox<String>();
		rateHeaderCB.setEnabled(false);
		mappingPanel.add(rateHeaderCB, "cell 5 2,growx");

		final JCheckBox chckbxBuyer = new JCheckBox("Buyer");
		chckbxBuyer.setSelected(true);
		chckbxBuyer.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				buyerHeaderCB.setEnabled(chckbxBuyer.isSelected());
			}
		});
		mappingPanel.add(chckbxBuyer, "cell 1 4");

		final JCheckBox chckbxLocation = new JCheckBox("Location");
		chckbxLocation.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				locationHeaderCB.setEnabled(chckbxLocation.isSelected());
			}
		});
		mappingPanel.add(chckbxLocation, "cell 3 4");

		final JCheckBox chckbxPrice = new JCheckBox("Price");
		chckbxPrice.setSelected(true);
		chckbxPrice.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				priceHeaderCB.setEnabled(chckbxPrice.isSelected());
			}
		});
		mappingPanel.add(chckbxPrice, "cell 5 4");

		buyerHeaderCB = new JComboBox<String>();
		mappingPanel.add(buyerHeaderCB, "cell 1 5,growx");

		locationHeaderCB = new JComboBox<String>();
		locationHeaderCB.setEnabled(false);
		mappingPanel.add(locationHeaderCB, "cell 3 5,growx");

		priceHeaderCB = new JComboBox<String>();
		mappingPanel.add(priceHeaderCB, "cell 5 5,growx");

		final JCheckBox chckbxCost = new JCheckBox("Cost");
		chckbxCost.setSelected(true);
		chckbxCost.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				costHeaderCB.setEnabled(chckbxCost.isSelected());
			}
		});
		mappingPanel.add(chckbxCost, "cell 1 7");

		final JCheckBox chckbxProfit = new JCheckBox("Profit");
		chckbxProfit.setSelected(true);
		chckbxProfit.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				profitHeaderCB.setEnabled(chckbxProfit.isSelected());
			}
		});
		mappingPanel.add(chckbxProfit, "cell 3 7");

		final JCheckBox chckbxDate = new JCheckBox("Date");
		chckbxDate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				dateHeaderCB.setEnabled(chckbxDate.isSelected());
			}
		});
		mappingPanel.add(chckbxDate, "cell 5 7");

		costHeaderCB = new JComboBox<String>();
		mappingPanel.add(costHeaderCB, "cell 1 8,growx");

		profitHeaderCB = new JComboBox<String>();
		mappingPanel.add(profitHeaderCB, "cell 3 8,growx");

		dateHeaderCB = new JComboBox<String>();
		mappingPanel.add(dateHeaderCB, "cell 5 8,growx");
		
		importBtn = new JButton("Start Import");
		mappingPanel.add(importBtn, "cell 5 10,alignx right");
		importBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// process import
				importData();
			}
		});
		importBtn.setEnabled(false);
		
		JPanel tipPanel = new JPanel();
		tipPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		tipPanel.setPreferredSize(new Dimension(400, 10));
		add(tipPanel, "cell 1 2,grow");
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
		consoleArea.setEditable(false);
		consoleArea.setWrapStyleWord(true);
		consoleArea.setLineWrap(true);
		consoleArea.setRows(15);
		panel.add(new JScrollPane(consoleArea), BorderLayout.CENTER);

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
				
				//enable Import button
				importBtn.setEnabled(true);
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
