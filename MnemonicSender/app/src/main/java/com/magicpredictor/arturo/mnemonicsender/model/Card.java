package com.magicpredictor.arturo.mnemonicsender.model;

import org.apache.http.entity.StringEntity;

public class Card {
	public enum Suit {
	    CLUBS, HEARTS, SPADES, DIAMONDS
	}

	public enum Value {
	    ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
	    TEN, JACK, QUEEN, KING
	}
	
	public Suit suit;
	public Value value;
	
	public Card(Suit suit, Value value) {
		super();
		this.suit = suit;
		this.value = value;
	}
	
	public Card nextCardInSiStebbinsOrder() {
		Suit nextSuit = nextSuitInSiStebbinsOrder();
		Value nextValue = nexValueInSiStebbinsOrder();
		
		return new Card(nextSuit, nextValue);
	}

	public Card nextCardInMnemonicOrder() {
		Suit nextSuit = null;
		Value nextValue = null;

		switch (suit) {
			case CLUBS:
				switch (value) {
					case ACE:
						nextSuit = Suit.CLUBS;
						nextValue = Value.NINE;
						break;
					case TWO:
						nextSuit = Suit.HEARTS;
						nextValue = Value.THREE;
						break;
					case THREE:
						nextSuit = Suit.HEARTS;
						nextValue = Value.FOUR;
						break;
					case FOUR:
						nextSuit = Suit.HEARTS;
						nextValue = Value.TWO;
						break;
					case FIVE:
						nextSuit = Suit.SPADES;
						nextValue = Value.KING;
						break;
					case SIX:
						nextSuit = Suit.HEARTS;
						nextValue = Value.ACE;
						break;
					case SEVEN:
						nextSuit = Suit.SPADES;
						nextValue = Value.QUEEN;
						break;
					case EIGHT:
						nextSuit = Suit.SPADES;
						nextValue = Value.TEN;
						break;
					case NINE:
						nextSuit = Suit.SPADES;
						nextValue = Value.JACK;
						break;
					case TEN:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.FIVE;
						break;
					case JACK:
						nextSuit = Suit.SPADES;
						nextValue = Value.SEVEN;
						break;
					case QUEEN:
						nextSuit = Suit.HEARTS;
						nextValue = Value.EIGHT;
						break;
					case KING:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.TWO;
						break;
				}
				break;
			case HEARTS:
				switch (value) {
					case ACE:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.NINE;
						break;
					case TWO:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.SEVEN;
						break;
					case THREE:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.EIGHT;
						break;
					case FOUR:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.SIX;
						break;
					case FIVE:
						nextSuit = Suit.SPADES;
						nextValue = Value.NINE;
						break;
					case SIX:
						nextSuit = Suit.CLUBS;
						nextValue = Value.TEN;
						break;
					case SEVEN:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.FOUR;
						break;
					case EIGHT:
						nextSuit = Suit.SPADES;
						nextValue = Value.SIX;
						break;
					case NINE:
						nextSuit = Suit.CLUBS;
						nextValue = Value.KING;
						break;
					case TEN:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.ACE;
						break;
					case JACK:
						nextSuit = Suit.SPADES;
						nextValue = Value.THREE;
						break;
					case QUEEN:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.THREE;
						break;
					case KING:
						nextSuit = Suit.CLUBS;
						nextValue = Value.JACK;
						break;
				}
				break;
			case SPADES:
				switch (value) {
					case ACE:
						nextSuit = Suit.HEARTS;
						nextValue = Value.FIVE;
						break;
					case TWO:
						nextSuit = Suit.HEARTS;
						nextValue = Value.QUEEN;
						break;
					case THREE:
						nextSuit = Suit.SPADES;
						nextValue = Value.EIGHT;
						break;
					case FOUR:
						nextSuit = Suit.HEARTS;
						nextValue = Value.SEVEN;
						break;
					case FIVE:
						nextSuit = Suit.HEARTS;
						nextValue = Value.NINE;
						break;
					case SIX:
						nextSuit = Suit.SPADES;
						nextValue = Value.FIVE;
						break;
					case SEVEN:
						nextSuit = Suit.HEARTS;
						nextValue = Value.TEN;
						break;
					case EIGHT:
						nextSuit = Suit.HEARTS;
						nextValue = Value.SIX;
						break;
					case NINE:
						nextSuit = Suit.SPADES;
						nextValue = Value.TWO;
						break;
					case TEN:
						nextSuit = Suit.HEARTS;
						nextValue = Value.KING;
						break;
					case JACK:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.QUEEN;
						break;
					case QUEEN:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.TEN;
						break;
					case KING:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.JACK;
						break;
				}
				break;
			case DIAMONDS:
				switch (value) {
					case ACE:
						nextSuit = Suit.SPADES;
						nextValue = Value.FOUR;
						break;
					case TWO:
						nextSuit = Suit.HEARTS;
						nextValue = Value.JACK;
						break;
					case THREE:
						nextSuit = Suit.CLUBS;
						nextValue = Value.QUEEN;
						break;
					case FOUR:
						nextSuit = Suit.CLUBS;
						nextValue = Value.ACE;
						break;
					case FIVE:
						nextSuit = Suit.DIAMONDS;
						nextValue = Value.KING;
						break;
					case SIX:
						nextSuit = Suit.SPADES;
						nextValue = Value.ACE;
						break;
					case SEVEN:
						nextSuit = Suit.CLUBS;
						nextValue = Value.THREE;
						break;
					case EIGHT:
						nextSuit = Suit.CLUBS;
						nextValue = Value.FIVE;
						break;
					case NINE:
						nextSuit = Suit.CLUBS;
						nextValue = Value.FOUR;
						break;
					case TEN:
						nextSuit = Suit.CLUBS;
						nextValue = Value.SIX;
						break;
					case JACK:
						nextSuit = Suit.CLUBS;
						nextValue = Value.EIGHT;
						break;
					case QUEEN:
						nextSuit = Suit.CLUBS;
						nextValue = Value.SEVEN;
						break;
					case KING:
						nextSuit = Suit.CLUBS;
						nextValue = Value.TWO;
						break;
				}
				break;
		}

		return new Card(nextSuit, nextValue);
	}

	private Suit nextSuitInSiStebbinsOrder() {
		Suit nextSuit;
		switch (this.suit) {
		case CLUBS:
			nextSuit = Suit.HEARTS;
			break;
		case HEARTS:
			nextSuit = Suit.SPADES;
			break;
		case SPADES:
			nextSuit = Suit.DIAMONDS;
			break;
		case DIAMONDS:
			nextSuit = Suit.CLUBS;
			break;
		default:
			nextSuit = null;
			break;
		}
		
		return nextSuit;
	}

	private Value nexValueInSiStebbinsOrder() {
		Value nextValue;
		switch (this.value) {
		case ACE:
			nextValue = Value.FOUR;
			break;
		case TWO:
			nextValue = Value.FIVE;
			break;
		case THREE:
			nextValue = Value.SIX;
			break;
		case FOUR:
			nextValue = Value.SEVEN;
			break;
		case FIVE:
			nextValue = Value.EIGHT;
			break;
		case SIX:
			nextValue = Value.NINE;
			break;
		case SEVEN:
			nextValue = Value.TEN;
			break;
		case EIGHT:
			nextValue = Value.JACK;
			break;
		case NINE:
			nextValue = Value.QUEEN;
			break;
		case TEN:
			nextValue = Value.KING;
			break;
		case JACK:
			nextValue = Value.ACE;
			break;
		case QUEEN:
			nextValue = Value.TWO;
			break;
		case KING:
			nextValue = Value.THREE;
			break;
		default:
			nextValue = null;
			break;
		}
		
		return nextValue;
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = false;
		
		if (o instanceof Card) {
			Card otherCard = (Card) o;
			equals = (this.suit == otherCard.suit) && (this.value == otherCard.value);
		}
		
		return equals;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append(stringValue(value));
		buff.append(" de ");
		buff.append(stringValue(suit));
		return buff.toString();
	}

	private String stringValue(Value value) {
		String result = null;
		switch (value) {
			case ACE:
				result = "As";
				break;
			case TWO:
				result = "Dos";
				break;
			case THREE:
				result = "Tres";
				break;
			case FOUR:
				result = "Cuatro";
				break;
			case FIVE:
				result = "Cinco";
				break;
			case SIX:
				result = "Seis";
				break;
			case SEVEN:
				result = "Siete";
				break;
			case EIGHT:
				result = "Ocho";
				break;
			case NINE:
				result = "Nueve";
				break;
			case TEN:
				result = "Diez";
				break;
			case JACK:
				result = "Jota";
				break;
			case QUEEN:
				result = "Dama";
				break;
			case KING:
				result = "Rey";
				break;
		}

		return result;
	}

	private String stringValue(Suit suit) {
		String result = null;
		switch (suit) {
			case CLUBS:
				result = "trébol";
				break;
			case HEARTS:
				result = "corazones";
                break;
			case SPADES:
				result = "picas";
                break;
			case DIAMONDS:
				result = "rombos";
                break;
		}

		return result;
	}
}
