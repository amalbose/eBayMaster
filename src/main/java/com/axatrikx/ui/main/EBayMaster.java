package com.axatrikx.ui.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.testng.log4testng.Logger;

import com.axatrikx.utils.Prefs;

public class EBayMaster {

	private static final Logger log = Logger.getLogger(EBayMaster.class);
	private JFrame mainFrame;

	/**
	 * Create the application.
	 */
	public EBayMaster() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e2) {
			log.error("Exception while setting system look and feel", e2);
		} catch (InstantiationException e2) {
			log.error("Exception while setting system look and feel", e2);
		} catch (IllegalAccessException e2) {
			log.error("Exception while setting system look and feel", e2);
		} catch (ClassNotFoundException e2) {
			log.error("Exception while setting system look and feel", e2);
		}
		mainFrame = new MainFrame();
		Dimension windowDimension = new Dimension(Prefs.getWindowWidth(), Prefs.getWindowHeight());
		mainFrame.setSize(windowDimension);
		mainFrame.setPreferredSize(windowDimension);

		mainFrame.setName("mainFrame");
		mainFrame.setTitle("eBay Master");
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

}
