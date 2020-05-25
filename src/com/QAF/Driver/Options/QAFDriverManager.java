/**
 * 
 */
package com.QAF.Driver.Options;

import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.QAF.Driver.Service.QAFDriverService;
import com.QAF.Utils.QAFConfig;

/**
 * @author Piyush
 *
 */
public class QAFDriverManager {
	private static final Logger log = Logger.getLogger(QAFDriverManager.class);		
	private WebDriver driver;
	String browserName;

	public WebDriver getDriver() {		
		return driver;
	}

	public QAFDriverManager(String browserName) {
		this.browserName= browserName.toUpperCase();	
		
	}
	
	public QAFDriverManager() {
		browserName = QAFConfig.getProperty("Application.Browser").toUpperCase();
		switch (browserName) {
		case "CHROME":
			URL remoteURL = QAFDriverService.getChromeDriverService().getUrl();
			driver = new RemoteWebDriver(remoteURL,QAFChromeOptions.getOptions());			
			break;

		default:
			break;
		}
		
	}
	

}
