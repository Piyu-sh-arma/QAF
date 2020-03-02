package com.TestSuits;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.QAF.Base.QAFTest;
import com.QAF.Utils.DataTransformer;
import bsh.This;

import java.util.HashMap;

public class AppTests2 extends QAFTest {
	private static final Logger log = Logger.getLogger(This.class);

	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataTransformer.class)
	public void f3(HashMap<String, String> data) {
		System.out.println("Executing test ->" + data);
		log.info("************" + Thread.currentThread().getName());
		
	}

}
