package com.QAF.Driver.Options;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.QAF.Utils.QAFConfig;

public class QAFChromeOptions {
	private static ChromeOptions chOps = null;
	private static final Logger log = Logger.getLogger(QAFChromeOptions.class);

	private QAFChromeOptions() {
	};

	public static ChromeOptions getOptions() {
		try {
			if (null == chOps) {
				chOps = new ChromeOptions();
				chOps.addArguments("start-maximized");
			}
		} catch (Exception e) {
			log.info("Failed to load Chrome Options");
		}

		return chOps;

	}

}
