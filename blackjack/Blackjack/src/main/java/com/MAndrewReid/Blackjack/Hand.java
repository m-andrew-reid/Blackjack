package com.MAndrewReid.Blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	public ArrayList<Card> cards;
	public boolean hit = false;
	public boolean stay = false;
	public int index = 0;

	

	public Hand(Card cardOne, Card cardTwo) {
		this.cards = new ArrayList<Card>();
		this.cards.add(cardOne);
		this.cards.add(cardTwo);
		
		int blah = cards.
	}

	
	public boolean isStay() {
		return stay;
	}

	public void setStay(boolean stay) {
		this.stay = stay;
	}



	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(Hand cards) {
		this.cards = cards.cards;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ArrayList<Card> hitMe(ArrayList<Card> deck) {
		cards.add(deck.remove(0));
		return deck;
	}

//calculate the values of the player's cards
	public int calculateValue() {

		int handValue = 0;
		boolean alreadyAce = false;

		for (Card card : this.getCards()) {
			if (card.getValue() > 1) {
				handValue += card.getValue();
			} else {

				if (alreadyAce == false) {
					handValue += 11;
					alreadyAce = true;
				} else {

					handValue += 1;

				}

			}

		}
		return handValue;

	}

} // end class
