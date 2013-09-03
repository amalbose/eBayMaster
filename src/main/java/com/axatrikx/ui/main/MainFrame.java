package com.axatrikx.ui.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.axatrikx.errors.DataBaseException;
import com.axatrikx.errors.DatabaseTableCreationException;
import com.axatrikx.ui.panels.HomePanel;
import com.axatrikx.ui.panels.HomeSideBar;
import com.axatrikx.ui.panels.ImportPanel;
import com.axatrikx.ui.panels.SettingsDialog;
import com.axatrikx.ui.panels.TransactionFormPanel;
import com.axatrikx.ui.panels.TransactionSideBar;
import com.axatrikx.ui.panels.TransactionsPanel;
import com.axatrikx.utils.Prefs;
import com.axatrikx.utils.SystemUtils;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -3828157333553193707L;

	private static final String QUERY_TRANS_DETAIL_TKN = "QUERY_TRANS_DETAIL";

	private JPanel contentPane;

	private CardLayout cardLayout;
	private CardLayout sideBarLayout;
	private JPanel mainPanel;
	private TransactionSideBar transSideBar;
	private TransactionsPanel transactionPanel;
	private JPanel transactionsPanel;
	private JPanel sidePanel;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setName("menuBar");
		menuBar.setBounds(new Rectangle(5, 0, 0, 0));
		menuBar.setAlignmentX(5.0f);
		setJMenuBar(menuBar);

		cardLayout = new CardLayout();
		sideBarLayout = new CardLayout();

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNewTransaction = new JMenuItem("New Transaction");
		mnFile.add(mntmNewTransaction);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmEditTransaction = new JMenuItem("Edit Transaction");
		mnEdit.add(mntmEditTransaction);

		JMenuItem mntmFind = new JMenuItem("Find");
		mnEdit.add(mntmFind);

		JSeparator separator_1 = new JSeparator();
		mnEdit.add(separator_1);

		JMenuItem mntmEbayFeeRates = new JMenuItem("eBay Fee Rates");
		mnEdit.add(mntmEbayFeeRates);

		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);

		JMenu mnReports = new JMenu("Reports");
		mnView.add(mnReports);

		JMenuItem mntmMonthly = new JMenuItem("Monthly");
		mnReports.add(mntmMonthly);

		JMenuItem mntmYearly = new JMenuItem("Yearly");
		mnReports.add(mntmYearly);

		JMenuItem mntmSummary = new JMenuItem("Summary");
		mnView.add(mntmSummary);

		JMenuItem mntmCategories = new JMenuItem("Categories");
		mnView.add(mntmCategories);

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);

		JMenuItem mntmImportData = new JMenuItem("Import Data");
		mnTools.add(mntmImportData);

		JMenuItem mntmExportData = new JMenuItem("Export Data");
		mnTools.add(mntmExportData);

		JMenuItem mntmEbayRates = new JMenuItem("eBay Rates");
		mnTools.add(mntmEbayRates);

		JSeparator separator_2 = new JSeparator();
		mnTools.add(separator_2);

		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnTools.add(mntmSettings);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmHelpTopics = new JMenuItem("Help Topics");
		mnHelp.add(mntmHelpTopics);

		JMenuItem mntmOnlineHelp = new JMenuItem("Online Help");
		mnHelp.add(mntmOnlineHelp);

		JSeparator separator_3 = new JSeparator();
		mnHelp.add(separator_3);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setSize(new Dimension(50, 60));
		toolBar.setPreferredSize(new Dimension(50, 60));
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton btnHome = new JButton("Home", new ImageIcon(MainFrame.class.getResource("/images/menu-24.png")));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeCard("Home");
			}
		});
		btnHome.setBorder(null);
		btnHome.setMaximumSize(new Dimension(50, 60));
		btnHome.setPreferredSize(new Dimension(50, 60));
		btnHome.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnHome.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(btnHome);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setMaximumSize(new Dimension(10, 32767));
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		horizontalStrut.setMinimumSize(new Dimension(10, 0));
		toolBar.add(horizontalStrut);

		JButton btnTransactions = new JButton("Transactions", new ImageIcon(
				MainFrame.class.getResource("/images/transaction-24.png")));
		btnTransactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				changeCard("Transactions");
			}
		});
		btnTransactions.setBorder(null);
		btnTransactions.setPreferredSize(new Dimension(50, 60));
		btnTransactions.setMaximumSize(new Dimension(75, 60));
		btnTransactions.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTransactions.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(btnTransactions);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalStrut_1.setPreferredSize(new Dimension(10, 0));
		horizontalStrut_1.setMinimumSize(new Dimension(10, 0));
		horizontalStrut_1.setMaximumSize(new Dimension(10, 32767));
		toolBar.add(horizontalStrut_1);

		JButton btnReport = new JButton("Report", new ImageIcon(MainFrame.class.getResource("/images/report-24.png")));
		btnReport.setBorder(null);
		btnReport.setMaximumSize(new Dimension(50, 60));
		btnReport.setPreferredSize(new Dimension(50, 60));
		btnReport.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnReport.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(btnReport);

		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalStrut_2.setPreferredSize(new Dimension(10, 0));
		horizontalStrut_2.setMinimumSize(new Dimension(10, 0));
		horizontalStrut_2.setMaximumSize(new Dimension(10, 32767));
		toolBar.add(horizontalStrut_2);

		JButton btnSettings = new JButton("Settings", new ImageIcon(
				MainFrame.class.getResource("/images/settings-24.png")));
		btnSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showSettingsDialog();
			}
		});
		btnSettings.setBorder(null);
		btnSettings.setMaximumSize(new Dimension(50, 60));
		btnSettings.setPreferredSize(new Dimension(50, 60));
		btnSettings.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSettings.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(btnSettings);

		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalStrut_3.setPreferredSize(new Dimension(10, 0));
		horizontalStrut_3.setMinimumSize(new Dimension(10, 0));
		horizontalStrut_3.setMaximumSize(new Dimension(10, 32767));
		toolBar.add(horizontalStrut_3);

		JButton btnImport = new JButton("Import", new ImageIcon(
				MainFrame.class.getResource("/images/1377466837_plus-24.png")));
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showImportPanel();
			}
		});
		btnImport.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnImport.setPreferredSize(new Dimension(50, 60));
		btnImport.setMaximumSize(new Dimension(50, 60));
		btnImport.setHorizontalTextPosition(SwingConstants.CENTER);
		btnImport.setBorder(null);
		toolBar.add(btnImport);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));

		sidePanel = new JPanel();
		sidePanel.setBackground(Color.WHITE);
		sidePanel.setLayout(sideBarLayout);
		transSideBar = new TransactionSideBar(this);
		JPanel homeSideBar = new HomeSideBar();
		sidePanel.add(homeSideBar, "Home");
		sidePanel.add(transSideBar, "Transactions");

		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);

		mainPanel.setLayout(cardLayout);

		JPanel homePanel = new HomePanel();
		homePanel.setBackground(Color.WHITE);
		transactionsPanel = new JPanel();

		transactionsPanel.setLayout(new BorderLayout());

		try {
			// add transaction table panel
			transactionPanel = new TransactionsPanel(QUERY_TRANS_DETAIL_TKN);
			TransactionFormPanel transactionFormPanel = new TransactionFormPanel();
			transactionFormPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null,
					null));
			transactionsPanel.add(transactionFormPanel, BorderLayout.NORTH);
			// TransactionsPanel.updateTableData();
			transactionsPanel.revalidate();
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (DatabaseTableCreationException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		transactionsPanel.add(transactionPanel, BorderLayout.CENTER);

		JPanel importPanel = new ImportPanel();

		// setting the various cards
		mainPanel.add(homePanel, "Home");
		mainPanel.add(transactionsPanel, "Transactions");
		mainPanel.add(importPanel, "Import");
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidePanel, mainPanel);
		splitPane.setDividerSize(0);
		splitPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(splitPane, "name_11357401020005");
		splitPane.setEnabled(false);
		splitPane.setDividerLocation(160);

		JPanel statusPanel = new JPanel();
		getContentPane().add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		JLabel statusLabel = new JLabel("status");
		statusLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		pack();
	}

	protected void showImportPanel() {
		changeCard("Import");
	}

	protected void showSettingsDialog() {
		SettingsDialog dialog = new SettingsDialog(this, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	public void updateTransactionPanel(String queryString) {
		transactionsPanel.remove(transactionPanel);
		try {
			// add transaction table panel
			transactionPanel = new TransactionsPanel(queryString);
			transactionsPanel.revalidate();
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (DatabaseTableCreationException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Exception Occured : " + e1.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		transactionsPanel.add(transactionPanel, BorderLayout.CENTER);
	}

	public void changeCard(String card) {
		cardLayout.show(mainPanel, card);
		sideBarLayout.show(sidePanel, card);
	}

	public MainFrame getFrame() {
		return this;
	}

	public void setExitToSystemTray() {
		if (SystemTray.isSupported()) {
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					setExtendedState(JFrame.ICONIFIED);
					SystemUtils.exitToSystemTray(getFrame());
				}
			});
		}
	}

	/**
	 * Exit application
	 */
	public void exitApp() {
		if (Prefs.isConfirmationOnExit()) {
			if (JOptionPane.showConfirmDialog(null, "Do you want to exit?", "Exit Application?",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				System.exit(0);
			}
		}
	}

	public void setCloseOperation() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitApp();
				getFrame().setExtendedState(JFrame.NORMAL);
			}
		});
	}
}
