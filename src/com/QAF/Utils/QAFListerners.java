package com.QAF.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.annotations.ITestAnnotation;

import com.QAF.Driver.Service.QAFDriverService;
import com.QAF.annotations.QAFTest;

public class QAFListerners implements IExecutionListener, IAnnotationTransformer, ISuiteListener {

	private static final Logger log = Logger.getLogger(QAFListerners.class);

	@Override
	public void onExecutionStart() {
		log.info(">>>>>>>>>>>>> Starting Execution...");
		DataTransformer.initDataFromSource();
	}

	@Override
	public void onExecutionFinish() {
		log.info("Finished Execution!! <<<<<<<<<<<<");

	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (null != testMethod) {
			String unqKey = "";
			if (testMethod.isAnnotationPresent(QAFTest.class)) {
				unqKey = testMethod.getAnnotation(QAFTest.class).key();
				if (!unqKey.isEmpty()) {
					HashMap<String, String> map = DataTransformer.getData(unqKey);
					if (map != null) {
						if (map.get("Execution Flag").equalsIgnoreCase("NO")) {
							annotation.setEnabled(false);
						}
					}
				}

			}
			if (!annotation.getEnabled()) {
				log.info("Test Id-" + unqKey + " is excluded from execution");
			}

		}

	}

	@Override
	public void onStart(ISuite suite) {
		log.info("Starting Test Suite - " + suite.getName());
		boolean gridEnabled = Boolean.parseBoolean(QAFConfig.getProperty("Grid.Enabled"));

		// if Grid is enabled then don't start any local services for Browsers
		if (gridEnabled)
			log.info("Grid is enabled.");
		else {
			QAFDriverService.startChromeService();
			// QAFDriverService.startIEService();
			// QAFDriverService.startEdgeService();
		}

	}

	@Override
	public void onFinish(ISuite suite) {
		QAFDriverService.stopChromeService();

	}

}
