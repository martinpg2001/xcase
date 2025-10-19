package com.javamarket.poker;

import java.util.ArrayList;
import java.util.List;

public class Card implements Comparable<Card> {
    private Rank rank = Rank.ACE;
    private Suit suit = Suit.SPADE;

    public Card(String card) {
        System.out.println("card is " + card);
			String suitString = card.substring(1);
			if (card.startsWith("10")) {
				rank = Rank.TEN;
				suitString = card.substring(2);
			} else if (card.startsWith("2")) {
				rank = Rank.DUECE;
			} else if (card.startsWith("3")) {
				rank = Rank.THREE;
			} else if (card.startsWith("4")) {
				rank = Rank.FOUR;
			} else if (card.startsWith("5")) {
				rank = Rank.FIVE;
			} else if (card.startsWith("6")) {
				rank = Rank.SIX;
			} else if (card.startsWith("7")) {
				rank = Rank.SEVEN;
			} else if (card.startsWith("8")) {
				rank = Rank.EIGHT;
			} else if (card.startsWith("9")) {
				rank = Rank.NINE;
			} else if (card.startsWith("J")) {
				rank = Rank.JACK;
			} else if (card.startsWith("Q")) {
				rank = Rank.QUEEN;
			} else if (card.startsWith("K")) {
				rank =Rank.KING;
			} else if (card.startsWith("A")) {
				rank = Rank.ACE;
			} else {
				System.out.println("failed to parse card");
			}

			System.out.println("suitString is " + suitString);
			String replacedSuitString = suitString.replace(".", "").replace("\"", "");
			System.out.println("replacedSuitString is " + replacedSuitString);
			suit = createSuitFromString(replacedSuitString);
		}
		
	    private Card(Rank rank, Suit suit) {
	        this.rank = rank;
	        this.suit = suit;
	    }
		
	    static private final List<Card> protoDeck = new ArrayList<>();

	    // Initialize prototype deck
	    static {
	        for (Rank rank : Rank.values()) {
	            for (Suit suit : Suit.values()) {
	                protoDeck.add(new Card(rank, suit));
	            }
	        }
	    }

	    static public Card valueOf(int index) {
	        if (index < 0 || index > protoDeck.size()) {
	            throw new IllegalArgumentException("Invalid card; index = " + index);
	        }
	        
	        return protoDeck.get(index);
	    }

	    static public Card valueOf(long num) {
	        int index;
	        if (num == 0) {
	            index = 0;
	        } else {
	            index = Long.numberOfTrailingZeros(num);
	        }
	        
	        if (index < 0 || index > protoDeck.size()) {
	            throw new IllegalArgumentException("Invalid card; num = " + num);
	        }
	        
	        return valueOf(index);
	    }

	    static public Card valueOf(Rank rank, Suit suit) {
	        int index = (new Card(rank, suit)).intValue();
	        return valueOf(index);
	    }
	    
	    public int rankValue() {
	        return rank.getValue();
	    }

	    static public Card valueOf(String card) {
	        if (card.length() != 2) {
	            throw new IllegalArgumentException("Invalid card format");
	        }
	        
	        char rank = card.charAt(0);
	        char suit = card.charAt(1);
	        return valueOf(Rank.valueOf(rank), Suit.valueOf(suit));
	    }

	    static public List<Card> newDeck() {
	        return new ArrayList<>(protoDeck); // Return copy of prototype deck
	    }

		private Suit createSuitFromString(String suitString) {
			if (suitString.equals("♣")) {
				return Suit.CLUB;
			} else if (suitString.equals("♦")) {
				return Suit.DIAMOND;
			} else if (suitString.equals("♥")) {
				return Suit.HEART;
			} else if (suitString.equals("♠")) {
				return Suit.SPADE;
			} else {
				System.out.println("unrecognized suitString " + suitString);
				return null;
			}
		}

		@Override
		public int compareTo(Card o) {
			if (rank != o.rank) {
				return rank.getValue() - o.rank.getValue();
			} else {
				return 0;
//				return suit.compareTo(o.suit);
			}
		}

		@Override
		public String toString() {
			return rankToString() + suitToString();
		}

		private String suitToString() {
			if (suit.equals(Suit.CLUB)) {
				return "♣";
			} else if (suit.equals(Suit.DIAMOND)) {
				return "♦";
			} else if (suit.equals(Suit.HEART)) {
				return "♥";
			} else if (suit.equals(Suit.SPADE)) {
				return "♠";
			} else {
				return null;
			}
		}

		private String rankToString() {
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
		
	    /**
	     * Return number between 0 and 51 (inclusive), used as index value
	     */
	    public int intValue() {
	        return rank.ordinal() * Suit.size + suit.ordinal();
	    }
	    
	    /**
	     * Return a number that contains 1 bit, used as bitset value
	     */
	    public long longValue() {
	        return 1l << intValue();
	    }
		
	    static public enum Suit {
	        SPADE,
	        HEART,
	        DIAMOND,
	        CLUB;

	        static public final int size = Suit.values().length;

	        private final char letter;

	        Suit() {
	            this.letter = Character.toLowerCase(this.name().charAt(0));
	        }

	        public char getLetter() {
	            return this.letter;
	        }

	        static public Suit valueOf(char letter) {
	            for (Suit s : Suit.values()) {
	                if (s.letter == letter) {
	                    return s;
	                }
	            }
	            
	            return null;
	        }
	    }
	    
	    static public enum Rank {
	        ACE('A', 14),
	        KING('K', 13),
	        QUEEN('Q', 12),
	        JACK('J', 11),
	        TEN('T', 10),
	        NINE('9', 9),
	        EIGHT('8', 8),
	        SEVEN('7', 7),
	        SIX('6', 6),
	        FIVE('5', 5),
	        FOUR('4', 4),
	        THREE('3', 3),
	        DUECE('2', 2);

	        static public final int size = Rank.values().length;

	        private final char letter;
	        private final int value;

	        Rank(char letter, int value) {
	            this.letter = letter;
	            this.value = value;
	        }

	        public char getLetter() {
	            return this.letter;
	        }

	        public int getValue() {
	            return this.value;
	        }

	        static public Rank valueOf(char letter) {
	            for (Rank r : Rank.values()) {
	                if (r.letter == letter) {
	                    return r;
	                }
	            }
	            
	            return null;
	        }

	        static public Rank valueOf(int value) {
	            for (Rank r : Rank.values()) {
	                if (r.value == value) {
	                    return r;
	                }
	            }
	            
	            return null;
	        }
	    }
	}