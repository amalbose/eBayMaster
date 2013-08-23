package com.axatrikx.utils;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UtilsTest extends TestNG {

	@Test(dataProvider = "expressionData")
	public void expressionEvaluatorTest(String actual, String expected) {
		Assert.assertEquals(Utils.evaluateExpression(actual).toString(), expected);
	}

	@DataProvider(name = "expressionData")
	private Object[][] expressionDataProvider() {
		return new Object[][] { { "5+2-3", "4.0" }, { "0.1*3.2-3", "-2.6799999999999997" } };
	}

	//@Test(dataProvider = "dateData")
	public void dateFormatterTest(String fromFormat, String toFormat, String date, String expected) throws ParseException {
		Assert.assertEquals(Utils.changeDateFormat(fromFormat, toFormat, date), expected);
	}
	
	@DataProvider(name = "dateData")
	private Object[][] dateDataProvider() {
		return new Object[][] { { "", "" }, { "", "" } };
	}
}
