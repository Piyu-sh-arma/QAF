package com.supportUtils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelUtil {

	private static final Logger log = Logger.getLogger(ExcelUtil.class);

	public static HashMap<String, HashMap<String, String>> loadExcelData(String testDataFilePath, String testSheetName) {
		Workbook wb = null;
		Sheet sheet;
		HashMap<String, HashMap<String, String>> map = new HashMap<>();

		if (testDataFilePath == null || testSheetName == null) {
			log.error("Test Data file parameters are empty. Check properties file");
			throw new RuntimeException("Test Data file parameters are empty. Check properties file");
		}

		File xlFile = new File(testDataFilePath);
		if (!xlFile.exists()) {
			log.error("Test Data file not found at location : " + testDataFilePath);
			throw new RuntimeException("Test Data file not found at location : " + testDataFilePath);
		}
		if (!(testSheetName.length() > 0)) {
			log.error("Test Sheet name is Empty. Check TestData.ExcelTestSheetName value in QAFConfig.properties");
			throw new RuntimeException("Test Sheet name is Empty. Check TestData.ExcelTestSheetName value in QAFConfig.properties");
		}
		try {
			log.info("Attempting to load data from " + testDataFilePath);
			if (testDataFilePath.toUpperCase().contains("XLSX")) {
				wb = new XSSFWorkbook(new FileInputStream(xlFile));
			} else {
				wb = new HSSFWorkbook(new FileInputStream(xlFile));
			}
			sheet = wb.getSheet(testSheetName);
			if (sheet == null) {
				log.error("Test Sheet " + testSheetName + " , is not found in Test data file.");
				wb.close();
				log.info("Data file closed, " + testDataFilePath);
				throw new RuntimeException("Test Sheet " + testSheetName + " , is not found in Test data file.");
			}

			int intRowCount = sheet.getLastRowNum();
			int intColCount = sheet.getRow(0).getLastCellNum();
			log.info("Excel file has " + intRowCount + "-Rows & " + intColCount + "-Columns");
			ArrayList<String> paramList = new ArrayList<>();
			for (int i = 0; i < intColCount; i++) {
				Row curRow = sheet.getRow(0);
				Cell curCell = curRow.getCell(i);
				String value = getCellValueAsString(curCell);
				//System.out.println(value);
				paramList.add(value);
			}

			for (int i = 1; i <= intRowCount; i++) {
				String strTestCaseId = getCellValueAsString(sheet.getRow(i).getCell(0));
				HashMap<String, String> tempMap = new HashMap<>();
				for (int j = 0; j < intColCount; j++) {
					Cell curCell = sheet.getRow(i).getCell(j);
					String value = getCellValueAsString(curCell);
					tempMap.put(paramList.get(j), value);
				}
				map.put(strTestCaseId, tempMap);
			}
			log.info("Data loaded - " + map);
		} catch (Exception e) {
			log.error("Exception while loading/reading the file. \n" + ExceptionUtils.getStackTrace(e));
			throw new RuntimeException("Exception while loading/reading the Excel file.");
		} finally {
			try {
				wb.close();
				log.info("Data file closed, " + testDataFilePath);
			} catch (IOException e1) {
				if (e1.getLocalizedMessage().contains("file because it is being used")) {
					log.info("File is being used already. Opened in ready only mode");
				}
				log.error(e1.getLocalizedMessage());
			}
		}
		return map;
	}

	private static String getCellValueAsString(Cell currCell) {
		String returnVal = "";
		CellType ct = currCell.getCellType();

		if (ct == CellType.FORMULA) {
			ct = currCell.getCachedFormulaResultType();
		}
		switch (ct) {
		case STRING:
			returnVal = currCell.getStringCellValue();
			break;
		case NUMERIC:
			returnVal = String.valueOf(currCell.getNumericCellValue());
			break;
		case BOOLEAN:
			returnVal = String.valueOf(currCell.getBooleanCellValue());
			break;
		default:
			break;
		}
		return returnVal;
	}
}
