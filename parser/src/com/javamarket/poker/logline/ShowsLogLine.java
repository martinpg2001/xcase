package com.javamarket.poker.logline;

public class ShowsLogLine  extends LogLine {
	public String showsPlayer;
	
	public ShowsLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		showsPlayer = parseShowsPlayerFromLogLine();
	}
	
	private String parseShowsPlayerFromLogLine() {
//		System.out.println("starting parseShowsPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("shows player name is " + name);		
		return name;
	}
}
