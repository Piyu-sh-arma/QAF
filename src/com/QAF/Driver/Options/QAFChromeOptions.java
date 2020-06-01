package com.QAF.Driver.Options;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;

public class QAFChromeOptions {
	private static ChromeOptions chOps = null;
	private static final Logger log = Logger.getLogger(QAFChromeOptions.class);

	private QAFChromeOptions() {
	}

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
