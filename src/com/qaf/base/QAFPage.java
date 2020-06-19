package com.qaf.base;

import com.qaf.utils.QAFConfig;
import com.qaf.utils.Reporter;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class QAFPage {

    private static final Logger logger = Logger.getLogger(QAFPage.class);
    public WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public QAFPage(WebDriver driver) {
        this.driver = driver;
    }

    public void display() {
        logger.info("Page Object =>" + Thread.currentThread().getName());

    }

    public void startApplication() {
        String url=getURL();
        driver.get(url);
        Reporter.reportStep("Application URL", url, StepStatus.PASS);
    }

    public String getURL() {
        String url;
        if (null == System.getProperty("ApplicationURL"))
            url = QAFConfig.getProperty("Application.url").trim();
        else
            url = System.getProperty("ApplicationURL");
        logger.info("Application URL - " + url);
        return url;

    }

}
