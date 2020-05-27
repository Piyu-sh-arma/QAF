package com.QAF.Base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.QAF.Driver.Options.QAFDriverManager;
import com.QAF.Utils.Reporter;

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
