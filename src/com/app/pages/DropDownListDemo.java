package com.app.pages;

import com.app.htmlcomponents.DropDownList;
import com.app.htmlcomponents.RadioButton;
import com.qaf.annotations.Find;
import com.qaf.base.QAFPage;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 */
public class DropDownListDemo extends QAFPage {

    @Find(with = With.id, value = "select-demo")
    public DropDownList ddLstDemo;

    @Find(with = With.id, value = "multi-select")
    public DropDownList ddLstMultiSelect;


    public DropDownListDemo(WebDriver driver) {
        super(driver);
        QAFPageFactory.initElements(driver, this);
    }

}
