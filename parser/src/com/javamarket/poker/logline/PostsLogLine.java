package com.javamarket.poker.logline;

public class PostsLogLine extends LogLine {
	String postsAmount;
	public String postsPlayer;
	
	public PostsLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		postsAmount = parsePostsAmountFromLogLine();
		postsPlayer = parsePostsPlayerFromLogLine();
	}
	
	private String parsePostsAmountFromLogLine() {
//		System.out.println("starting parsePostsAmountFromLogLine()");
		int blindOpen = message.indexOf("blind");
		int quotesClose = -1;
		if (message.indexOf("all in") < 0) {
			quotesClose = message.lastIndexOf("\"");
		} else {
			quotesClose = message.lastIndexOf("and go all in") - 1;
		}
		
		String amount = message.substring(blindOpen + 9, quotesClose);
//		System.out.println("posts player amount is " + amount);	
		return amount;
	}
	
	private String parsePostsPlayerFromLogLine() {
//		System.out.println("starting parsePostsPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("posts player name is " + name);		
		return name;
	}
}
