package com.axatrikx.ui.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.axatrikx.utils.CommonSettings;

public class EBayMaster {

	private static final String DEFAULT_WINDOW_WIDTH_TKN = "DEFAULT_WINDOW_WIDTH";
	private static final String DEFAULT_WINDOW_HEIGHT_TKN = "DEFAULT_WINDOW_HEIGHT";

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
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainFrame = new MainFrame();
		Dimension windowDimension = new Dimension(Integer.parseInt(CommonSettings
				.getSettingValue(DEFAULT_WINDOW_WIDTH_TKN)), Integer.parseInt(CommonSettings
				.getSettingValue(DEFAULT_WINDOW_HEIGHT_TKN)));
		mainFrame.setSize(windowDimension);
		mainFrame.setPreferredSize(windowDimension);

		mainFrame.setName("mainFrame");
		mainFrame.setTitle("eBay Master");
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

}
