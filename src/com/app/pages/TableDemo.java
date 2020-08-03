package com.app.pages;

import com.app.htmlcomponents.TableWithHeaders;
import com.qaf.annotations.Find;
import com.qaf.base.QAFPage;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 */
public class TableDemo extends QAFPage {

    @Find(with = With.tagName, value = "table")
    public TableWithHeaders table;


    public TableDemo(WebDriver driver) {
        super(driver);
        QAFPageFactory.initElements(driver, this);
    }

}
