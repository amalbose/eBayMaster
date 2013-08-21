package com.axatrikx.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static String changeDateFormat(String inputFormat, String outputFormat, String originalDate) throws ParseException {
		DateFormat originalFormat = new SimpleDateFormat(inputFormat);
		DateFormat targetFormat = new SimpleDateFormat(outputFormat);
		Date date = originalFormat.parse(originalDate);
		return targetFormat.format(date);  
	}
	
}
