package com.qaf.base;

import com.qaf.annotations.QAFTest;
import com.qaf.driver.options.QAFDriverManager;
import com.qaf.exceptions.QAFException;
import com.qaf.utils.DataTransformer;
import com.qaf.utils.Reporter;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;
import java.util.HashMap;

public abstract class QAFBaseTest {
    private static final Logger log = Logger.getLogger(QAFBaseTest.class);

    @SuppressWarnings("unchecked")
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method testMethod) {
        if (!testMethod.isAnnotationPresent(QAFTest.class))
            throw new QAFException("Test Method - " + testMethod.getName() + " is annotated with QAFTest");

        String unqKey = testMethod.getAnnotation(QAFTest.class).key();
        if (unqKey.isEmpty())
            throw new QAFException("Key is not set Test Method - " + testMethod.getName());

        Thread.currentThread().setName(Thread.currentThread().getId() + "~_" + unqKey);
        log.info("Executing Before Method for test - " + unqKey);
        Reporter.initializeReport();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(Method m) {
        log.info("Executing After Method for test - " + Thread.currentThread().getName().split("~_")[1]);
        Reporter.closeReport();
        QAFDriverManager.quitDriver();

    }

}
