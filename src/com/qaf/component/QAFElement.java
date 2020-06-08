package com.qaf.component;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class QAFElement implements IWebComponent {
    private QAFElement parentQAFElement = null;
    private WebDriver driver = null;
    private String value;
    private With with;
    private WebElement element = null;


    private static final Logger log = Logger.getLogger(QAFElement.class);

    public QAFElement(WebDriver driver, With with, String value) {
        this.driver = driver;
        this.value = value;
        this.with = with;
    }

    public QAFElement(QAFElement parent, With with, String value) {
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

    private WebElement initElement() {
        WebElement ele;
        if (null != driver) {
            Wait<WebDriver> wait = new QAFWait<>(driver);
            ele = wait.until(driver -> {
                log.info("Locating element having " + with.toString() + " = " + value);
                return driver.findElement(getBy(with, value));
            });
        } else if (null != parentQAFElement) {
            WebElement parentWebElement = parentQAFElement.initElement();
            Wait<WebElement> wait = new QAFWait<>(parentWebElement);
            ele = wait.until(element -> {
                log.info("Locating element having " + with.toString() + " = " + value);
                return element.findElement(getBy(with, value));
            });
        } else {
            Wait<WebElement> wait = new QAFWait<>(element);
            ele = wait.until(element -> {
                if (element.isDisplayed() && element.isEnabled())
                    return element;
                else
                    return null;
            });
        }
        log.info("Element having " + with.toString() + " = " + value+" is located");
        return ele;
    }


    public QAFElement findElement(With with, String value) {
        return new QAFElement(this, with, value);
    }

    public List<QAFElement> findElements(With with, String value) {
        FluentWait<WebElement> wait = new FluentWait<>(this.initElement()).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofMillis(500));
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
            log.info("Element having " + with.toString() + " = " + value + " is clicked.");
        } catch (Exception e) {
            log.info("Element having " + with.toString() + " = " + value + " is not clicked.");
            log.error(e.getStackTrace());
        }

    }

    public void jsClick() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.initElement());
            log.info("Element having " + with.toString() + " = " + value + " is clicked using java script");

        } catch (Exception e) {
            log.error("Element having " + with.toString() + " = " + value + " is not clicked using java script");
            log.error(e.getStackTrace());
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

    public boolean isSelected() {
        return this.initElement().isSelected();
    }

    public boolean isEnabled() {

        return this.initElement().isEnabled();
    }

    public String getText() {
        return this.initElement().getText();
    }


    public boolean isDisplayed() {
        return this.initElement().isDisplayed();
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
        log.info("Returning driver..");
        return driver;
    }

    public boolean waitForVisible() {
        Wait<QAFElement> wait = new QAFWait<>(this);
        return wait.until(element1 -> element1.initElement().isDisplayed());
    }

    public void verifyVisible() {
        if (waitForVisible())
            System.out.println("Element is visible");
        else
            System.out.println("Element is not visible");

    }

}
