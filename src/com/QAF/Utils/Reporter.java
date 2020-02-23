package com.QAF.Utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.SupportUtils.DateAndTime;
import com.SupportUtils.FilesUtil;
import com.SupportUtils.TestRunInfo;
import com.SupportUtils.TestRunStatus;

public class Reporter {
	String name;
	private static final Logger log = Logger.getLogger(Reporter.class);
	public static ConcurrentHashMap<String, TestRunInfo> runs;
	static {
		runs = new ConcurrentHashMap<String, TestRunInfo>();

	}

	public static void initialzeReport(Method m) {
		String sourceFile = ProjectConfig.getProperty("TestResult.SourceHTML");
		if (FilesUtil.fileExist(sourceFile)) {
			try {
				String resultFileHeaderPart1 = FilesUtil.readFile(sourceFile);
				HashMap<String, String> testDetails = FWDataManager.getTestData(m.getName());
				runs.put(m.getName(), new TestRunInfo());

				String testReportFilePath = ProjectConfig.getProperty("TestResult.Directory") + "/" + m.getName() + "_"
						+ ProjectConfig.getProperty("TestResult.ResultFileDatePostfix") + ".html";
				Instant start = Instant.now();
				runs.get(m.getName()).resultFilePath = testReportFilePath;
				runs.get(m.getName()).startStamp = start;
				runs.get(m.getName()).testStatus = TestRunStatus.In_Progress;

				String resultFileHeaderPart2 = "\n<script>\ndocument.getElementById('testName').innerText = '"
						+ testDetails.get("Test Name") + "'\n" + "document.getElementById('testId').innerText = '"
						+ m.getName() + "'\n" + "document.getElementById('status').innerText = 'In Progress'\n"
						+ "document.getElementById('MachineName').innerText = '"
						+ InetAddress.getLocalHost().getHostName() + "'\n"
						+ "document.getElementById('ALMID').innerText = '" + testDetails.get("ALM ID") + "'\n"
						+ "document.getElementById('Starttime').innerText = '"
						+ DateAndTime.formatAsString(start, "YYYY-MM-dd hh:mm:ss a") + "'\n</script>\n"
						+ "<table id='stepsSummary'>\n" + "<thead>\n<th>Step Name</th>\n"
						+ "<th>Description</th>\n<th>Result</th>\n<th>Time</th>\n</thead>\n<tbody id='steps'>\n";

				log.info(resultFileHeaderPart2);
				FilesUtil.writeToFile(sourceFile, resultFileHeaderPart1 + resultFileHeaderPart2);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public static void closeReport(Method m) {
		try {
			TestRunInfo curTestRunInfo = runs.get(m.getName());
			if (curTestRunInfo == null) {
				return;
			}
			curTestRunInfo.endStamp = Instant.now();
			long timeTaken = Duration.between(curTestRunInfo.startStamp, curTestRunInfo.endStamp).get(ChronoUnit.SECONDS);

			String tstStatus;

			if (curTestRunInfo.testStatus == TestRunStatus.Failed) {
				tstStatus = "Failed";
			} else if (curTestRunInfo.totalStepCount == 0) {
				tstStatus = "Not Completed";
				curTestRunInfo.testStatus = TestRunStatus.Not_Completed;
			} else {
				tstStatus = "Passed";
				curTestRunInfo.testStatus = TestRunStatus.Passed;
			}

			int PassPercent = 0, FailPercent = 0;
			if (curTestRunInfo.passCount > 0 || curTestRunInfo.failCount > 0) {
				PassPercent = curTestRunInfo.passCount * 100 / (curTestRunInfo.passCount + curTestRunInfo.failCount);
				FailPercent = 100 - PassPercent;
			}

			String resultFileFooter = "</tbody>\n</table>\n<script>\n"
					+ "document.getElementById('PassSteps').innerText = '" + PassPercent + "'\n"
					+ "document.getElementById('FailSteps').innerText = '" + FailPercent + "'\n"
					+ "document.getElementById('Endtime').innerText = '"
					+ DateAndTime.formatAsString(curTestRunInfo.endStamp, "YYYY-MM-dd hh:mm:ss a") + "'\n"
					+ "document.getElementById('ExecutionTime').innerText = '"
					+ DateAndTime.getDuation(timeTaken, TimeUnit.SECONDS) + "'\n"
					+ "document.getElementById('status').innerText = '"+tstStatus+"'\n" + "<script>\n</body>\n</html>";
			System.out.println(resultFileFooter);
			FilesUtil.writeToFile(curTestRunInfo.resultFilePath, resultFileFooter, true);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void main(String[] args) {
		System.out.println();
	}
}
