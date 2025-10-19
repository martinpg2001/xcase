package com.javamarket.poker.logline;

public class ReturnedLogLine  extends LogLine {
	public String returnedPlayer;
	public String returnedAmount;
	
	public ReturnedLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		returnedAmount = parseReturnedAmountFromLogLine();
		returnedPlayer = parseReturnedPlayerFromLogLine();
	}
	
	private String parseReturnedPlayerFromLogLine() {
//		System.out.println("starting parseReturnedPlayerFromLogLine()");
		int returnedOpen = message.indexOf("returned");
		int atClose = message.indexOf("@");
		String name = message.substring(returnedOpen + 14, atClose - 1);
//		System.out.println("returned player name is " + name);	
		return name;
	}
	
	private String parseReturnedAmountFromLogLine() {
//		System.out.println("starting parseReturnedAmountFromLogLine()");
		int betOpen = message.indexOf("bet");
		int returnedClose = message.indexOf("returned");
		String amount = message.substring(betOpen + 7, returnedClose - 1);
//		System.out.println("returned player amount is " + amount);	
		return amount;
	}
}