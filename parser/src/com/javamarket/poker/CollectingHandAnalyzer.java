package com.javamarket.poker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class CollectingHandAnalyzer {

	private static final String COMMA_DELIMITER = ",";
	private static final String ZERO = "0";
	/*
	 * Represents the list of players. It is used to store the player name for each
	 * player.
	 */
	private static HashMap<Integer, String> playerHashMap = new HashMap<Integer, String>();
	/* Represents the game type: valid values are THE and OHL. */
	private static String gameType = "THE";

	private static String collectingHandDirectory = "D:\\temp\\pokernow";

	private static ArrayList<OHLWinningHand> ohlWinningHandArrayList = new ArrayList<OHLWinningHand>();

	private static ArrayList<THEWinningHand> theWinningHandArrayList = new ArrayList<THEWinningHand>();

	public static void main(String[] args) {
		try {
			combineFiles("THE");
			combineFiles("OHL");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void combineFiles(String gameType) {
		try {
			ArrayList<WinningHand> winningHandArrayList = new ArrayList<WinningHand>();
			File directoryPath = new File(collectingHandDirectory);
			File filesList[] = directoryPath.listFiles();
			for (File file : filesList) {
				String fileName = file.getName();
				if (fileName.startsWith(gameType)) {
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String line;
						while ((line = br.readLine()) != null) {
							if (line.startsWith("collectingHand is ")) {
								String winningHand = line.substring(18);
								String[] winningCardsArray = winningHand.split(COMMA_DELIMITER);
							    if ("THE".equals(gameType)) {
							    	winningHandArrayList.add(new THEWinningHand(winningCardsArray[0], winningCardsArray[1]));
							    } else if ("OHL".equals(gameType)) {
							    	winningHandArrayList.add(new OHLWinningHand(winningCardsArray[0],
											winningCardsArray[1], winningCardsArray[2], winningCardsArray[3]));
							    }
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			System.out.println("winningHandArrayList size: " + winningHandArrayList.size());
			Collections.sort(winningHandArrayList, Collections.reverseOrder());
			for (WinningHand winningHand : winningHandArrayList) {
				System.out.println(winningHand);
			}

			String winningHandOutputFileName = "D:\\temp\\" + gameType + "_WinningHand_"
					+ Instant.now().toEpochMilli() + ".txt";
			System.out.println("winningHandOutputFileName is " + winningHandOutputFileName);
			File outputFile = new File(winningHandOutputFileName);
			if (outputFile.createNewFile()) {
				System.out.println("File created");
			} else {
				System.out.println("File already exists");
			}

			FileWriter winningHandFileWriter = new FileWriter(winningHandOutputFileName);
			BufferedWriter winningHandFileBufferedWriter = new BufferedWriter(winningHandFileWriter);
			for (WinningHand winningHand : winningHandArrayList) {
				winningHandFileBufferedWriter.write(winningHand + System.lineSeparator());
			}
			
			winningHandFileBufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static abstract class WinningHand implements Comparable<WinningHand> {
		ArrayList<Card> cardArrayList;

		/**
		 * We compare two hands by comparing the first cards first (by rank only), and
		 * if these are equal, then compare the second cards, and if these are equal,
		 * then compare the third cards, and so on.
		 */
		public int compareTo(WinningHand o) {
			for (int i = 0; i < cardArrayList.size(); i++) {
				if (cardArrayList.get(i).compareTo(o.cardArrayList.get(i)) != 0) {
					return cardArrayList.get(i).compareTo(o.cardArrayList.get(i));
				}
			}

			return 0;
		}
	}

	/**
	 * The OHL winning hand comprises four cards. By convention, for grouping, the
	 * constructor will order the cards in the card ArrayList.
	 * 
	 * @author martinpg
	 *
	 */
	static class OHLWinningHand extends WinningHand {

		public OHLWinningHand(String firstCardString, String secondCardString, String thirdCardString,
				String fourthCardString) {
			cardArrayList = new ArrayList<Card>();
			cardArrayList.add(new Card(firstCardString.trim()));
			cardArrayList.add(new Card(secondCardString.trim()));
			cardArrayList.add(new Card(thirdCardString.trim()));
			cardArrayList.add(new Card(fourthCardString.trim()));
			Collections.sort(cardArrayList, Collections.reverseOrder());
		}

		@Override
		public String toString() {
			return cardArrayList.get(0) + ":" + cardArrayList.get(1) + ":" + cardArrayList.get(2) + ":"
					+ cardArrayList.get(3);
		}
	}

	/**
	 * The THE winning hand comprises two cards. By convention, for grouping, the
	 * constructor will put the higher of the two cards in the firstCard field, and
	 * the lower of the two cards in the secondCard field.
	 * 
	 * @author martinpg
	 *
	 */
	static class THEWinningHand extends WinningHand {

		public THEWinningHand(String firstCardString, String secondCardString) {
			cardArrayList = new ArrayList<Card>();
			cardArrayList.add(new Card(firstCardString.trim()));
			cardArrayList.add(new Card(secondCardString.trim()));
			Collections.sort(cardArrayList, Collections.reverseOrder());
		}

		@Override
		public String toString() {
			return cardArrayList.get(0) + ":" + cardArrayList.get(1);
		}
	}
}
