package com.app.component;

import com.qaf.annotations.Find;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

public class LogInForm extends QAFElement {
    @Find(with = With.name, value = "email")
    public QAFElement tbEmail;

    @Find(with = With.name, value = "password")
    public QAFElement tbPwd;

    @Find(with = With.xpath, value = ".//div[text()='Login']")
    public QAFElement btnLogin;

    public LogInForm(WebDriver driver, With with, String value) {
        super(driver, with, value);
        QAFPageFactory.initElements(this);
    }

}
