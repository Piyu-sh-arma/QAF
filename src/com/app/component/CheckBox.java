package com.app.component;

import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import com.qaf.utils.Reporter;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class CheckBox extends QAFElement {
    private static final Logger log = Logger.getLogger(CheckBox.class);

    public void check(String chkBoxName) {
        if (this.isSelected()) {
            Reporter.reportStep("Select checkbox", "Checkbox " + chkBoxName + " is already checked", StepStatus.PASS);
            return;
        }
        this.waitForElementToBeEnabled();
        this.check();
        if (this.isSelected())
            Reporter.reportStep("Select checkbox", "Checkbox " + chkBoxName + " is checked", StepStatus.PASS);
        else
            Reporter.reportStep("Select checkbox", "Checkbox " + chkBoxName + " is unchecked", StepStatus.FAIL);
    }

    public void unCheck(String chkBoxName) {
        if (!this.isSelected()) {
            Reporter.reportStep("Deselect checkbox", "Checkbox " + chkBoxName + " is already unchecked", StepStatus.PASS);
            return;
        }
        this.waitForElementToBeEnabled();
        this.unCheck();
        if (this.isDeselected())
            Reporter.reportStep("Deselect checkbox", "Checkbox " + chkBoxName + " is unchecked", StepStatus.PASS);
        else
            Reporter.reportStep("Deselect checkbox", "Checkbox " + chkBoxName + " is checked", StepStatus.FAIL);
    }

    public void check() {
        if (!this.isSelected()) {
            this.click();
            log.info("CheckBox is checked");
        } else {
            log.info("CheckBox is already checked");
        }
    }

    public void unCheck() {
        if (this.isSelected()) {
            this.click();
            log.info("CheckBox is unchecked");
        } else {
            log.info("CheckBox is already unchecked");
        }
    }


    public CheckBox(WebDriver driver, With with, String value) {
        super(driver, with, value);
        QAFPageFactory.initElements(this);
    }

}
