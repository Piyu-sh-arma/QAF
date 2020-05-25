package com.QAF.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class QAFConfig {
	private static final Logger log = Logger.getLogger(QAFConfig.class);
	private static Properties config;
	static {
		config = new Properties();
		try {
			log.info("<---Initiating Properties--->");
			config.load(new FileInputStream("./resources/configFiles/QAFConfig.properties"));
			log.info("Properties Info - " + config);
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
