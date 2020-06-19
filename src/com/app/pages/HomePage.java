package com.app.pages;

import com.qaf.annotations.Find;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.base.QAFPage;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 *
 */
public class HomePage extends QAFPage {

	@Find(with = With.xpath,value = "//a[@id='basic_example']/span")
	public QAFElement lnkBasicExmpl;

	@Find(with = With.xpath,value = "//a[text()='Check Box Demo']")
	public QAFElement lnkChkBoxDemo;

	@Find(with = With.xpath,value = "//a[text()='Radio Buttons Demo']")
	public QAFElement lnkRadioBtnDemo;



	
	public HomePage(WebDriver driver) {
		super(driver);
		QAFPageFactory.initElements(driver,this);
	}


}
