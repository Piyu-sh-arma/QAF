package com.qaf.exceptions;

import bsh.This;
import org.apache.log4j.Logger;

/**
 * @author Piyush
 *
 */

public class QAFTimeOutException extends QAFException {
	public QAFTimeOutException(String message) {
		super(message);
	}
}