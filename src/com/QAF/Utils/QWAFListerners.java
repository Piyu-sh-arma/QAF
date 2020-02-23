package com.QAF.Utils;

import org.testng.IExecutionListener;

public class QWAFListerners implements IExecutionListener {

	@Override
	public void onExecutionStart() {
		FWDataManager.initDataFromSource();
	}

	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub

	}
}
