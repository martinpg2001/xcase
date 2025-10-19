package com.javamarket.poker.logline;

import java.util.ArrayList;
import java.util.Arrays;

import com.javamarket.poker.Card.Rank;

public class CollectedLogLine extends LogLine {
	public String collectedHand;
	public String collectedPlayer;
	public String collectedAmount;

	public CollectedLogLine(String message, String dateTime, String timestamp) {
		super(message, dateTime, timestamp);
		collectedHand = parseCollectedHandFromLogLine();
		collectedPlayer = parseCollectedPlayerFromLogLine();
		collectedAmount = parseCollectedAmountFromLogLine();
	}

	private String parseCollectedHandFromLogLine() {
//		System.out.println("starting parseCollectedHandFromLogLine()");
		try {
//			System.out.println("message is " + message);
			int collectedOpen = message.indexOf("(");
			int collectedClose = message.indexOf(")");
			String hand = message.substring(collectedOpen + 14, collectedClose);
//			System.out.println("collected hand is " + hand);
			String[] convertedHandArray = convertHand(hand);
			String convertedHand = Arrays.toString(convertedHandArray);
//			System.out.println("convertedHand is " + convertedHand);
			return convertedHand;
		} catch (Exception e) {
			return null;
		}
	}

	private String[] convertHand(String hand) {
		ArrayList<String> convertedCardArrayList = new ArrayList<String>();
		String[] cardArray = hand.split(",");
		for (String card : cardArray) {
//			System.out.println("card is " + card);
			String convertedCard = convertCard(card.trim());
			convertedCardArrayList.add(convertedCard.toString());
		}

		return convertedCardArrayList.toArray(new String[0]);
	}

	private String convertCard(String rawCard) {
//		System.out.println("card is " + rawCard);
		String suitString = rawCard.substring(1);
		Rank rank = null;
		if (rawCard.startsWith("10")) {
			rank = Rank.TEN;
			suitString = rawCard.substring(2);
		} else if (rawCard.startsWith("2")) {
			rank = Rank.DUECE;
		} else if (rawCard.startsWith("3")) {
			rank = Rank.THREE;
		} else if (rawCard.startsWith("4")) {
			rank = Rank.FOUR;
		} else if (rawCard.startsWith("5")) {
			rank = Rank.FIVE;
		} else if (rawCard.startsWith("6")) {
			rank = Rank.SIX;
		} else if (rawCard.startsWith("7")) {
			rank = Rank.SEVEN;
		} else if (rawCard.startsWith("8")) {
			rank = Rank.EIGHT;
		} else if (rawCard.startsWith("9")) {
			rank = Rank.NINE;
		} else if (rawCard.startsWith("J")) {
			rank = Rank.JACK;
		} else if (rawCard.startsWith("Q")) {
			rank = Rank.QUEEN;
		} else if (rawCard.startsWith("K")) {
			rank = Rank.KING;
		} else if (rawCard.startsWith("A")) {
			rank = Rank.ACE;
		} else {
			System.out.println("failed to parse card");
		}

		String convertedRank = convertRank(rank);
//		System.out.println("suitString is " + suitString);
		String convertedSuit = convertSuit(suitString);
//		System.out.println("convertedSuit is " + convertedSuit);
//		// suit = createSuitFromString(replacedSuitString);
		String convertedCard = convertedRank + convertedSuit;
//		System.out.println("convertedCard is " + convertedCard);
		return convertedCard;
	}

	private String convertRank(Rank rank) {
		if (rank.getValue() == 2) {
			return "2";
		} else if (rank.getValue() == 3) {
			return "3";
		} else if (rank.getValue() == 4) {
			return "4";
		} else if (rank.getValue() == 5) {
			return "5";
		} else if (rank.getValue() == 6) {
			return "6";
		} else if (rank.getValue() == 7) {
			return "7";
		} else if (rank.getValue() == 8) {
			return "8";
		} else if (rank.getValue() == 9) {
			return "9";
		} else if (rank.getValue() == 10) {
			return "10";
		} else if (rank.getValue() == 11) {
			return "J";
		} else if (rank.getValue() == 12) {
			return "Q";
		} else if (rank.getValue() == 13) {
			return "K";
		} else if (rank.getValue() == 14) {
			return "A";
		} else {
			return "0";
		}
	}

	private String convertSuit(String suitString) {
		switch (suitString) {
		case "♦":
			return "D";
		case "♥":
			return "H";
		case "♣":
			return "C";
		case "♠":
			return "S";
		default:
			return "X";
		}
	}

	private String parseCollectedAmountFromLogLine() {
//		System.out.println("starting parseCollectedAmountFromLogLine()");
		int collectedIndex = message.indexOf("collected");
		int fromIndex = message.indexOf("from");
		String amount = message.substring(collectedIndex + 10, fromIndex).trim();
//		System.out.println("collected player amount is " + amount);
		return amount;
	}

	private String parseCollectedPlayerFromLogLine() {
//		System.out.println("starting parseAllInPlayerFromLogLine()");
		String[] splitAt = message.split("@");
		String name = splitAt[0].trim().substring(3);
//		System.out.println("collected player name is " + name);
		return name;
	}
}
