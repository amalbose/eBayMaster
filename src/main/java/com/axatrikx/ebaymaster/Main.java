package com.axatrikx.ebaymaster;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.axatrikx.ui.main.EBayMaster;

public class Main {

	static Logger log = Logger.getLogger(Main.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		if (!ApplicationInstanceManager.registerInstance()) {
			// instance already running.
			log.error("Another instance of this application is already running.  Exiting.");
			System.out.println("Another instance of this application is already running.  Exiting.");
			System.exit(0);
		}
		ApplicationInstanceManager.setApplicationInstanceListener(new ApplicationInstanceListener() {
			public void newInstanceCreated() {
				log.warn("New instance detected...");
				System.out.println("New instance detected...");
				JOptionPane.showMessageDialog(null, "New instance detected and closed.", "Multiple Instances",
						JOptionPane.WARNING_MESSAGE);
			}
		});

		invokeApplication();
	}

	/**
	 * Invokes the application
	 */
	private static void invokeApplication() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new EBayMaster();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
