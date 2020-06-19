package com.app.pages;

import com.app.component.CheckBox;
import com.app.component.LogInForm;
import com.qaf.annotations.Find;
import com.qaf.base.QAFPage;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 */
public class CheckBoxDemo extends QAFPage {

    @Find(with = With.id, value = "isAgeSelected")
    public CheckBox chkBoxAge;


    public CheckBoxDemo(WebDriver driver) {
        super(driver);
        QAFPageFactory.initElements(driver, this);
    }

}
