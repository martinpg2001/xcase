package com.javamarket.poker.logline;

public class BetsLogLine extends LogLine {
	public String betsPlayer;
	public String betsAmount;
	
	public BetsLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		betsPlayer = parseBetsPlayerFromLogLine();
	}
	
	private String parseBetsPlayerFromLogLine() {
//		System.out.println("starting parseBetsPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("bets player name is " + name);		
		return name;
	}
}
