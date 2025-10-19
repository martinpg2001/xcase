package com.javamarket.poker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

	@Test
	void test() {
		Card twoClubCard = new Card("2♣");
		assertTrue(twoClubCard.compareTo(new Card("2♣")) == 0);
		assertTrue(twoClubCard.compareTo(new Card("2♦")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("2♥")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("2♠")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("3♣")) < 0);		
		assertTrue(twoClubCard.compareTo(new Card("3♦")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("3♥")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("3♠")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("A♣")) < 0);		
		assertTrue(twoClubCard.compareTo(new Card("A♦")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("A♥")) < 0);
		assertTrue(twoClubCard.compareTo(new Card("A♠")) < 0);
		Card aceSpadeCard = new Card("A♠");
		assertTrue(aceSpadeCard.compareTo(new Card("2♦")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("2♥")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("2♠")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("3♣")) > 0);		
		assertTrue(aceSpadeCard.compareTo(new Card("3♦")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("3♥")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("3♠")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("A♣")) > 0);		
		assertTrue(aceSpadeCard.compareTo(new Card("A♦")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("A♥")) > 0);
		assertTrue(aceSpadeCard.compareTo(new Card("A♠")) == 0);
	}

}
