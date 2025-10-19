package com.javamarket.poker.logline;

public class FoldsLogLine extends LogLine {
	public String foldsPlayer;
	
	public FoldsLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		foldsPlayer = parseFoldsPlayerFromLogLine();
	}
	
	private String parseFoldsPlayerFromLogLine() {
//		System.out.println("starting parseFoldsPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("folds player name is " + name);		
		return name;
	}
}
