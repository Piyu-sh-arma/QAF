package com.TestSuits;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import com.QAF.Base.QAFTest;
import com.QAF.Utils.DataTransformer;
import com.QAF.Utils.Reporter;
import com.QAF.annotations.QAFInput;
import com.SupportUtils.StepStatus;

import bsh.This;

import java.util.HashMap;

public class AppTests extends QAFTest {
	private static final Logger log = Logger.getLogger(This.class);
	
	@QAFInput(key = "TestKey1")
	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataTransformer.class)
	public void f1(HashMap<String, String> data) {
		try {
			Reporter.reportStep("Step1", "Test2 Details", StepStatus.PASS);
			Reporter.reportStep("Step2", "Test2 Details", StepStatus.FAIL);
		} catch (Exception e) {
			Reporter.reportStep("End", "Test Failed due to follwing exception -" + e.getLocalizedMessage(),
					StepStatus.FAIL);
			log.error(e.getStackTrace());

		}
	}
	
	@QAFInput(key = "TestKey2")
	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataTransformer.class)
	public void f2(HashMap<String, String> data) {
		try {
			Reporter.reportStep("Step1", "Test1 Details", StepStatus.PASS);
			Reporter.reportStep("Step2", "Test1 Details", StepStatus.FAIL);
		} catch (Exception e) {
			Reporter.reportStep("End", "Test Failed due to follwing exception -" + e.getLocalizedMessage(),
					StepStatus.FAIL);
			log.error(e.getStackTrace());

		}

	}
}
