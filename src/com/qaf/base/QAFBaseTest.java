package com.qaf.base;

import com.qaf.driver.options.QAFDriverManager;
import com.qaf.utils.Reporter;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class QAFBaseTest {
	private static final Logger log = Logger.getLogger(QAFBaseTest.class);

	@SuppressWarnings("unchecked")
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Object[] tstParams) {
		HashMap<String, String> data = (HashMap<String, String>) tstParams[0];
		Thread.currentThread().setName(Thread.currentThread().getId() + "~_" + data.get("TestKey"));
		log.info("Executing Before Method for test - " + data.get("TestKey"));
		Reporter.initialzeReport();

	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(Method m) {
		log.info("Executing After Method for test - " + Thread.currentThread().getName().split("~_")[1]);
		Reporter.closeReport();
		QAFDriverManager.quitDriver();
		
	}

}
