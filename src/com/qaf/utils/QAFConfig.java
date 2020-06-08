package com.qaf.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QAFConfig {
	private static final Logger log = Logger.getLogger(QAFConfig.class);
	private static final Properties config;
	public static int implicitWait=0;
	public static int tryDuration=5;
	public static long pollingInterval=500,pageLoadTimeOut=10;
	static {
		config = new Properties();
		try {
			log.info("<---Initiating Properties--->");
			config.load(new FileInputStream("./resources/configFiles/QAFConfig.properties"));
			log.info("Properties Info - " + config);
			implicitWait = Integer.parseInt(config.getProperty("WebDriver.ImplicitWait"));
			tryDuration = Integer.parseInt(config.getProperty("WebElement.WaitFor"));
			pollingInterval = Long.parseLong(config.getProperty("WebElement.PoolEvery"));
			pageLoadTimeOut = Long.parseLong(config.getProperty("WebDriver.PageLoadTimeOut"));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static String getProperty(String key) {
		return config.getProperty(key);
	}

	public static void setProperty(String key, String value) {
		config.setProperty(key, value);
	}

	/*public static void main(String[] args) {
		System.out.println(getProperty("TestData.Source"));
		setProperty("xyz", "asdfsd");
		System.out.println(getProperty("xyz"));
		setProperty("xyz", "asdqqwwqrfsd");
		System.out.println(getProperty("xyz"));
		
	}*/

}
