package com.qaf.pages;

import com.qaf.annotations.Find;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 *
 */
public class HomePage extends QAFPage{

	@Find(with = With.xpath,value = "//*[text()='Log In']/parent::a")
	QAFElement lnkLogin;
	
	public HomePage(WebDriver driver) {
		super(driver);
		QAFPageFactory.initElements(driver,this);
	}


}
