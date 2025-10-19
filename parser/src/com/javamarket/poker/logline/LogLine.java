package com.javamarket.poker.logline;

import java.util.Date;

public class LogLine {
	public String message;
	public String dateTime;
	public String timestamp;
	
	public LogLine(String message, String dateTime, String timestamp) {
		this.message = message;
		this.dateTime = dateTime;
		this.timestamp = timestamp;
	}
	
	public static LogLine parseLineAsLogLine(String line) {
		if (line == null) {
			return null;
		}

		int indexOfLastQuote = line.lastIndexOf("\"");
		if (indexOfLastQuote < 0) {
			return null;
		}

		String message = line.substring(0, indexOfLastQuote + 1);
//	    LOGGER.debug("message is " + message);
		String lineSubString = line.substring(indexOfLastQuote + 2, line.length());
//		LOGGER.debug("lineSubString is " + lineSubString);
		String[] lineSubStringArray = lineSubString.split(",");
		String dateTime = lineSubStringArray[0];
		String timestamp = lineSubStringArray[1];
		return new LogLine(message, dateTime, timestamp);
	}
	
	public static LogLine getNewestLogLine() {
		return new LogLine("Newest log line", (new Date()).toString(),
				Long.toString(Long.MAX_VALUE));
	}
	
	public static LogLine getOldestLogLine() {
		return new LogLine("Oldest log line", (new Date(0)).toString(),
				Long.toString(Long.MIN_VALUE));
	}
	
	public String toString() {
		return message + ":" + dateTime + ":" + timestamp;
	}
	
	public String toString(char separator) {
		return message + separator + dateTime + separator + timestamp;
	}

	public boolean isOldest() {
		if (message != null && message.startsWith("Oldest")) {
			return true;
		} else {
		    return false;
		}
	}
}
