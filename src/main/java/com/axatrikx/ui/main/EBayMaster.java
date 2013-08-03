package com.axatrikx.ui.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

public class EBayMaster {

	private JFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EBayMaster window = new EBayMaster();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		mainFrame = new JFrame();
		mainFrame.getContentPane().setSize(new Dimension(800, 500));
		mainFrame.getContentPane().setPreferredSize(new Dimension(800, 500));
		mainFrame.setSize(new Dimension(724, 440));
		mainFrame.setPreferredSize(new Dimension(800, 500));
		mainFrame.setName("mainFrame");
		mainFrame.setTitle("eBay Master");
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setBounds(100, 100, 450, 300);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
