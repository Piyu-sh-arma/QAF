package com.app.pages;

import com.app.component.LogInForm;
import com.qaf.annotations.Find;
import com.qaf.component.QAFElement;
import com.qaf.component.With;
import com.qaf.base.QAFPage;
import com.qaf.utils.QAFPageFactory;
import org.openqa.selenium.WebDriver;

/**
 * @author Piyush
 */
public class LoginPage extends QAFPage {

    @Find(with = With.xpath, value = "//a[text()='Forgot your password?']")
    public QAFElement lnkForgotPwd;

    @Find(with = With.tagName, value = "form")
    public LogInForm logInForm;


    public LoginPage(WebDriver driver) {
        super(driver);
        QAFPageFactory.initElements(driver, this);
    }

    public void logIn(String email, String password) {
        logInForm.tbEmail.type(email);
        logInForm.tbPwd.type(password);
        logInForm.btnLogin.click();
    }

}
