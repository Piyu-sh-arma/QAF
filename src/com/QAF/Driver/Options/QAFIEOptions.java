package com.QAF.Driver.Options;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.QAF.Utils.QAFConfig;

public class QAFIEOptions {
	private static InternetExplorerOptions ieOps = null;
	private static final Logger log = Logger.getLogger(QAFIEOptions.class);

	public static void loadOptions() {
		ieOps = new InternetExplorerOptions();
//		System.setProperty("webdriver.ie.driver",ProjectConfig.getProperty("webdriver.ie.driver"));
		try {
			String options = QAFConfig.getProperty("ie.options");			
			JSONObject jo = new JSONObject(options);
			for (String opt : jo.keySet()) {
				ieOps.setCapability(opt, jo.get(opt));
			}
			
		} catch (Exception e) {
			log.info("Failed to load IExplorer Options from properties file. Error is : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static InternetExplorerOptions getOptions() {
		if (null == ieOps)
			loadOptions();
		return ieOps;

	}

}
