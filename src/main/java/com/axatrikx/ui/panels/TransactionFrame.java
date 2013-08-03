package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.Insets;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSplitPane;
import java.awt.CardLayout;

public class TransactionFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					TransactionFrame frame = new TransactionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TransactionFrame() {
		setTitle("eBay Master");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setName("menuBar");
		menuBar.setBounds(new Rectangle(5, 0, 0, 0));
		menuBar.setAlignmentX(5.0f);
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNewTransaction = new JMenuItem("New Transaction");
		mnFile.add(mntmNewTransaction);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmQuit = new JMenuItem("Quit");
		mnFile.add(mntmQuit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmEditTransaction = new JMenuItem("Edit Transaction");
		mnEdit.add(mntmEditTransaction);
		
		JMenuItem mntmFind = new JMenuItem("Find");
		mnEdit.add(mntmFind);
		
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
		
		JMenuItem mntmSettings = new JMenuItem("Settings");
		mnTools.add(mntmSettings);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpTopics = new JMenuItem("Help Topics");
		mnHelp.add(mntmHelpTopics);
		
		JMenuItem mntmOnlineHelp = new JMenuItem("Online Help");
		mnHelp.add(mntmOnlineHelp);
		
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
		
		JButton btnHome = new JButton("Home", new ImageIcon(TransactionFrame.class.getResource("/images/menu-24.png")));
		btnHome.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
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
		
		JButton btnTransactions = new JButton("Transactions",new ImageIcon(TransactionFrame.class.getResource("/images/transaction-24.png")));
		btnTransactions.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
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
		
		JButton btnReport = new JButton("Report", new ImageIcon(TransactionFrame.class.getResource("/images/report-24.png")));
		btnReport.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
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
		
		JButton btnSettings = new JButton("Settings", new ImageIcon(TransactionFrame.class.getResource("/images/settings-24.png")));
		btnSettings.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
		btnSettings.setMaximumSize(new Dimension(50, 60));
		btnSettings.setPreferredSize(new Dimension(50, 60));
		btnSettings.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSettings.setHorizontalTextPosition(SwingConstants.CENTER);
		toolBar.add(btnSettings);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
	}

}
