package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class SettingsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1890295441196460732L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public SettingsDialog() {
		setTitle("Settings");
		setBounds(100, 100, 450, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane, BorderLayout.CENTER);
			{
				JPanel generalTab = new JPanel();
				tabbedPane.addTab("General", null, generalTab, null);
			}
			{
				JPanel ebayTab = new JPanel();
				tabbedPane.addTab("eBay Fees", null, ebayTab, null);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
