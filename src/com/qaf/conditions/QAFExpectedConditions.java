package com.qaf.conditions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class QAFExpectedConditions {
    public static ExpectedCondition<Boolean> waitForPageLoadWithJS() {
        return driver -> ((JavascriptExecutor) driver).executeScript("document.readyState()").toString().equalsIgnoreCase("Complete");
    }
}
