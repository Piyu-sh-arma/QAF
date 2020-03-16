package com.QAF.Base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.QAF.Utils.Reporter;

public class QAFTest {
	private static final Logger log = Logger.getLogger(QAFTest.class);

	@SuppressWarnings("unchecked")
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Object[] tstParams) {

		HashMap<String, String> data = (HashMap<String, String>) tstParams[0];
		Thread.currentThread().setName(data.get("TestKey"));
		log.info("Executing Before Method for test - " + data.get("TestKey"));
		Reporter.initialzeReport();

	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(Method m) {
		log.info("Executing After Method for test - " + Thread.currentThread().getName());
		Reporter.closeReport();
	}
	

}
