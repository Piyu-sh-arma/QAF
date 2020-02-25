package com.QAF.pages;

import org.apache.log4j.Logger;

public class MyPage {
	
	private static final Logger log = Logger.getLogger(MyPage.class);
	public void display() {
		log.info("Page Object =>"+Thread.currentThread().getName());
		
	}

}
