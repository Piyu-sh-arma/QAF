package com.qaf.component;

import com.qaf.utils.QAFConfig;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class QAFWait<T> extends FluentWait<T> {
    public QAFWait(T input) {
        super(input);
        this.withTimeout(Duration.ofSeconds(QAFConfig.tryDuration));
        this.pollingEvery(Duration.ofMillis(QAFConfig.pollingInterval));
        this.ignoring(NoSuchElementException.class);
    }
}
