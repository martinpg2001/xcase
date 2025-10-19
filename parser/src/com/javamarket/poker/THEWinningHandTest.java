package com.javamarket.poker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.javamarket.poker.CollectingHandAnalyzer.THEWinningHand;

class THEWinningHandTest {

	@Test
	void test() {
		THEWinningHand theLowestWinningHand = new THEWinningHand("2♦", "2♣");
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("2♦", "2♣")) == 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("2♥", "2♣")) == 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("2♠", "2♣")) == 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("2♥", "2♦")) == 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("2♠", "2♦")) == 0);		
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("2♠", "2♥")) == 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("3♦", "3♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("3♥", "3♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("3♠", "3♣")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("3♥", "3♦")) < 0);
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("3♠", "3♦")) < 0);		
		assertTrue(theLowestWinningHand.compareTo(new THEWinningHand("3♠", "3♥")) < 0);
		THEWinningHand theHighestWinningHand = new THEWinningHand("A♠", "A♥");
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("A♠", "A♥")) == 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("A♠", "A♦")) == 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("A♠", "A♣")) == 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("A♥", "A♦")) == 0);		
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("A♥", "A♣")) == 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("A♦", "A♣")) == 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("2♠", "2♥")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("2♠", "2♦")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("2♠", "2♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("2♥", "2♦")) > 0);		
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("2♥", "2♣")) > 0);
		assertTrue(theHighestWinningHand.compareTo(new THEWinningHand("2♣", "2♣")) > 0);
		THEWinningHand winningHand = new THEWinningHand("A♠", "K♥");
		assertTrue(winningHand.compareTo(new THEWinningHand("A♠", "A♥")) < 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("A♠", "A♦")) < 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("A♠", "A♣")) < 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("A♥", "A♦")) < 0);		
		assertTrue(winningHand.compareTo(new THEWinningHand("A♥", "A♣")) < 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("A♦", "A♣")) < 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("2♠", "2♥")) > 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("2♠", "2♦")) > 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("2♠", "2♣")) > 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("2♥", "2♦")) > 0);		
		assertTrue(winningHand.compareTo(new THEWinningHand("2♥", "2♣")) > 0);
		assertTrue(winningHand.compareTo(new THEWinningHand("2♣", "2♣")) > 0);
	}

}
