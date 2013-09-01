package com.axatrikx.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

@SuppressWarnings("restriction")
public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class);

	private static Preferences pref;
	private static final String preferenceNode = "com/axatrikx/trnmaster";

	static {
		pref = Preferences.userRoot().node(preferenceNode);
	}

	public static String changeDateFormat(String inputFormat, String outputFormat, String originalDate)
			throws ParseException {
		DateFormat originalFormat = new SimpleDateFormat(inputFormat);
		DateFormat targetFormat = new SimpleDateFormat(outputFormat);
		Date date = originalFormat.parse(originalDate);
		return targetFormat.format(date);
	}

	public static Preferences getPreference() {
		return pref;
	}

	/**
	 * Evaluates the given expression.
	 * 
	 * @param expression
	 * @return
	 */
	public static String evaluateExpression(String expression) {
		String result = null;
		if (isExpressionValid(expression)) {
			log.info("Evaluation expression " + expression);
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("JavaScript");
			try {
				result = engine.eval(expression).toString();
			} catch (ScriptException e) {
				log.error(e.getMessage(), e);
			}
		}
		return result;
	}

	public static Properties getPropertiesFromFile(String fileName) {
		Properties props = new Properties();
		try {
			props.load(Utils.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			log.error("IO Exception while loading " + fileName, e);
		}
		return props;
	}

	private static boolean isExpressionValid(String expression) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Finds the date from date string and returns it.
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date getDate(String dateString) throws ParseException {
		Parser parser = new Parser();
		List<DateGroup> dates = parser.parse(dateString);
		return dates.get(0).getDates().get(0);
	}
	
	/**
	 * Gets the best match(closest match) from the list of strings.
	 * @param token
	 * @param matches
	 * @return
	 */
	public static String getBestMatch(List<String> keywords, List<String> matches) {
		for(String keyword : keywords) {
			for(String possMatch : matches) {
				if(possMatch.toLowerCase().contains(keyword.toLowerCase())) {
					return possMatch;
				}
			}
		}
		return null;
	}
}
