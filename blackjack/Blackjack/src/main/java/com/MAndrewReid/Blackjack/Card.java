package com.MAndrewReid.Blackjack;
import java.util.HashMap;
import java.util.Map;

import org.omg.CORBA.PRIVATE_MEMBER;

public class Card {
	
	private char suit;
	private int value;
	private char display;
	
	
	

	
	public Card(char suit, int value, char display) {
		this.suit = suit;
		this.value = value;
		this.display = display;
	}


	public char getSuit() {
		return suit;
	}


	public void setSuit(char suit) {
		this.suit = suit;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}
	
	public char getDisplay() {
		return display;
	}


	public void setDisplay(char display) {
		this.display = display;
	}
		
	
	
	
	
}

	
