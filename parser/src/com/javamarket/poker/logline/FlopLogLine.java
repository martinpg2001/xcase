package com.javamarket.poker.logline;

public class FlopLogLine extends LogLine implements ParseMessage {
	private String flop;
	
	public FlopLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		flop = parseFlopFromLogLine();
	}
	
	private String parseFlopFromLogLine() {
		return null;
	}
	
	public void parseMessage(String message) {
		
	}
}
