package com.QAF.Utils;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.QAF.annotations.QAFInput;
import com.SupportUtils.ExcelUtil;

/**
 * @author Piyush
 *
 */
/**
 * @author Piyush
 *
 */
public class FWDataManager {

	private static final Logger log = Logger.getLogger(FWDataManager.class);
	static HashMap<String, HashMap<String, String>> map = null;

	/************************************************
	 * Puspose - 
	 * @Copyright - Piyush Sharma
	 *************************************************/
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
	
	
	/************************************************
	 * Purpose - Provider method for testNg.
	 * @Copyright - Piyush Sharma
	 *************************************************/
	@DataProvider(name = "ExcelProvider")
	public static Object[][] getData(Method m) {		
		if(!m.isAnnotationPresent(QAFInput.class)) {
			throw new RuntimeException(
					"Data Key not available for Test : " + m.getName() + ". Please add @TestKey annotation to test");
		}
		String unqKey = m.getAnnotation(QAFInput.class).key();
		if (unqKey.isEmpty()) {
			throw new RuntimeException("Empty key for Test : " + m.getName());
		}
		log.info("Data requested for Test : " + unqKey);
		if (!map.containsKey(unqKey)) {
			log.error("Data not available for Test : " + unqKey);
			throw new RuntimeException("Data not available for Test : " + unqKey);
		}
		HashMap<String, String> dataMap = map.get(unqKey);
		log.info("Returning data for Test : " + unqKey + " ->" + dataMap);
		return new Object[][] { { dataMap } };

	}
	
	
	
	/************************************************
	 * Puspose - 
	 * @Copyright - Piyush Sharma
	 *************************************************/
	public static HashMap<String, String> getTestData(String testKey) {
		if (map != null) {
			if (map.containsKey(testKey)) {
				return map.get(testKey);
			}
		}
		return null;
	}

	

	
	public static void main(String[] args) {
		System.out.println(getTestData("f1"));
	}
	 

}
