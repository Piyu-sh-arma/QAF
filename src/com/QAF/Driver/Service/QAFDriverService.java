
/*
 * Note :- Start services for all browsers before test start
 * 
 * 
 */

package com.QAF.Driver.Service;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.service.DriverService;

import com.QAF.Utils.QAFConfig;

public class QAFDriverService {
	static ChromeDriverService chromeDriverService = null;
	static InternetExplorerDriverService ieDriverService = null;
	static EdgeDriverService edgeDriverService = null;
	private static final Logger log = Logger.getLogger(QAFDriverService.class);

	public static ChromeDriverService getChromeDriverService() {
		return chromeDriverService;
	}

	public static InternetExplorerDriverService getIeDriverService() {
		return ieDriverService;
	}

	public static EdgeDriverService getEdgeDriverService() {
		return edgeDriverService;
	}

	public static DriverService getDriverService(String browserName) {
		switch (browserName) {
		case "CHROME":
			return chromeDriverService;
		case "IE":
			return ieDriverService;
		case "EDGE":
			return edgeDriverService;
		default:
			break;
		}
		return edgeDriverService;
	}

	private QAFDriverService() {
	}

    public static void startChromeService() {
		try {
			String driverExePath = QAFConfig.getProperty("webdriver.chrome.driver");
			if (chromeDriverService == null)
				chromeDriverService = new ChromeDriverService.Builder().usingAnyFreePort().usingDriverExecutable(new File(driverExePath)).build();
			if (!chromeDriverService.isRunning()) {
				chromeDriverService.start();
				log.info("Chrome service started at URL : " + chromeDriverService.getUrl());
			} else {
				log.info("Chrome service already running at URL : " + chromeDriverService.getUrl());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void startIEService() {
		try {
			String driverExePath = QAFConfig.getProperty("webdriver.ie.driver");
			if (ieDriverService == null)
				ieDriverService = new InternetExplorerDriverService.Builder().usingDriverExecutable(new File(driverExePath)).usingAnyFreePort().build();

			if (!ieDriverService.isRunning())
				ieDriverService.start();

			log.info("IE service started at URL : " + ieDriverService.getUrl());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void startEdgeService() {
		try {
			String driverExePath = QAFConfig.getProperty("webdriver.edge.driver");
			if (edgeDriverService == null)
				edgeDriverService = new EdgeDriverService.Builder().usingDriverExecutable(new File(driverExePath)).usingAnyFreePort().build();

			if (!edgeDriverService.isRunning())
				ieDriverService.start();

			log.info("Edge service started with URL : " + edgeDriverService.getUrl());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void stopChromeService() {
		if (chromeDriverService != null && chromeDriverService.isRunning())
			chromeDriverService.stop();
		log.info("Chrome service Stopped");
	}

	//Other methods to be added.
	//	public static void startChromeService(String driverExePath, int portNum) {}
	//	public static void startIEService(String driverExePath, int portNum) {}
	//	public static void startEdgeService(String driverExePath, int portNum) {}

}
