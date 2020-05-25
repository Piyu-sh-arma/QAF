package com.SupportUtils;

import java.time.Instant;

public class TestRunInfo {

	public String resultFilePath,Browser;
	public int passCount = 0, failCount = 0, totalStepCount = 0;
	public TestRunStatus testStatus;
	public Instant startStamp, endStamp;

}
