package com.testSuits;

import com.qaf.base.QAFBaseTest;
import com.qaf.conditions.QAFExpectedConditions;
import com.qaf.driver.options.QAFDriverManager;
import com.qaf.pages.LoginPage;
import com.qaf.utils.DataTransformer;
import com.qaf.utils.Reporter;
import com.qaf.annotations.QAFTest;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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
			driver.get("https://ui.freecrm.com/");
			LoginPage loginPage = new LoginPage(driver);
			loginPage.logIn("xys","pqr");




		} catch (Exception e) {
			Reporter.reportStep("End", "Test Failed due to following exception -" + e.getLocalizedMessage(), StepStatus.FAIL);
			log.error("Exception Occurred", e);

		}
		if (Reporter.hasTestFailed())
            assertTrue(false, "Test run failed");

	}

	@QAFTest(key = "T_Key_2")
	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataTransformer.class)
	public void f2(HashMap<String, String> data) {
		try {
			QAFDriverManager qafDriverManager = new QAFDriverManager();
			WebDriver driver = qafDriverManager.getDriver();
			driver.get("http://thedemosite.co.uk/addauser.php");
			System.out.println(driver.getTitle());
			Reporter.reportStep("Step1", driver.getTitle(), StepStatus.FAIL);
			WebDriverWait wait = new WebDriverWait(driver,20,500);
			wait.until(QAFExpectedConditions.waitForPageLoadWithJS());
		} catch (Exception e) {
			Reporter.reportStep("End", "Test Failed due to following exception -" + e.getLocalizedMessage(), StepStatus.FAIL);
			log.error("Exception Occurred", e);
            assertTrue(false, "Test run failed");

		}
		if (Reporter.hasTestFailed())
            assertTrue(false, "Test run failed");

	}


}
