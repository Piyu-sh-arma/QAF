package com.TestSuits;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.QAF.Base.TestBase;
import com.QAF.Utils.DataManager;
import com.QAF.pages.MyPage;

import bsh.This;

import java.util.HashMap;

public class AppTests2 extends TestBase {
	private static final Logger log = Logger.getLogger(This.class);

	@Test(dataProvider = "ExcelProvider", dataProviderClass = DataManager.class)
	public void f3(HashMap<String, String> data) {
		System.out.println("Executing test ->" + data);
		log.info("************" + Thread.currentThread().getName());
		MyPage page = new MyPage();
		page.display();
	}

}
