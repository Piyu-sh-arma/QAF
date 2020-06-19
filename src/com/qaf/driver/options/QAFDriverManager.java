package com.qaf.driver.options;

import com.qaf.driver.service.QAFDriverService;
import com.qaf.exceptions.QAFException;
import com.qaf.utils.QAFConfig;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Piyush
 */
public class QAFDriverManager {
    private static final Logger log = Logger.getLogger(QAFDriverManager.class);
    private WebDriver driver = null;
    String browserName;
    public static Map<Long, WebDriver> driverMap = new HashMap<>();

    public QAFDriverManager(String browserName) {
        this.browserName = browserName.toUpperCase();
    }

    public QAFDriverManager() {
        browserName = QAFConfig.getProperty("Application.Browser").toUpperCase();
    }

    public WebDriver getDriver() {
        if (driver == null) {
            boolean gridEnabled = Boolean.parseBoolean(QAFConfig.getProperty("Grid.Enabled"));
            String strGridURL = QAFConfig.getProperty("Grid.URL");
            URL remoteURL;
            if (gridEnabled) {
                if (strGridURL.trim().isEmpty())
                    throw new QAFException("Grid is enabled but Grid URL is empty");
                else {
                    try {
                        remoteURL = new URL(strGridURL);
                        log.info("Grid is enabled. URL -" + strGridURL);
                    } catch (MalformedURLException e) {
                        throw new QAFException("Grid is enabled but Grid URL is not proper. " + strGridURL);
                    }

                }
            } else {
                remoteURL = QAFDriverService.getDriverService(browserName).getUrl();
            }

            switch (browserName) {
                case "CHROME":
                    driver = new RemoteWebDriver(remoteURL, QAFChromeOptions.getOptions());
                    driverMap.put(Thread.currentThread().getId(), driver);
                    break;
                case "IE":
                    driver = new RemoteWebDriver(remoteURL, QAFIEOptions.getOptions());
                    break;
                case "EDGE":
                    driver = new RemoteWebDriver(remoteURL, QAFEdgeOptions.getOptions());
                    break;
                default:
                    throw new QAFException(browserName + " is not supported");
            }

        }
        driver.manage().timeouts().pageLoadTimeout(QAFConfig.pageLoadTimeOut, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(QAFConfig.implicitWait, TimeUnit.SECONDS);
        return driver;
    }

    public static void quitDriver() {
        WebDriver driver = driverMap.get(Thread.currentThread().getId());
        if (null != driver) {
            driver.quit();
            log.info("Driver quited for test : " + Thread.currentThread().getName().split("~_")[1]);
        } else {
            log.error("Couldn't find driver for test : " + Thread.currentThread().getName().split("~_")[1]);
        }

    }

}
