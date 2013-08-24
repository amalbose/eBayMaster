package com.axatrikx.utils;

import java.util.prefs.Preferences;

public class Prefs {

	private static final String IS_SETTINGS_SET = "isSettingsSet";
	private static final String ALWAYS_ON_TOP = "alwaysOnTop";
	private static final String START_MAXIMIZED = "startMaximized";
	private static final String EXIT_TO_SYS_TRAY = "exitToSysTray";
	private static final String START_PANE = "startPane";
	private static final String HOME_GRAPH = "homeGraph";
	private static final String SEACH_WHEN_TYPING = "searchWhenTyping";
	private static final String CONFIRMATION_ON_EXIT = "confirmOnExit";
	private static final String CONFIRMATION_BEFORE_DELETE = "confirmBeforeDelete";
	private static final String CONFIRMATION_AFTER_DELETE = "confirmAfterDelete";
	private static final String LOCALIZATION = "localization";
	private static final String WINDOW_WIDTH = "windowWidth";
	private static final String WINDOW_HEIGHT = "windowHeight";

	private static Preferences prefs = Utils.getPreference();

	/*
	 * These are default values obtained from settings file.
	 */

	private static final boolean alwaysOnTop = false;
	private static final boolean startMaximized = false;
	private static final boolean exitToTray = false;
	private static final boolean searchWhenTyping = false;
	private static final boolean confirmationOnExit = false;
	private static final boolean confirmationBeforeDelete = true;
	private static final boolean confirmationAfterDelete = true;
	private static final String startPaneVal = "Home";
	private static final String homeGraphVal = "Trends";
	private static final String localizationVal = "Default";
	private static int windowWidthVal = 1000;
	private static int windowHeightVal = 600;

	static {
		if (!prefs.getBoolean(IS_SETTINGS_SET, false)) {
			setDefaultPreference();
			prefs.putBoolean(IS_SETTINGS_SET, true);
		}
	}

	public static boolean isAlwaysOnTop() {
		return prefs.getBoolean(ALWAYS_ON_TOP, alwaysOnTop);
	}

	public static boolean isStartMaximized() {
		return prefs.getBoolean(START_MAXIMIZED, startMaximized);
	}

	public static boolean isExitToTray() {
		return prefs.getBoolean(EXIT_TO_SYS_TRAY, exitToTray);
	}

	public static boolean isSearchWhenTyping() {
		return prefs.getBoolean(SEACH_WHEN_TYPING, searchWhenTyping);
	}

	public static boolean isConfirmationOnExit() {
		return prefs.getBoolean(CONFIRMATION_ON_EXIT, confirmationOnExit);
	}

	public static boolean istConfirmationBeforeDelete() {
		return prefs.getBoolean(CONFIRMATION_BEFORE_DELETE, confirmationBeforeDelete);
	}

	public static boolean isConfirmationAfterDelete() {
		return prefs.getBoolean(CONFIRMATION_AFTER_DELETE, confirmationAfterDelete);
	}

	public static String getStartPane() {
		return prefs.get(START_PANE, startPaneVal);
	}

	public static String getHomeGraph() {
		return prefs.get(HOME_GRAPH, homeGraphVal);
	}

	public static String getLocalization() {
		return prefs.get(LOCALIZATION, localizationVal);
	}

	public static int getWindowHeight() {
		return prefs.getInt(WINDOW_HEIGHT, windowHeightVal);
	}

	public static int getWindowWidth() {
		return prefs.getInt(WINDOW_WIDTH, windowWidthVal);
	}

	/**
	 * Sets the default preferences.
	 */
	private static void setDefaultPreference() {
		prefs.putBoolean(ALWAYS_ON_TOP, alwaysOnTop);
		prefs.putBoolean(START_MAXIMIZED, startMaximized);
		prefs.putBoolean(EXIT_TO_SYS_TRAY, exitToTray);
		prefs.putBoolean(SEACH_WHEN_TYPING, searchWhenTyping);
		prefs.putBoolean(CONFIRMATION_ON_EXIT, confirmationOnExit);
		prefs.putBoolean(CONFIRMATION_BEFORE_DELETE, confirmationBeforeDelete);
		prefs.putBoolean(CONFIRMATION_AFTER_DELETE, confirmationAfterDelete);
		prefs.put(START_PANE, startPaneVal);
		prefs.put(HOME_GRAPH, homeGraphVal);
		prefs.put(LOCALIZATION, localizationVal);
		prefs.putInt(WINDOW_HEIGHT, windowHeightVal);
		prefs.putInt(WINDOW_WIDTH, windowWidthVal);
	}

	public void setIsAlwaysOnTop(boolean bool) {
		prefs.putBoolean(ALWAYS_ON_TOP, bool);
	}

	public void setStartMaximized(boolean bool) {
		prefs.putBoolean(START_MAXIMIZED, bool);
	}

	public void setExitToTray(boolean bool) {
		prefs.putBoolean(EXIT_TO_SYS_TRAY, bool);
	}

	public void setSearchWhenTyping(boolean bool) {
		prefs.putBoolean(SEACH_WHEN_TYPING, bool);
	}

	public void setConfirmationOnExit(boolean bool) {
		prefs.putBoolean(CONFIRMATION_ON_EXIT, bool);
	}

	public void setConfirmationBeforeDelete(boolean bool) {
		prefs.putBoolean(CONFIRMATION_BEFORE_DELETE, bool);
	}

	public void setConfirmationAfterDelete(boolean bool) {
		prefs.putBoolean(CONFIRMATION_AFTER_DELETE, bool);
	}

	public void setStartPaneVal(String value) {
		prefs.put(START_PANE, value);
	}

	public void setHomeGraphVal(String value) {
		prefs.put(HOME_GRAPH, value);
	}

	public void setLocalizationVal(String value) {
		prefs.put(LOCALIZATION, value);
	}

	public void setWindowHeightVal(int value) {
		prefs.putInt(WINDOW_HEIGHT, value);
	}

	public void setWindowWidthVal(int value) {
		prefs.putInt(WINDOW_WIDTH, value);
	}
}
