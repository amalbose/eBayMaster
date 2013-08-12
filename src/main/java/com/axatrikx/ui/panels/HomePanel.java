package com.axatrikx.ui.panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class HomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public HomePanel() {
		
		JLabel lblHomePanel = new JLabel("Home Panel");
		lblHomePanel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblHomePanel);

	}

}
