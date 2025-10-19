package com.javamarket.poker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.javamarket.poker.CollectingHandAnalyzer.OHLWinningHand;
import com.javamarket.poker.CollectingHandAnalyzer.THEWinningHand;

class OHLWinningHandTest {

	@Test
	void test() {
		OHLWinningHand theLowestWinningHand = new OHLWinningHand("2♠", "2♥", "2♦", "2♣");
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "2♦", "2♣")) == 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "2♦", "3♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "3♦", "2♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "3♥", "2♦", "2♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("3♠", "2♥", "2♦", "2♣")) < 0);		
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "2♦", "4♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "4♦", "2♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "4♥", "2♦", "2♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("4♠", "2♥", "2♦", "2♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "2♦", "5♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "2♥", "5♦", "2♣")) < 0);		
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("2♠", "5♥", "2♦", "2♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new OHLWinningHand("5♠", "2♥", "2♦", "2♣")) < 0);
		OHLWinningHand theHighestWinningHand = new OHLWinningHand("A♠", "A♥", "A♦", "A♣");
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "A♣")) == 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "K♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "Q♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "J♣")) > 0);		
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "10♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "9♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "8♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "7♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "6♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "5♣")) > 0);		
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "4♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "3♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new OHLWinningHand("A♠", "A♥", "A♦", "2♣")) > 0);
	}

}
