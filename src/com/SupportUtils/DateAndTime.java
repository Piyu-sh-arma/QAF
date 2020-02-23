package com.SupportUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateAndTime {
	public static String formatAsString(Date date, String format) {
		if (format.isEmpty()) {
			format = "YYYY-MM-dd_HH-mm-ss";
		}
		return new SimpleDateFormat(format).format(date);
	}

	public static String formatAsString(Instant instant, String format) {
		if (format.isEmpty()) {
			format = "YYYY-MM-dd_HH-mm-ss";
		}
		return new SimpleDateFormat(format).format(Date.from(instant));
	}

	public static String getDuation(Long number, TimeUnit unit) {

		int hours = 0, mins = 0, sec = 0, days = 0;

		switch (unit) {
		case MILLISECONDS:
			sec = (int) (number / 1000);
			mins = sec / 60;
			sec = sec % 60;
			hours = mins / 60;
			mins = mins % 60;
			break;
		case SECONDS:
			mins = (int) (number / 60);
			sec = (int) (number % 60);
			hours = mins / 60;
			mins = mins % 60;
			break;

		case MINUTES:
			hours = (int) (number / 60);
			mins = (int) (number % 60);
			break;

		default:
			break;
		}
		;

		if (hours > 24) {
			days = hours / 24;
			hours = hours % 24;
		}

		if (days > 0) {
			return String.format("%02d Days, %02d Hrs, %02d Mins, %02d Secs", days, hours, mins, sec);

		} else {
			return String.format("%02d Hrs, %02d Mins, %02d Secs", hours, mins, sec);

		}

	}

}
