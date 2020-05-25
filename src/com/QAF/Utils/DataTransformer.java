package com.QAF.Utils;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import com.QAF.Exceptions.ExecutionException;
import com.QAF.annotations.QAFTest;
import com.SupportUtils.ExcelUtil;

/**
 * @author Piyush
 *
 */
public class DataTransformer {

	private static final Logger log = Logger.getLogger(DataTransformer.class);
	static HashMap<String, HashMap<String, String>> map = null;

	/************************************************
	 * Purpose - 
	 * 
	 *************************************************/
	public static void initDataFromSource() {
		String source = QAFConfig.getProperty("TestData.Source").trim().toUpperCase();
		switch (source) {
		case "EXCEL":
			String testDataFilePath = QAFConfig.getProperty("TestData.ExcelFilePath");
			String testSheetName = QAFConfig.getProperty("TestData.ExcelTestSheetName");
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
	 * 
	 *************************************************/
	@DataProvider(name = "ExcelProvider")
	public static Object[][] getData(Method m) {
		if (!m.isAnnotationPresent(QAFTest.class)) {
			throw new ExecutionException("Data Key not available for Test : " + m.getName() + ". Please add @TestKey annotation.");
		}
		String unqKey = m.getAnnotation(QAFTest.class).key();
		if (unqKey.isEmpty()) {
			throw new ExecutionException("Empty key for Test : " + m.getName());
		}
		log.info("Data requested for Test : " + unqKey);
		if (!map.containsKey(unqKey)) {
			log.error("Data not available for Test : " + unqKey);
			throw new ExecutionException("Data not available for Test : " + unqKey);
		}
		HashMap<String, String> dataMap = map.get(unqKey);
		log.info("Returning data for Test : " + unqKey + " ->" + dataMap);
		return new Object[][] { { dataMap } };

	}

	/************************************************
	 * Puspose - 
	 * 
	 *************************************************/
	public static HashMap<String, String> getData(String testKey) {
		if (map != null) {
			if (map.containsKey(testKey)) {
				return map.get(testKey);
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getData("f1"));
	}

}
