package com.qaf.exceptions;

import bsh.This;
import org.apache.log4j.Logger;

/**
 * @author Piyush
 */
public class QAFException extends RuntimeException {
    private static final Logger log = Logger.getLogger(This.class);

    public QAFException(String message) {
        super(message);
        log.error(message);
    }

}