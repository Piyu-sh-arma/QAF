package com.QAF.Utils;

import org.apache.log4j.Logger;
import org.testng.IExecutionListener;

public class QWAFListerners implements IExecutionListener {
	
	private static final Logger log = Logger.getLogger(QWAFListerners.class);

	@Override
	public void onExecutionStart() {
		log.info("<---- Starting TestNG Execution ---->");		
		FWDataManager.initDataFromSource();
	}

	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub

	}

}
