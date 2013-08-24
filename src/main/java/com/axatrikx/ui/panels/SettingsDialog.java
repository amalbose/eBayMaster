package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import com.axatrikx.beans.ApplicationPages;
import com.axatrikx.beans.GraphTypes;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;

public class SettingsDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890295441196460732L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SettingsDialog(Frame owner, boolean modal) {
		super(owner,modal);
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
				generalTab.setLayout(new MigLayout("", "[404.00px,grow]", "[94.00px,baseline][][grow]"));
				
				JPanel panel = new JPanel();
				panel.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
				generalTab.add(panel, "cell 0 0,growx,aligny top");
				panel.setLayout(new MigLayout("", "[151px][197px]", "[30.00,fill][][][]"));
				
				JLabel lblWhenEbaymasterStarts = new JLabel("When eBayMaster starts, show");
				lblWhenEbaymasterStarts.setToolTipText("Panel to be displayed on application start");
				panel.add(lblWhenEbaymasterStarts, "cell 0 0,alignx right,growy");
				{
					JComboBox<String> comboBox = new JComboBox<String>();
					comboBox.setModel(new DefaultComboBoxModel(ApplicationPages.values()));
					panel.add(comboBox, "cell 1 0,alignx left");
				}
				{
					JCheckBox chckbxStartMinimized = new JCheckBox("Start Maximized");
					chckbxStartMinimized.setToolTipText("Start the application in Maximized mode");
					panel.add(chckbxStartMinimized, "cell 0 1");
				}
				{
					JCheckBox chckbxAlwaysOnTop = new JCheckBox("Always on Top");
					chckbxAlwaysOnTop.setToolTipText("Always keep the application window on top of others");
					panel.add(chckbxAlwaysOnTop, "flowy,cell 0 2");
				}
				{
					JCheckBox chckbxExitToSystem = new JCheckBox("Exit to system tray");
					chckbxExitToSystem.setToolTipText("Keep application in System Tray when exited");
					panel.add(chckbxExitToSystem, "cell 0 3");
				}
				{
					JPanel panel_1 = new JPanel();
					panel_1.setBorder(new LineBorder(new Color(192, 192, 192), 1, true));
					generalTab.add(panel_1, "cell 0 1,grow");
					panel_1.setLayout(new MigLayout("", "[128px][grow]", "[14.00,fill][14px][][][][][]"));
					{
						JLabel lblShowGraphOn = new JLabel("Show graph on Home Page");
						lblShowGraphOn.setToolTipText("The Graph type to be shown on Home Page");
						panel_1.add(lblShowGraphOn, "flowx,cell 0 0,alignx left");
					}
					{
						JCheckBox chckbxSearchWhenI = new JCheckBox("Search when I start typing");
						chckbxSearchWhenI.setToolTipText("Automatically start search when user starts to type");
						panel_1.add(chckbxSearchWhenI, "cell 0 1");
					}
					{
						JCheckBox chckbxShowConfirmationOn = new JCheckBox("Show confirmation on Exit");
						chckbxShowConfirmationOn.setToolTipText("Show confirmation on Exit");
						panel_1.add(chckbxShowConfirmationOn, "cell 0 2");
					}
					{
						JCheckBox chckbxShowConfirmationBefore = new JCheckBox("Show confirmation before deletion");
						chckbxShowConfirmationBefore.setToolTipText("Asks for confirmation before deleting record");
						chckbxShowConfirmationBefore.setSelected(true);
						panel_1.add(chckbxShowConfirmationBefore, "flowy,cell 0 3");
					}
					{
						Component horizontalStrut = Box.createHorizontalStrut(20);
						panel_1.add(horizontalStrut, "cell 0 0");
					}
					{
						JComboBox comboBox = new JComboBox();
						comboBox.setModel(new DefaultComboBoxModel(GraphTypes.values()));
						panel_1.add(comboBox, "cell 0 0,alignx right");
					}
					{
						JCheckBox chckbxShowConfirmationMessage = new JCheckBox("Show confirmation message after record deletion");
						chckbxShowConfirmationMessage.setToolTipText("Show delete confirmation message after deletion");
						chckbxShowConfirmationMessage.setSelected(true);
						panel_1.add(chckbxShowConfirmationMessage, "cell 0 4");
					}
					{
						JLabel lblLocalization = new JLabel("Localization");
						panel_1.add(lblLocalization, "flowx,cell 0 6,alignx left,growy");
					}
					{
						Component horizontalStrut = Box.createHorizontalStrut(20);
						panel_1.add(horizontalStrut, "cell 0 6,growx");
					}
					{
						JComboBox comboBox = new JComboBox();
						comboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "English"}));
						panel_1.add(comboBox, "cell 0 6,growy");
					}
				}
				{
					JPanel panel_1 = new JPanel();
					panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
					generalTab.add(panel_1, "cell 0 2,grow");
					panel_1.setLayout(new MigLayout("", "[91.00][][][][][][][]", "[][]"));
					{
						JLabel lblSetDefaultWindow = new JLabel("Default window size");
						panel_1.add(lblSetDefaultWindow, "cell 0 0 2 1");
					}
					{
						JLabel lblWindowWidth = new JLabel("Window Width");
						panel_1.add(lblWindowWidth, "flowx,cell 0 1,alignx left,aligny center");
					}
					{
						JSpinner spinner = new JSpinner();
						spinner.setPreferredSize(new Dimension(50, 20));
						spinner.setMinimumSize(new Dimension(35, 20));
						spinner.setModel(new SpinnerNumberModel(new Integer(1000), new Integer(600), null, new Integer(10)));
						panel_1.add(spinner, "cell 1 1,aligny center");
					}
					{
						JLabel lblWindowHeight = new JLabel("Window Height");
						panel_1.add(lblWindowHeight, "cell 5 1,alignx left,aligny center");
					}
					{
						JSpinner spinner = new JSpinner();
						spinner.setModel(new SpinnerNumberModel(new Integer(600), new Integer(400), null, new Integer(10)));
						panel_1.add(spinner, "cell 7 1,aligny center");
					}
				}
			}
			{
				JPanel ebayTab = new JPanel();
				tabbedPane.addTab("eBay Fees", null, ebayTab, null);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new MigLayout("", "[17.00px][][][100px][110.00px][128.00px]", "[23px]"));
			{
				JButton btnResultTo = new JButton("Reset to Default");
				buttonPane.add(btnResultTo, "cell 1 0,alignx right,aligny top");
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(150);
				buttonPane.add(horizontalStrut, "cell 3 0,grow");
			}
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 4 0,alignx right,aligny top");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 5 0,alignx right,aligny top");
			}
		}
	}
}
