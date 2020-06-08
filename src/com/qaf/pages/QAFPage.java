package com.qaf.pages;

import com.qaf.utils.QAFConfig;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class QAFPage {
	
	private static final Logger log = Logger.getLogger(QAFPage.class);
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
		log.info("Page Object =>"+Thread.currentThread().getName());
		
	}

	public void startApplication(){
		driver.get(getURL());
	}

	public String getURL(){
		if(null == System.getProperty("ApplicationURL"))
			return QAFConfig.getProperty("Application.url").trim();
		else
			return System.getProperty("ApplicationURL");

	}

}
