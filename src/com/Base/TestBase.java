package com.Base;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;

import com.QAF.Utils.FWDataManager;
import com.QAF.Utils.Reporter;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class TestBase {
	private static final Logger log = Logger.getLogger(TestBase.class);
  
  @BeforeMethod(alwaysRun = true)
  public void beforeMethod(Method m) {
	  log.info("Executing Before Method for test - "+m.getName());
	  Reporter.initialzeReport(m);
	  
  }

  @AfterMethod(alwaysRun = true)
  public void afterMethod(Method m) {
	  log.info("Executing After Method for test - "+m.getName());
	  Reporter.closeReport(m);
  }

  @BeforeClass
  public void beforeClass() {
  }

  @AfterClass
  public void afterClass() {
  }

  @BeforeTest
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

  @BeforeSuite
  public void beforeSuite() {
  }

  @AfterSuite
  public void afterSuite() {
  }

}
