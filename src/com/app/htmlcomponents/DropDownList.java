package com.app.htmlcomponents;

import com.qaf.component.QAFElement;
import com.qaf.component.QAFWait;
import com.qaf.component.With;
import com.qaf.driver.options.QAFDriverManager;
import com.qaf.exceptions.QAFException;
import com.qaf.utils.QAFPageFactory;
import com.qaf.utils.Reporter;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Quotes;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DropDownList extends QAFElement implements ISelect {
    private static final Logger logger = Logger.getLogger(DropDownList.class);

    public DropDownList(WebDriver driver, With with, String value) {
        super(driver, with, value);
        QAFPageFactory.initElements(this);
    }

    @Override
    public boolean isMultiple() {
        try {
            boolean result = new Select(this.initElement()).isMultiple();
            if (result)
                logger.info("Multiple selections are allowed in drop down");
            else
                logger.info("Multiple selections are not allowed drop down");
            return result;
        } catch (Exception ex) {
            throw new QAFException(ex.getLocalizedMessage());
        }
    }

    @Override
    public void deselectAll() {
        if (!this.isMultiple())
            throw new QAFException("Multiple selections are not allowed drop down");
        for (WebElement ele : this.getOptions()) {
            this.setSelected(ele, false);
            logger.info(ele.getText() + " is deselected");
        }
    }

    @Override
    public void deselectByValue(String value) {
        if (!this.isMultiple())
            throw new QAFException("Multiple selections are not allowed drop down");
        try {
            Wait<WebElement> wait = new QAFWait<>(this.initElement());
            String optnXPath = ".//option[@value = " + Quotes.escape(value) + "]";
            Boolean found = wait.until(new Function<WebElement, Boolean>() {
                @Override
                public Boolean apply(WebElement element) {
                    if (element.findElements(By.xpath(optnXPath)).size() > 0)
                        return true;
                    else
                        logger.info("Waiting for elements having xpath : " + optnXPath);
                    return null;
                }
            });
            new Select(this.initElement()).deselectByValue(value);
        } catch (Exception ex) {
            throw new QAFException(ex.getLocalizedMessage());
        }
    }

    public void deselectByValue(String value, String eleName) {
        try {
            this.deselectByValue(value);
            Reporter.reportStep("Deselect option", "option with value" + value + " is deselected in list:" + eleName, StepStatus.PASS);
        } catch (RuntimeException ex) {
            throw new QAFException(eleName + " : " + ex.getMessage());
        }
    }

    @Override
    public void deselectByVisibleText(String text) {
        if (!this.isMultiple())
            throw new QAFException("Multiple selections are not allowed drop down");
        try {
            Wait<WebElement> wait = new QAFWait<>(this.initElement());
            String optnXPath = ".//option[normalize-space(.) = " + Quotes.escape(text) + "]";
            Boolean found = wait.until(new Function<WebElement, Boolean>() {
                @Override
                public Boolean apply(WebElement element) {
                    if (element.findElements(By.xpath(optnXPath)).size() > 0)
                        return true;
                    else
                        logger.info("Waiting for elements at xpath : " + optnXPath);
                    return null;
                }
            });
            new Select(this.initElement()).deselectByVisibleText(text);
        } catch (Exception ex) {
            throw new QAFException(ex.getLocalizedMessage());
        }
    }

    public void deselectByVisibleText(String text, String eleName) {
        try {
            this.deselectByVisibleText(text);
            Reporter.reportStep("Deselect option", "option with text" + text + " is deselected in list:" + eleName, StepStatus.PASS);
        } catch (RuntimeException ex) {
            throw new QAFException(eleName + " : " + ex.getMessage());
        }
    }

    @Override
    public List<WebElement> getOptions() {
        List<WebElement> optnLst = new Select(this.initElement()).getOptions();
        logger.info(optnLst.size() + " options are found in list");
        return optnLst;
    }

    @Override
    public List<WebElement> getAllSelectedOptions() {
        List<WebElement> optnLst = new ArrayList<>();
        for (WebElement ele : new Select(this.initElement()).getOptions()) {
            QAFElement qafOptnEle = new QAFElement(ele);
            if (qafOptnEle.isSelected()) {
                optnLst.add(ele);
            }
        }
        return optnLst;
    }

    @Override
    public void selectByValue(String value) {
    }

    @Override
    public void deselectByIndex(int index) {
        if (this.isDisplayed()) {
            Select lst = new Select(this.initElement());
            if (lst.getOptions().size() > index) {
                lst.deselectByIndex(index);
                logger.info("Index:" + index + " is deselected in Drop down list");
            } else {
                throw new QAFException("Index:" + index + " can't be deselected as number of items are less.");
            }
        } else {
            throw new QAFException("Drop down list is not displayed");
        }

    }

    public void deselectByIndex(int index, String eleName) {
        try {
            this.deselectByIndex(index);
            Reporter.reportStep("Deselect option", "option at index" + index + " is deselected in list:" + eleName, StepStatus.PASS);
        } catch (RuntimeException ex) {
            throw new QAFException(eleName + " : " + ex.getMessage());
        }
    }

    @Override
    public void selectByIndex(int index) {
        if (this.isDisplayed()) {
            Select lst = new Select(this.initElement());
            if (lst.getOptions().size() > index) {
                lst.selectByIndex(index);
                logger.info("Index:" + index + " is selected in Drop down list");
            } else {
                throw new QAFException("Index:" + index + " can't be selected as number of items are less.");
            }
        } else {
            throw new QAFException("Drop down list is not displayed");
        }
    }

    public void select(int index, String eleName) {
        try {
            this.selectByIndex(index);
            Reporter.reportStep("Select option", "option at index" + index + " is selected in list:" + eleName, StepStatus.PASS);
        } catch (Exception ex) {
            throw new QAFException(eleName + " : " + ex.getMessage());
        }
    }

    @Override
    public void selectByVisibleText(String text) {
        if (this.isDisplayed()) {
            if (this.findElement(With.xpath, ".//option[normalize-space(.)='" + text + "']").waitForElementToBePresent()) {
                new Select(this.initElement()).selectByVisibleText(text);
                logger.info("Text:" + text + " is selected in Drop down list.");
            } else {
                throw new QAFException("Drop down list has no option with text : " + text);
            }
        } else {
            throw new QAFException("Drop down list is not displayed");
        }

    }

    public void select(String visibleText, String eleName) {
        try {
            this.selectByVisibleText(visibleText);
            Reporter.reportStep("Select option", "option " + visibleText + " is selected in list:" + eleName, StepStatus.PASS);
        } catch (Exception ex) {
            throw new QAFException(eleName + " : " + ex.getMessage());
        }
    }

    public String selectWithValue(String value) {
        String optnText = "";
        if (this.isDisplayed()) {
            if (this.findElement(With.xpath, ".//option[@value='" + value + "']").waitForElementToBePresent()) {
                optnText = this.findElement(With.xpath, ".//option[@value='" + value + "']").getText();
                new Select(this.initElement()).selectByValue(value);
                logger.info("Value:" + value + " is selected in Drop down list. Visible Text:" + optnText);
            } else {
                throw new QAFException("Drop down list has no option with text : " + value);
            }
        } else {
            throw new QAFException("Drop down list is not displayed");
        }
        return optnText;

    }

    public void selectWithValue(String value, String eleName) {
        try {
            String optnText = this.selectWithValue(value);
            Reporter.reportStep("Select option", "option with value - " + value + " is selected in list:" + eleName + ", Visible Text:" + optnText, StepStatus.PASS);
        } catch (Exception ex) {
            throw new QAFException(eleName + " : " + ex.getMessage());
        }
    }

    @Override
    public WebElement getFirstSelectedOption() {
        WebElement ele = null;
        try {
            ele = new Select(this.initElement()).getFirstSelectedOption();
            return ele;
        } catch (Exception ex) {
            throw new QAFException(ex.getLocalizedMessage());
        }
    }


    public String getFirstSelectedOptionText() {
        String eleText = this.getFirstSelectedOption().getText();
        logger.info(eleText + " is the first selected option");
        return eleText;
    }

    /**
     * Select or deselect specified option
     *
     * @param option The option which state needs to be changed
     * @param select Indicates whether the option needs to be selected (true) or
     *               deselected (false)
     */
    private void setSelected(WebElement option, boolean select) {
        boolean isSelected = option.isSelected();
        if ((!isSelected && select) || (isSelected && !select)) {
            option.click();
        }
    }
}
