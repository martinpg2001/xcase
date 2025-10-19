package com.javamarket.poker.logline;

public class ChecksLogLine  extends LogLine {
	public String checksPlayer;
	
	public ChecksLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		checksPlayer = parseChecksPlayerFromLogLine();
	}
	
	private String parseChecksPlayerFromLogLine() {
//		System.out.println("starting parseChecksPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("checks player name is " + name);		
		return name;
	}
}
