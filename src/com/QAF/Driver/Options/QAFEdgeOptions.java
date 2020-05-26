package com.QAF.Driver.Options;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.edge.EdgeOptions;

import com.QAF.Utils.QAFConfig;

public class QAFEdgeOptions {
	private static EdgeOptions edgeOps = null;
	private static final Logger log = Logger.getLogger(QAFEdgeOptions.class);
	
	private QAFEdgeOptions() {};

	public static void loadOptions() {
		edgeOps = new EdgeOptions();
		System.setProperty("webdriver.edge.driver",QAFConfig.getProperty("webdriver.edge.driver"));
		try {
			String options = QAFConfig.getProperty("edge.options");
			JSONObject jo = new JSONObject(options);
			for (String opt : jo.keySet()) {
				edgeOps.setCapability(opt, jo.get(opt));
			}
		} catch (Exception e) {
			log.info("Failed to load IExplorer Options from properties file. Error is : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static EdgeOptions getOptions() {
		if (null == edgeOps)
			loadOptions();
		return edgeOps;

	}

}
