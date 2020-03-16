/**
 * 
 */
package com.QAF.Utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Piyush
 *
 */
public class DriverProvider {
	private static final Logger log = Logger.getLogger(DriverProvider.class);
	
	public void initDriver() {
		ProjectConfig.getProperty("Application.Browser");
		
	}
	WebDriver driver = new ChromeDriver();

}
