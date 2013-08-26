package com.axatrikx.utils;

import java.awt.Image;
import java.awt.SystemTray;
import java.net.URL;

import javax.swing.ImageIcon;

import com.axatrikx.ui.main.MainFrame;
import com.axatrikx.ui.panels.SysTrayPopup;

public class SystemUtils {

	public static void exitToSystemTray(MainFrame parent) {
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray is not supported");
			return;
		}
		SysTrayPopup.getSysTrayPopup(parent);
	}

	@SuppressWarnings("static-access")
	public static Image createImage(String path, String description) {
		URL imageURL = SystemUtils.class.getClassLoader().getSystemResource(path);

		if (imageURL == null) {
			System.err.println("Resource not found: " + path);
			return null;
		} else {
			return (new ImageIcon(imageURL, description)).getImage();
		}
	}
}
