package com.qaf.utils;

import com.supportUtils.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Reporter {
	String name;
	private static final Logger log = Logger.getLogger(Reporter.class);
	public static ConcurrentHashMap<String, TestRunInfo> runs;
	static {
		runs = new ConcurrentHashMap<>();

	}

	/************************************************
	 * Purpose - Initialize test execution report
	 * @Author - Piyush Sharma
	 *************************************************/
	public static void initializeReport() {
		String sourceFile = QAFConfig.getProperty("TestResult.SourceHTML");
		if (FilesUtil.fileExist(sourceFile)) {
			try {
				String tstRunKey = Thread.currentThread().getName();
				String tstKey = tstRunKey.split("~_")[1];
				log.info("Initiating report for test " + tstKey);
				String resultFileHeaderPart1 = FilesUtil.readFile(sourceFile);
				HashMap<String, String> testDetails = DataTransformer.getData(tstKey);
				runs.put(tstRunKey, new TestRunInfo());
				String testResultDir = QAFConfig.getProperty("TestResult.Directory");
				if (!FilesUtil.folderExist(testResultDir))
					testResultDir = "./htmlResults";

				String testReportFilePath = testResultDir + "/" + tstKey + "_"
						+ DateAndTime.formatAsString(new Date(), QAFConfig.getProperty("TestResult.ResultFileDatePostfix")) + "_" + RandomNumber.getInt(100) + ".html";
				Instant start = Instant.now();
				runs.get(tstRunKey).resultFilePath = testReportFilePath;
				runs.get(tstRunKey).startStamp = start;
				runs.get(tstRunKey).testStatus = TestRunStatus.In_Progress;

				String resultFileHeaderPart2 = "\n<script>\ndocument.getElementById('testName').innerText = '" + testDetails.get("Test Name") + "'\n"
						+ "document.getElementById('TestKey').innerText = '" + tstKey + "'\n" + "document.getElementById('status').innerText = 'In Progress'\n"
						+ "document.getElementById('MachineName').innerText = '" + InetAddress.getLocalHost().getHostName() + "'\n"
						+ "document.getElementById('ALMID').innerText = '" + testDetails.get("ALM ID") + "'\n" + "document.getElementById('Starttime').innerText = '"
						+ DateAndTime.formatAsString(start, "YYYY-MM-dd hh:mm:ss a") + "'\n</script>\n" + "<table id='stepsSummary'>\n" + "<thead><tr>\n<th>Step</th>\n"
						+ "<th>Details</th>\n<th>Result</th>\n<th>Time</th>\n</tr></thead>\n<tbody id='steps'>\n";

				FilesUtil.writeToFile(testReportFilePath, resultFileHeaderPart1 + resultFileHeaderPart2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/************************************************
	 * Purpose - Add step in test report
	 * @Author - Piyush Sharma
	 *************************************************/
	public static void reportStep(String step, String details, StepStatus status) {
		String stepData = "";
		TestRunInfo curTestRunInfo = runs.get(Thread.currentThread().getName());
		if (curTestRunInfo == null) {
			return;
		}
		curTestRunInfo.totalStepCount++;
		switch (status) {
		case PASS:
			stepData = "<tr class='Pass'><td>" + step + "</td><td>" + details
					+ "</td><td><span style=\"color: transparent;  text-shadow: 0 0 0 green; \" >&#10004;</span></td><td>"
					+ DateAndTime.formatAsString(new Date(), "hh:mm:ss") + "</td></tr>";
			curTestRunInfo.passCount++;
			break;

		case FAIL:
			stepData = "<tr class='Fail'><td>" + step + "</td><td>" + details + "</td><td>&#10060;</td><td>" + DateAndTime.formatAsString(new Date(), "hh:mm:ss")
					+ "</td></tr>";
			curTestRunInfo.failCount++;
			curTestRunInfo.testStatus = TestRunStatus.Failed;
			break;

		case INFO:
			stepData = "<tr class='Info'><td>" + step + "</td><td>" + details + "</td><td>&#8505;</td><td>" + DateAndTime.formatAsString(new Date(), "hh:mm:ss")
					+ "</td></tr>";
			break;

		default:
			break;
		}

		try {
			FilesUtil.writeToFile(curTestRunInfo.resultFilePath, stepData, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/************************************************
	 * Purpose - Close test execution report
	 * @Author - Piyush Sharma
	 *************************************************/
	public static void closeReport() {
		try {
			String tstRunKey = Thread.currentThread().getName();
			String tstKey = tstRunKey.split("~_")[1];
			log.info("Closing report for test " + tstKey);
			TestRunInfo curTestRunInfo = runs.get(tstRunKey);
			if (curTestRunInfo == null) {
				return;
			}
			curTestRunInfo.endStamp = Instant.now();
			long timeTaken = Duration.between(curTestRunInfo.startStamp, curTestRunInfo.endStamp).get(ChronoUnit.SECONDS);

			String tstStatus;

			if (curTestRunInfo.failCount > 0) {
				tstStatus = "Failed";
			} else if (curTestRunInfo.totalStepCount == 0) {
				tstStatus = "Not Completed";
			} else {
				tstStatus = "Passed";
			}

			int PassPercent = 0, FailPercent = 0;
			if (curTestRunInfo.passCount > 0 || curTestRunInfo.failCount > 0) {
				PassPercent = curTestRunInfo.passCount * 100 / (curTestRunInfo.passCount + curTestRunInfo.failCount);
				FailPercent = 100 - PassPercent;
			}

			String resultFileFooter = "</tbody>\n</table>\n<script>\n" + "document.getElementById('PassSteps').innerText = '" + PassPercent + "'\n"
					+ "document.getElementById('FailSteps').innerText = '" + FailPercent + "'\n" + "document.getElementById('Endtime').innerText = '"
					+ DateAndTime.formatAsString(curTestRunInfo.endStamp, "YYYY-MM-dd hh:mm:ss a") + "'\n" + "document.getElementById('ExecutionTime').innerText = '"
					+ DateAndTime.getDuration(timeTaken, TimeUnit.SECONDS) + "'\n" + "document.getElementById('status').innerText = '" + tstStatus + "'\n"
					+ "</script>\n</body>\n</html>";
			//			System.out.println(resultFileFooter);
			FilesUtil.writeToFile(curTestRunInfo.resultFilePath, resultFileFooter, true);
			log.info("Closed report - " + curTestRunInfo.resultFilePath);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void main(String[] args) {
		reportStep("sadfasdf", "asdfasdfas", StepStatus.PASS);
	}

	/************************************************
	 * Purpose - Check if test has failed
	 * @Author - Piyush Sharma
	 *************************************************/
	public static boolean hasTestFailed() {
		TestRunInfo curTestRunInfo = runs.get(Thread.currentThread().getName());
        return curTestRunInfo == null || curTestRunInfo.testStatus == TestRunStatus.Failed;

	}

}
