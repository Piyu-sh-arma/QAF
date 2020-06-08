package com.testSuits;

import com.qaf.annotations.QAFTest;
import com.qaf.base.QAFBaseTest;
import com.qaf.driver.options.QAFDriverManager;
import com.qaf.pages.HomePage;
import com.qaf.pages.LoginPage;
import com.qaf.utils.DataTransformer;
import com.qaf.utils.Reporter;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.HashMap;

import static org.testng.Assert.assertTrue;

public class AppTests extends QAFBaseTest {
	private static final Logger log = Logger.getLogger(AppTests.class);

	@QAFTest(key = "T_Key_1")
	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataTransformer.class)
	public void f1(HashMap<String, String> data) {
		try {
			QAFDriverManager qafDriverManager = new QAFDriverManager();
			WebDriver driver = qafDriverManager.getDriver();
			HomePage homePage= new HomePage(driver);
			LoginPage loginPage = new LoginPage(driver);
			homePage.startApplication();
			loginPage.logIn("xys","pqr");

		} catch (Exception e) {
			Reporter.reportStep("End", "Test Failed due to following exception -" + e.getLocalizedMessage(), StepStatus.FAIL);
			log.error("Exception Occurred", e);

		}
		if (Reporter.hasTestFailed())
            assertTrue(false, "Test run failed");

	}
}
