package com.QAF.Utils;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.SupportUtils.ExcelUtil;

public class FWDataManager {

	private static final Logger log = Logger.getLogger(FWDataManager.class);
	static HashMap<String, HashMap<String, String>> map = null;

	public static void initDataFromSource() {
		String source = ProjectConfig.getProperty("TestData.Source").trim().toUpperCase();
		switch (source) {
		case "EXCEL":
			String testDataFilePath = ProjectConfig.getProperty("TestData.ExcelFilePath");
			String testSheetName = ProjectConfig.getProperty("TestData.ExcelTestSheetName");
			map = ExcelUtil.loadExcelData(testDataFilePath, testSheetName);
			break;
		case "CSV":
			// Future code to be added in case needed.
		case "db":
			// Future code to be added in case needed.
		default:
			break;
		}
	}

	@DataProvider(name = "ExcelProvider")
	public static Object[][] getData(Method m) {
		log.info("Data requested for Test : " + m.getName());
		if (!map.containsKey(m.getName())) {
			log.error("Data not available for Test : " + m.getName());
			throw new RuntimeException("Data not available for Test : " + m.getName());
		}
		HashMap<String, String> dataMap = map.get(m.getName());
		log.info("Returning data for Test : " + m.getName() + " ->" + dataMap);
		return new Object[][] { { dataMap } };

	}
	
	
	
	public static HashMap<String, String> getTestData(String TestCaseId) {
		String testDataFilePath = ProjectConfig.getProperty("TestData.ExcelFilePath");
		String testSheetName = ProjectConfig.getProperty("TestData.ExcelTestSheetName");
		map = ExcelUtil.loadExcelData(testDataFilePath, testSheetName);
		if (map != null) {
			if (map.containsKey(TestCaseId)) {
				return map.get(TestCaseId);
			}
		}
		return null;
	}

	

	
	public static void main(String[] args) {
		System.out.println(getTestData("f1"));
	}
	 

}
