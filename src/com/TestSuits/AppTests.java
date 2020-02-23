package com.TestSuits;

import org.testng.annotations.Test;

import com.Base.TestBase;
import com.QAF.Utils.FWDataManager;

import java.util.HashMap;

public class AppTests extends TestBase{
  @Test(dataProvider = "ExcelProvider", dataProviderClass = FWDataManager.class)
  public void fx(HashMap<String,String> data) {
	  System.out.println("Executing test ->"+data);
  }
  
  @Test(dataProvider = "ExcelProvider", dataProviderClass = FWDataManager.class)
  public void f1(HashMap<String,String> data) {
	  System.out.println("Executing test ->"+data);
  }
}
