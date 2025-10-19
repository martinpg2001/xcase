package com.javamarket.poker.logline;

public class CallsLogLine extends LogLine {
	public String callsPlayer;
	
	public CallsLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		callsPlayer = parseCallsPlayerFromLogLine();
	}
	
	private String parseCallsPlayerFromLogLine() {
//		System.out.println("starting parseCallsPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("calls player name is " + name);		
		return name;
	}
}
