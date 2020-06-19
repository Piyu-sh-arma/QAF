package com.qaf.component;

import com.qaf.utils.QAFConfig;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class QAFWait<T> extends FluentWait<T> {
    public QAFWait(T input, long... waitForSeconds) {
        super(input);
        long waitDuration = QAFConfig.tryDuration;
        if (waitForSeconds.length > 0)
            waitDuration = waitForSeconds[0];

        this.withTimeout(Duration.ofSeconds(waitDuration));
        this.pollingEvery(Duration.ofMillis(QAFConfig.pollingInterval));
        this.ignoring(NoSuchElementException.class);
        this.ignoring(RuntimeException.class);
    }
}
