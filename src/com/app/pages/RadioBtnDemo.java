package com.app.pages;

import com.app.htmlcomponents.RadioButton;
import com.qaf.annotations.Find;
import com.qaf.base.QAFPage;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 */
public class RadioBtnDemo extends QAFPage {

    @Find(with = With.xpath, value = "//input[@type='radio' and @name='optradio' and @value='Male']")
    public RadioButton rdBtnGenderMale;

    @Find(with = With.xpath, value = "//input[@type='radio' and @name='optradio' and @value='Female']")
    public RadioButton rdBtnGenderFemale;


    public RadioBtnDemo(WebDriver driver) {
        super(driver);
        QAFPageFactory.initElements(driver, this);
    }

}
