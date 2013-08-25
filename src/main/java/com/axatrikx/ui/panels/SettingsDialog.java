package com.axatrikx.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import com.axatrikx.beans.ApplicationPages;
import com.axatrikx.beans.GraphTypes;
import com.axatrikx.utils.Prefs;

public class SettingsDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1890295441196460732L;
	private final JPanel contentPanel = new JPanel();
	private JCheckBox chckbxStartMinimized;
	private JCheckBox chckbxAlwaysOnTop;
	private JCheckBox chckbxExitToSystem;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox_2;
	private JCheckBox chckbxSearchWhenI;
	private JCheckBox chckbxShowConfirmationOn;
	private JCheckBox chckbxShowConfirmationBefore;
	private JCheckBox chckbxShowConfirmationMessage;
	private JComboBox<String> comboBox;
	private JSpinner widthSpinner;
	private JSpinner heightSpinner;
	private Frame owner;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SettingsDialog(Frame owner, boolean modal) {
		super(owner,modal);
		this.owner = owner;
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
					comboBox_1 = new JComboBox<String>();
					comboBox_1.setModel(new DefaultComboBoxModel(ApplicationPages.values()));
					comboBox_1.setSelectedIndex(ApplicationPages.valueOf(Prefs.getStartPane()).getIndex());
					panel.add(comboBox_1, "cell 1 0,alignx left");
				}
				{
					chckbxStartMinimized = new JCheckBox("Start Maximized");
					chckbxStartMinimized.setToolTipText("Start the application in Maximized mode");
					chckbxStartMinimized.setSelected(Prefs.isStartMaximized());
					panel.add(chckbxStartMinimized, "cell 0 1");
				}
				{
					chckbxAlwaysOnTop = new JCheckBox("Always on Top");
					chckbxAlwaysOnTop.setToolTipText("Always keep the application window on top of others");
					chckbxAlwaysOnTop.setSelected(Prefs.isAlwaysOnTop());
					panel.add(chckbxAlwaysOnTop, "flowy,cell 0 2");
				}
				{
					chckbxExitToSystem = new JCheckBox("Exit to system tray");
					chckbxExitToSystem.setToolTipText("Keep application in System Tray when exited");
					chckbxExitToSystem.setSelected(Prefs.isExitToTray());
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
						chckbxSearchWhenI = new JCheckBox("Search when I start typing");
						chckbxSearchWhenI.setToolTipText("Automatically start search when user starts to type");
						chckbxSearchWhenI.setSelected(Prefs.isSearchWhenTyping());
						panel_1.add(chckbxSearchWhenI, "cell 0 1");
					}
					{
						chckbxShowConfirmationOn = new JCheckBox("Show confirmation on Exit");
						chckbxShowConfirmationOn.setToolTipText("Show confirmation on Exit");
						chckbxShowConfirmationOn.setSelected(Prefs.isConfirmationOnExit());
						panel_1.add(chckbxShowConfirmationOn, "cell 0 2");
					}
					{
						chckbxShowConfirmationBefore = new JCheckBox("Show confirmation before deletion");
						chckbxShowConfirmationBefore.setToolTipText("Asks for confirmation before deleting record");
						chckbxShowConfirmationBefore.setSelected(Prefs.isConfirmationBeforeDelete());
						panel_1.add(chckbxShowConfirmationBefore, "flowy,cell 0 3");
					}
					{
						Component horizontalStrut = Box.createHorizontalStrut(20);
						panel_1.add(horizontalStrut, "cell 0 0");
					}
					{
						comboBox_2 = new JComboBox();
						comboBox_2.setModel(new DefaultComboBoxModel(GraphTypes.values()));
						panel_1.add(comboBox_2, "cell 0 0,alignx right");
					}
					{
						chckbxShowConfirmationMessage = new JCheckBox("Show confirmation message after record deletion");
						chckbxShowConfirmationMessage.setToolTipText("Show delete confirmation message after deletion");
						chckbxShowConfirmationMessage.setSelected(Prefs.isConfirmationAfterDelete());
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
						comboBox = new JComboBox();
						comboBox.setModel(new DefaultComboBoxModel(new String[] {"Default", "English"}));
						comboBox.setSelectedItem(Prefs.getLocalization());
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
						widthSpinner = new JSpinner();
						widthSpinner.setToolTipText("Set window width");
						widthSpinner.setPreferredSize(new Dimension(50, 20));
						widthSpinner.setMinimumSize(new Dimension(35, 20));
						widthSpinner.setModel(new SpinnerNumberModel(new Integer(1000), new Integer(600), null, new Integer(10)));
						widthSpinner.setValue(Prefs.getWindowWidth());
						panel_1.add(widthSpinner, "cell 1 1,aligny center");
					}
					{
						JLabel lblWindowHeight = new JLabel("Window Height");
						panel_1.add(lblWindowHeight, "cell 5 1,alignx left,aligny center");
					}
					{
						heightSpinner = new JSpinner();
						heightSpinner.setToolTipText("Set window height");
						heightSpinner.setModel(new SpinnerNumberModel(new Integer(600), new Integer(400), null, new Integer(10)));
						heightSpinner.setValue(Prefs.getWindowHeight());
						panel_1.add(heightSpinner, "cell 7 1,aligny center");
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
				btnResultTo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Prefs.setDefaultPreference();
					}
				});
				buttonPane.add(btnResultTo, "cell 1 0,alignx right,aligny top");
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(150);
				buttonPane.add(horizontalStrut, "cell 3 0,grow");
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						savePreferences();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton, "cell 4 0,growx,aligny top");
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						closeDialog();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton, "cell 5 0,alignx right,aligny top");
			}
		}
	}
	@SuppressWarnings("unchecked")
	protected void savePreferences() {
		Prefs.setIsAlwaysOnTop(getChckbxAlwaysOnTop().isSelected());
		Prefs.setStartMaximized(getChckbxStartMinimized().isSelected());
		Prefs.setExitToTray(getChckbxExitToSystem().isSelected());
		Prefs.setHomeGraphVal(((Enum<ApplicationPages>) getHomeGraphComboBox().getSelectedItem()).name());
		Prefs.setStartPaneVal(((Enum<ApplicationPages>) getHomePaneComboBox().getSelectedItem()).name());
		Prefs.setSearchWhenTyping(getChckbxSearchWhenI().isSelected());
		Prefs.setConfirmationOnExit(getChckbxShowConfirmationOn().isSelected());
		Prefs.setConfirmationAfterDelete(getChckbxShowConfirmationMessage().isSelected());
		Prefs.setConfirmationBeforeDelete(getChckbxShowConfirmationBefore().isSelected());
		Prefs.setLocalizationVal(getLocalizationComboBox().getSelectedItem().toString());
		Prefs.setWindowHeightVal(Integer.parseInt(getHeightSpinner().getValue().toString()));
		Prefs.setWindowWidthVal(Integer.parseInt(getWidthSpinner().getValue().toString()));
		closeDialog();
		
		applySettingChanges();
	}

	protected JCheckBox getChckbxStartMinimized() {
		return chckbxStartMinimized;
	}
	protected JCheckBox getChckbxAlwaysOnTop() {
		return chckbxAlwaysOnTop;
	}
	protected JCheckBox getChckbxExitToSystem() {
		return chckbxExitToSystem;
	}
	protected JComboBox<String> getHomePaneComboBox() {
		return comboBox_1;
	}
	protected JComboBox<String> getHomeGraphComboBox() {
		return comboBox_2;
	}
	protected JCheckBox getChckbxSearchWhenI() {
		return chckbxSearchWhenI;
	}
	protected JCheckBox getChckbxShowConfirmationOn() {
		return chckbxShowConfirmationOn;
	}
	protected JCheckBox getChckbxShowConfirmationBefore() {
		return chckbxShowConfirmationBefore;
	}
	protected JCheckBox getChckbxShowConfirmationMessage() {
		return chckbxShowConfirmationMessage;
	}
	protected JComboBox<String> getLocalizationComboBox() {
		return comboBox;
	}
	protected JSpinner getWidthSpinner() {
		return widthSpinner;
	}
	protected JSpinner getHeightSpinner() {
		return heightSpinner;
	}
	private void closeDialog(){
		this.dispose();
	}
	
	/**
	 * Applies the setting changes
	 */
	private void applySettingChanges() {
		//Always on top
		owner.setAlwaysOnTop(Prefs.isAlwaysOnTop());
	}
}
