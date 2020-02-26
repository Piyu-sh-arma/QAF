package com.QAF.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IExecutionListener;
import org.testng.annotations.ITestAnnotation;

import com.QAF.annotations.QAFInput;

public class QAFListerners implements IExecutionListener, IAnnotationTransformer {
	
	private static final Logger log = Logger.getLogger(QAFListerners.class);

	@Override
	public void onExecutionStart() {
		log.info("<---- Starting TestNG Execution ---->");		
		DataManager.initDataFromSource();
	}

	@Override
	public void onExecutionFinish() {
		// TODO Auto-generated method stub

	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		if (null != testMethod) {
			String unqKey = testMethod.getName();
			if (testMethod.isAnnotationPresent(QAFInput.class)) {
				unqKey = testMethod.getAnnotation(QAFInput.class).key();
				if (!unqKey.isEmpty()) {
					HashMap<String, String> map = DataManager.getData(unqKey);
					if (map != null) {
						if (map.get("Execution Flag").equalsIgnoreCase("NO")) {
							annotation.setEnabled(false);
						}
					}
				}

			}
			if(!annotation.getEnabled()) {
				log.info("Test Id-" + unqKey + " is excluded from execution");
			}

		}

	}

}
