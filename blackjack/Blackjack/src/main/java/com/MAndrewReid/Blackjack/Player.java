package com.MAndrewReid.Blackjack;

import java.util.ArrayList;

public class Player {
String name;
int score;
ArrayList<Hand> hands;
boolean isComputer;
int hit;

public Player(String name,  boolean isComputer) {
	this.hands = new ArrayList<Hand>();
	this.name = name;
	this.isComputer = isComputer;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getScore() {
	return score;
}

public void changeScore(int score) {
	this.score += score;
}

public ArrayList<Hand> getHands() {
	return hands;
}

public void setHands(ArrayList<Hand> hands) {
	this.hands = hands;
}

public void clearHands() {
	this.hands.clear();
}

public Hand getHand(int handIndex) {
	return hands.get(handIndex);
}

public void setHand(Hand hand) {
	this.hands.add(hand);
}

public boolean isComputer() {
	return isComputer;
}

public void setComputer(boolean isComputer) {
	this.isComputer = isComputer;
}













}
