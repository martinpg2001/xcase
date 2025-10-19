package com.javamarket.poker.logline;

import java.util.HashMap;

public class AllInLogLine extends LogLine implements ParseMessage {
	String allInPlayer;
	
	public AllInLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		allInPlayer = parseAllInPlayerFromLogLine();
	}
	
	public void parseMessage(String message) {
		String[] splitAt = message.split("@");
		allInPlayer = splitAt[0].trim().substring(3);
//		System.out.println("all in player name is " + allInPlayer);		
	}

	private String parseAllInPlayerFromLogLine() {
//		System.out.println("starting parseAllInPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("all in player name is " + name);		
		return name;
	}

}
