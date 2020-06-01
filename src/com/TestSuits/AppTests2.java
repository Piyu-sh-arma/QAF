package com.TestSuits;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.QAF.Base.QAFBaseTest;
import com.QAF.Utils.DataTransformer;
import com.QAF.Utils.QAFConfig;
import com.QAF.annotations.QAFTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AppTests2 extends QAFBaseTest {
	private static final Logger log = Logger.getLogger(AppTests2.class);

	@QAFTest(key = "T_Key_3")
	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataTransformer.class)
	public void f3(HashMap<String, String> data) throws IOException {
		
		ChromeDriverService serv = new ChromeDriverService.Builder().usingAnyFreePort()
				.usingDriverExecutable(new File(QAFConfig.getProperty("webdriver.chrome.driver"))).build();
		
		serv.start();

		WebDriver chdriver = new RemoteWebDriver(serv.getUrl(), new ChromeOptions());
		chdriver.get("http://www.google.com");
		System.out.println(chdriver.getTitle());

		serv.stop();
		

	}

}
