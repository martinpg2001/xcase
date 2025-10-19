package com.javamarket.poker.logline;

public class RaisesLogLine  extends LogLine {
	public String raisesPlayer;
	public String raisesAmount;
	
	public RaisesLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		raisesPlayer = parseRaisesPlayerFromLogLine();
	}
	
	private String parseRaisesPlayerFromLogLine() {
//		System.out.println("starting parseRaisesPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("raises player name is " + name);		
		return name;
	}
}
