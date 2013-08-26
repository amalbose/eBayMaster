package com.axatrikx.ui.panels;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.axatrikx.ui.main.MainFrame;
import com.axatrikx.utils.ConfigValues;
import com.axatrikx.utils.SystemUtils;

public class SysTrayPopup extends JPopupMenu {

	private static SysTrayPopup sysPopup = null;

	private static final long serialVersionUID = -5905114109873547660L;

	MainFrame parentFrame;

	private SysTrayPopup() {
	}

	public static SysTrayPopup getSysTrayPopup(MainFrame parentFrame) {
		if (sysPopup == null) {
			sysPopup = new SysTrayPopup(parentFrame);
			return sysPopup;
		} else {
			return sysPopup;
		}
	}

	private SysTrayPopup(MainFrame parentFrame) {
		this.parentFrame = parentFrame;
		initialize();
	}

	private void initialize() {
		final TrayIcon trayIcon = new TrayIcon(SystemUtils.createImage(ConfigValues.APPLICATION_ICON.toString(),
				"Tray icon"));
		// Create a pop-up menu components
		JMenuItem aboutItem = new JMenuItem("About");
		JMenuItem showWindow = new JMenuItem("Show window");
		showWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * parentFrame.setExtendedState(JFrame.NORMAL); parentFrame.setState(JFrame.NORMAL);
				 * parentFrame.setVisible(true); parentFrame.toFront(); parentFrame.repaint();
				 */
				int state = parentFrame.getExtendedState();
				state = state & ~Frame.ICONIFIED;
				parentFrame.setExtendedState(state);
				parentFrame.setVisible(true);
				parentFrame.toFront();
			}
		});
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.exitApp();
			}
		});
		// Add components to pop-up menu
		add(aboutItem);
		addSeparator();
		add(showWindow);
		add(exitItem);

		final SystemTray tray = SystemTray.getSystemTray();
		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					JPopupMenu jpopup = getPopUp();
					jpopup.setLocation(e.getX(), e.getY());
					jpopup.setInvoker(jpopup);
					jpopup.setVisible(true);
				}
			}
		});
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
		}
	}

	private JPopupMenu getPopUp() {
		return this;
	}
}
