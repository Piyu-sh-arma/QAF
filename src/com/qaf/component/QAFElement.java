package com.qaf.component;

import com.qaf.exceptions.QAFException;
import com.qaf.utils.Reporter;
import com.supportUtils.StepStatus;
import org.apache.log4j.Logger;
import org.omg.CORBA.TIMEOUT;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


public class QAFElement<T extends QAFElement> implements IWebComponent {
    private T parentQAFElement = null;
    private WebDriver driver = null;
    private String value;
    private With with;
    private WebElement element = null;


    private static final Logger logger = Logger.getLogger(QAFElement.class);

    public QAFElement(WebDriver driver, With with, String value) {
        this.driver = driver;
        this.value = value;
        this.with = with;
    }

    public QAFElement(T parent, With with, String value) {
        this.parentQAFElement = parent;
        this.value = value;
        this.with = with;
    }


    public QAFElement(WebElement element) {
        this.element = element;
    }

    public By getBy(With with, String value) {
        By by;
        switch (with) {
            case xpath:
                by = By.xpath(value);
                break;
            case id:
                by = By.id(value);
                break;
            case linkText:
                by = By.linkText(value);
                break;
            case name:
                by = By.name(value);
                break;
            case tagName:
                by = By.tagName(value);
                break;
            case className:
                by = By.className(value);
                break;
            case cssSelector:
                by = By.cssSelector(value);
                break;
            case partialLinkText:
                by = By.partialLinkText(value);
            default:
                by = null;
                break;
        }
        return by;

    }

    protected WebElement initElement() {
        WebElement ele;
        if (null != driver) {
            Wait<WebDriver> wait = new QAFWait<>(driver);
            try {
                ele = wait.until(driver -> {
                    logger.info("Locating element having " + with.toString() + " = " + value);
                    return driver.findElement(getBy(with, value));
                });
            } catch (TimeoutException exTO) {
                throw new QAFException("Timeout while locating element having " + with.toString() + " = " + value);
            }
        } else if (null != parentQAFElement) {
            WebElement parentWebElement = parentQAFElement.initElement();
            Wait<WebElement> wait = new QAFWait<>(parentWebElement);
            try {
                ele = wait.until(element -> {
                    logger.info("Locating element having " + with.toString() + " = " + value);
                    return element.findElement(getBy(with, value));
                });
            } catch (TimeoutException exTO) {
                throw new QAFException("Timeout while locating element having " + with.toString() + " = " + value);
            }
        } else {
            Wait<WebElement> wait = new QAFWait<>(element);
            try {
                ele = wait.until(element -> {
                    if (element.isDisplayed() && element.isEnabled())
                        return element;
                    else {
                        logger.info("Waiting for WebElement to be visible");
                        return null;
                    }
                });
            } catch (TimeoutException exTO) {
                throw new QAFException("Timeout while locating webelement");
            }
        }
        logger.info("Element having " + with.toString() + " = " + value + " is located");
        return ele;
    }


    public QAFElement findElement(With with, String value) {
        return new QAFElement(this, with, value);
    }

    public List<QAFElement> findElements(With with, String value) {
        Wait<WebElement> wait = new QAFWait<>(this.initElement());
        return wait.until(element1 -> {
            List<WebElement> elements = element1.findElements(getBy(with, value));
            if (!(elements.size() > 0))
                return null;
            List<QAFElement> qafElements = new ArrayList<>();
            for (WebElement ele : elements)
                qafElements.add(new QAFElement(ele));
            return qafElements;
        });
    }

    public WebElement getWrappedWebElement() {
        return this.initElement();
    }

    public WebElement findElement(By by) {
        return this.getWrappedWebElement().findElement(by);
    }


    public List<WebElement> findElements(By by) {
        return this.getWrappedWebElement().findElements(by);
    }

    public void click() {
        try {
            this.initElement().click();
            logger.info("Element having " + with.toString() + " = " + value + " is clicked.");
        } catch (Exception e) {
            logger.info("Element having " + with.toString() + " = " + value + " is not clicked.");
            logger.error(e.getStackTrace());
        }

    }

    public void jsClick() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.initElement());
            logger.info("Element having " + with.toString() + " = " + value + " is clicked using java script");

        } catch (Exception e) {
            logger.error("Element having " + with.toString() + " = " + value + " is not clicked using java script");
            logger.error(e.getStackTrace());
        }

    }


    public void submit() {
        this.initElement().submit();
    }


    public void sendKeys(CharSequence... keysToSend) {
        this.initElement().sendKeys(keysToSend);
    }

    public void clear() {
        this.initElement().clear();
    }

    public void type(CharSequence... keysToSend) {
        this.clear();
        this.sendKeys(keysToSend);
    }

    public String getTagName() {
        return this.initElement().getTagName();
    }

    public String getAttribute(String name) {
        return this.initElement().getAttribute(name);
    }


    public String getText() {
        return this.initElement().getText();
    }

    public Point getLocation() {
        return this.initElement().getLocation();
    }

    public Dimension getSize() {
        return this.initElement().getSize();
    }

    public Rectangle getRect() {
        return this.initElement().getRect();
    }

    public String getCssValue(String propertyName) {
        return this.initElement().getCssValue(propertyName);
    }

    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return this.initElement().getScreenshotAs(target);
    }

    public WebElement getWrappedElement() {
        return ((WrapsElement) this.initElement()).getWrappedElement();
    }

    public Coordinates getCoordinates() {
        return ((Locatable) this.initElement()).getCoordinates();
    }

    public WebDriver getDriver() {
        logger.info("Returning driver..");
        return driver;
    }


    public boolean waitForElementToBeVisible(long... waitForSeconds) {
        Wait<QAFElement> wait = new QAFWait<>(this, waitForSeconds);
        Boolean result = false;
        try {
            result = wait.until(qafElement -> {
                if (qafElement.initElement().isDisplayed()) {
                    logger.info("Element is visible");
                    return true;
                } else {
                    logger.info("Waiting for element to be visible");
                    return null;
                }

            });
        } catch (TimeoutException exTO) {
            logger.error("Timeout while waiting for element to be visible");
        }
        return result;
    }

    public boolean waitForElementToBeInvisible(long... waitForSeconds) {
        Wait<QAFElement> wait = new QAFWait<>(this, waitForSeconds);
        Boolean result = false;
        try {
            result = wait.until(new Function<QAFElement, Boolean>() {
                @Override
                public Boolean apply(QAFElement qafElement) {
                    if (qafElement.initElement().isDisplayed()) {
                        logger.info("Waiting for element to be invisible");
                        return null;
                    } else {
                        logger.info("Element is invisible");
                        return true;
                    }

                }
            });
        } catch (TimeoutException exTO) {
            logger.error("Timeout while waiting for element to be invisible");
        }
        return result;
    }

    public boolean isDisplayed() {
        if (this.initElement().isDisplayed()) {
            logger.info("Element is displayed");
            return true;
        } else {
            logger.error("Element is not displayed");
            return false;
        }
    }

    public boolean isNotDisplayed() {
        if (!this.initElement().isDisplayed()) {
            logger.info("Element is not displayed");
            return true;
        } else {
            logger.error("Element is displayed");
            return false;
        }

    }

    public void verifyVisible(String eleName) {
        if (this.waitForElementToBeVisible())
            Reporter.reportStep("Verify element visible", eleName + " is visible", StepStatus.PASS);
        else
            Reporter.reportStep("Verify element visible", eleName + " is not visible", StepStatus.FAIL);
    }

    public void verifyInvisible(String eleName) {
        if (this.waitForElementToBeInvisible())
            Reporter.reportStep("Verify element visible", eleName + " is visible", StepStatus.PASS);
        else
            Reporter.reportStep("Verify element visible", eleName + " is not visible", StepStatus.FAIL);
    }

    public boolean waitForElementToBeEnabled(long... waitForSeconds) {
        Wait<QAFElement> wait = new QAFWait<>(this, waitForSeconds);
        Boolean result = false;
        try {
            result = wait.until(new Function<QAFElement, Boolean>() {
                @Override
                public Boolean apply(QAFElement qafElement) {
                    if (qafElement.initElement().isEnabled()) {
                        logger.info("Element is enabled");
                        return true;
                    } else {
                        logger.info("Waiting for element to be enabled");
                        return null;
                    }

                }
            });
        } catch (TimeoutException exTO) {
            logger.error("Timeout while waiting for element to be enabled");
        }
        return result;
    }

    public boolean waitForElementToBeDisabled(long... waitForSeconds) {
        Wait<QAFElement> wait = new QAFWait<>(this, waitForSeconds);
        Boolean result = false;
        try {
            result = wait.until(new Function<QAFElement, Boolean>() {
                @Override
                public Boolean apply(QAFElement qafElement) {
                    if (qafElement.initElement().isEnabled()) {
                        logger.info("Waiting for element to be disabled");
                        return null;
                    } else {
                        logger.info("Element is disabled");
                        return true;
                    }
                }
            });
        } catch (TimeoutException exTO) {
            logger.error("Timeout while waiting for element to be disabled");
        }
        return result;
    }

    public boolean isEnabled() {
        if (this.initElement().isEnabled()) {
            logger.info("Element is enabled");
            return true;
        } else {
            logger.error("Element is not enabled");
            return false;
        }
    }

    public boolean isDisabled() {
        if (this.initElement().isEnabled()) {
            logger.error("Element is enabled");
            return false;
        } else {
            logger.info("Element is not enabled");
            return true;
        }
    }

    public void verifyEnabled(String eleName) {
        if (this.waitForElementToBeEnabled())
            Reporter.reportStep("Verify element enabled", eleName + " is enabled", StepStatus.PASS);
        else
            Reporter.reportStep("Verify element disabled", eleName + " is disabled", StepStatus.FAIL);
    }

    public void verifyDisabled(String eleName) {
        if (this.waitForElementToBeDisabled())
            Reporter.reportStep("Verify element disabled", eleName + " is disabled", StepStatus.PASS);
        else
            Reporter.reportStep("Verify element disabled", eleName + " is enabled", StepStatus.FAIL);
    }

    public boolean waitForElementToBeSelected(long... waitForSeconds) {
        Wait<QAFElement> wait = new QAFWait<>(this, waitForSeconds);
        Boolean result = false;
        try {
            result = wait.until(new Function<QAFElement, Boolean>() {
                @Override
                public Boolean apply(QAFElement qafElement) {
                    if (qafElement.initElement().isSelected()) {
                        logger.info("Element is selected");
                        return true;
                    } else {
                        logger.info("Waiting for element to be selected");
                        return null;
                    }

                }
            });
        } catch (TimeoutException exTO) {
            logger.error("Timeout while waiting for element to be selected");
        }
        return result;

    }

    public boolean waitForElementToBeDeselected(long... waitForSeconds) {
        Wait<QAFElement> wait = new QAFWait<>(this, waitForSeconds);
        Boolean result = false;
        try {
            result = wait.until(new Function<QAFElement, Boolean>() {
                @Override
                public Boolean apply(QAFElement qafElement) {
                    if (qafElement.initElement().isSelected()) {
                        logger.info("Waiting for element to be deselected");
                        return null;
                    } else {
                        logger.info("Element is deselected");
                        return true;
                    }

                }
            });
        } catch (TimeoutException exTO) {
            logger.error("Timeout while checking for element to be selectable");
        }
        return result;
    }

    public boolean isSelected() {
        if(this.initElement().isSelected()){
            logger.info("Element is selected");
            return true;
        }else {
            logger.error("Element is not selected");
            return false;
        }
    }

    public boolean isDeselected() {
        if (!this.initElement().isSelected()) {
            logger.info("Element is not selected");
            return true;
        } else {
            logger.error("Element is selected");
            return false;
        }

    }

    public void verifySelected(String eleName) {
        if (this.waitForElementToBeSelected())
            Reporter.reportStep("Verify element selected", eleName + " is selected", StepStatus.PASS);
        else
            Reporter.reportStep("Verify element selected", eleName + " is selected", StepStatus.FAIL);
    }

    public void verifyDeselected(String eleName) {
        if (this.waitForElementToBeDeselected())
            Reporter.reportStep("Verify element deselected", eleName + " is deselected", StepStatus.PASS);
        else
            Reporter.reportStep("Verify element deselected", eleName + " is deselected", StepStatus.FAIL);
    }

}
