package com.app.testSuites;

import com.app.component.CheckBox;
import com.app.component.RadioButton;
import com.app.pages.CheckBoxDemo;
import com.app.pages.RadioBtnDemo;
import com.qaf.annotations.QAFTest;
import com.qaf.base.QAFBaseTest;
import com.qaf.driver.options.QAFDriverManager;
import com.app.pages.HomePage;
import com.app.pages.LoginPage;
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
            HomePage homePage = new HomePage(driver);
            CheckBoxDemo chkBxDemo = new CheckBoxDemo(driver);
            homePage.startApplication();
            homePage.lnkBasicExmpl.click();
            homePage.lnkChkBoxDemo.jsClick();
            chkBxDemo.chkBoxAge.check("AgeCheckBox");
            chkBxDemo.chkBoxAge.check("AgeCheckBox");
            chkBxDemo.chkBoxAge.verifyVisible("AgeCheckBox");
            chkBxDemo.chkBoxAge.verifyEnabled("AgeCheckBox");
            chkBxDemo.chkBoxAge.unCheck("AgeCheckBox");
            chkBxDemo.chkBoxAge.unCheck("AgeCheckBox");

        } catch (Exception e) {
            Reporter.reportStep("End", "Test Failed due to following exception -" + e.getLocalizedMessage(), StepStatus.FAIL);
            log.error("Exception Occurred", e);

        }
        if (Reporter.hasTestFailed())
            assertTrue(false, "Test run failed");

    }

    @QAFTest(key = "T_Key_2")
    @Test()
    public void f2() {
        try {
            QAFDriverManager qafDriverManager = new QAFDriverManager();
            WebDriver driver = qafDriverManager.getDriver();
            HomePage homePage = new HomePage(driver);
            RadioBtnDemo rdBtnPage = new RadioBtnDemo(driver);
            homePage.startApplication();
            homePage.lnkBasicExmpl.click();
            homePage.lnkRadioBtnDemo.jsClick();
            rdBtnPage.rdBtnGenderMale.select("Male");
            rdBtnPage.rdBtnGenderMale.verifySelected("Option-Male");
            rdBtnPage.rdBtnGenderFemale.verifyDeselected("Option-Female");
            rdBtnPage.rdBtnGenderFemale.select("Female");
            rdBtnPage.rdBtnGenderFemale.verifySelected("Option-Female");

        } catch (Exception e) {
            Reporter.reportStep("End", "Test Failed due to following exception -" + e.getLocalizedMessage(), StepStatus.FAIL);
            log.error("Exception Occurred", e);

        }
        if (Reporter.hasTestFailed())
            assertTrue(false, "Test run failed");

    }
}
