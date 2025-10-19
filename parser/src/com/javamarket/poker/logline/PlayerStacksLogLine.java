package com.javamarket.poker.logline;

import java.util.HashMap;

public class PlayerStacksLogLine extends LogLine implements ParseMessage {
	private HashMap<String, String> playerStackHashMap;

	public PlayerStacksLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		parseMessage(message);
	}

	public void parseMessage(String message) {
		HashMap<String, String> playerStackHashMap = new HashMap<String, String>();
//		System.out.println("line is " + line);
		String strippedValue = message.replace("\"", "").substring("Player stacks:".length());
//		System.out.println("strippedValue is " + strippedValue);
		/*
		 * Split the line at the | symbol to break up into the separate player fragments
		 */
		String[] playerStackArray = strippedValue.split("\\|");
//		System.out.println("playerStackArray is " + Arrays.toString(playerStackArray));
		/* Each fragment is of the form name @ (stake) so break it at the @ symbol */
		for (String playerStackData : playerStackArray) {
//			System.out.println("player playerStackData is " + playerStackData);
			String[] playerDataArray = playerStackData.split("@");
			/* Extract the first name fragment */
//			String name = playerDataArray[0].split(" ")[2];
			String name = extractNameFromPlayerDataArray(playerDataArray[0]);
//			System.out.println("player name is " + name);
			/* Extract the first stack fragment and remove the brackets */
			String stack = playerDataArray[1].split(" ")[2];
			stack = stack.replace("(", "").replace(")", "");
			playerStackHashMap.put(name, stack);
//			if (!playerHashMap.containsValue(name)) {
//				playerHashMap.put(new Integer(playerHashMap.size()), name);
//			}
		}

//		return playerStackHashMap;
	}
	
	public HashMap<String, String> getPlayerStackHashMap() {
		return playerStackHashMap;
	}

	private String extractNameFromPlayerDataArray(String playerData) {
//		System.out.println("player playerData is " + playerData);
		String name = playerData.split(" ")[2];
//		System.out.println("player name is " + name);
		return name;
	}

}
