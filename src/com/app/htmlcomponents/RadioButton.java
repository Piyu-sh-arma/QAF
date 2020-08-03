package com.app.htmlcomponents;

import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import com.qaf.utils.Reporter;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class RadioButton extends QAFElement {
    private static final Logger log = Logger.getLogger(RadioButton.class);

    public void select(String rdBtnGender) {
        this.waitForElementToBeEnabled();
        this.select();
        if (this.isSelected())
            Reporter.reportStep("Select option", "option " + rdBtnGender + " is selected", StepStatus.PASS);
        else
            Reporter.reportStep("Select option", "option " + rdBtnGender + " is selected", StepStatus.FAIL);
    }

    public void select() {
        this.click();
        log.info("option is selected");
    }


    public RadioButton(WebDriver driver, With with, String value) {
        super(driver, with, value);
        QAFPageFactory.initElements(this);
    }

}
