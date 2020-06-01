package com.QAF.Exceptions;

import org.apache.log4j.Logger;

import bsh.This;

/**
 * @author Piyush
 *
 */
public class ExecutionException extends RuntimeException {
	private static final Logger log = Logger.getLogger(This.class);
	public ExecutionException(String message) {
		super(message);
		log.error(message);
	}

}
