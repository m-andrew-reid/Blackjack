package com.MAndrewReid.Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.processing.RoundEnvironment;

import org.omg.CORBA.PUBLIC_MEMBER;

public class BlackjackController {

	public static ArrayList<Player> players = new ArrayList<Player>();
	public static ArrayList<Card> newDeckCards = new ArrayList<Card>();
	public static int roundNumber = 0;

	public static void main(String[] args) {

		Scanner myScanner = new Scanner(System.in);

		// print Greeting message
		printGreeting();
		enterPrompt();

		// enter player names and add them to the game
		System.out.println();
		System.out.println("Enter number of human players (0-4):");

		players = addPlayers(myScanner.nextInt());
		int[] hitList = new int[players.size()]; // will be used later to determine who hits

		enterPrompt();

		beginRound(players);

	}

//********Methods*********************

	public static void roundCheck(ArrayList<Player> players) {

		for (Player player : players) {
			for (Hand hand : player.getHands()) {
				if (hand.calculateValue() < 21 && hand.isStay() == false) {
					conductRound(players);
				}

			}
		}

	}

//*********CONDUCT ROUND	

	public static void conductRound(ArrayList<Player> players) {
		// offer choices

		players = hitOrStay(players);

		// deal hits
		players = dealHits(players, newDeckCards);

		displayHands(players);

		displayValues(players);

		houseCheck(players);

		roundCheck(players);
	}

	public static void beginRound(ArrayList<Player> players) { // starts the round off

		// build a deck
		roundNumber += 1;
		System.out.println("Round Number " + roundNumber);

		newDeckCards.clear();
		newDeckCards = buildDeck();

		// deal the cards
		dealCards(players, newDeckCards);

		// print the cards to the screen
		displayHands(players);

		displayValues(players);

		houseCheck(players);
	}

	// ************houseCheck

	public static void houseCheck(ArrayList<Player> players) {
		int houseValue = players.get(0).hands.get(0).calculateValue();
		boolean houseStay = players.get(0).hands.get(0).isStay();

		// house busts_________
		if (houseValue > 21) {
			System.out.println();
			System.out.println("HOUSE BUSTED!");
			houseLoss(players);

		}
		// house has 21__________
		if (houseValue == 21) {
			System.out.println();
			System.out.println("HOUSE HAS 21");
			houseWin(players);

		}

		// if the house has stayed ______________
		if (houseStay) {
			for (Player player : players) {
				for (Hand hand : player.getHands()) {
					if (hand.isHit()) {
						conductRound(players);
					} else {
						if (hand.calculateValue() <= houseValue) {
							houseLoss(players);
						} else {
							if (hand.calculateValue() > houseValue) {
								houseWin(players);
							}
						}
					}

				}
			}

		}
		// ____________________________

		conductRound(players);

	}

	
//*********Print Score
	public static void printScore(Player player) {
		System.out.println(player.getName() + "'s Score is: " + player.getScore());
	}
	
//******HOUSE WIN

	public static void houseWin(ArrayList<Player> players) {
		System.out.println();
		System.out.println("HOUSE WINS!");
		System.out.println();
		
		for (Player player : players) {
			if (players.indexOf(player) != 0) {
				for (Hand hand : player.getHands()) {

					player.changeScore(-5);
					printScore(player);

					

				}
			}
		}
		if (roundNumber <= 10) {
			beginRound(players);
		}
	}

//*********HOUSE LOSS	

	public static void houseLoss(ArrayList<Player> players) {
		System.out.println();
		System.out.println("THE HOUSE LOSES");
//_______________________________________
		for (Player player : players) {
			if (players.indexOf(player) != 0) {
				for (Hand hand : player.getHands()) {
					if (hand.calculateValue() == 21) {
						player.changeScore(20);
						printScore(player);
						
					}

					if (hand.calculateValue() < 21 || hand.calculateValue() > 21) {
						player.changeScore(5);
						printScore(player);
						
					}
				}
			}
		}
//________________________________________
		if (roundNumber <= 10) {
			beginRound(players);
		}
	}

	// ***********DEAL HITS

	public static ArrayList<Player> dealHits(ArrayList<Player> players, ArrayList<Card> deck) {
		ArrayList<Player> hitPlayers = new ArrayList<Player>();
		hitPlayers = players;
		for (Player player : hitPlayers) {
			for (Hand hand : player.getHands()) {
				if (hand.isHit()) {
					// is this okay?
					deck = hand.hitMe(deck);
					hand.setHit(false);
				}
			}

		}
		updateDeck(deck);
		return hitPlayers;
	}

//**********************

	public static void displayValues(ArrayList<Player> players) {
		System.out.println();
		for (Player player : players) {
			for (Hand hand : player.getHands()) {
				int handValue = hand.calculateValue();
				if (handValue > 21) {
					System.out.printf("%-20s", "BUST!:" + hand.calculateValue());
				} else {
					System.out.printf("%-20s", "Hand Value :" + hand.calculateValue());
				}
			}
		}
	}

//*********BOT HIT	
	public static boolean botHit(Hand hand) {
		int handValue = hand.calculateValue();

		if (handValue <= 17) {
			hand.setStay(false);
			hand.setHit(true);
			System.out.println("House Hits");
			return hand.isHit();
		}
		hand.setStay(true);
		hand.setHit(false);
		System.out.println("House Stays");
		return hand.isHit();
	}

//**********HIT-OR-STAY	

	public static ArrayList<Player> hitOrStay(ArrayList<Player> players) {
		System.out.println();
		Scanner myScanner = new Scanner(System.in);
		String response = "";
		for (Player player : players) {

			for (Hand hand : player.hands) {
				int handIndex = hand.getIndex();
				// computer players will decide to hit or stay
				if (player.isComputer() == true) {
					hand.setHit(botHit(hand));

				} else {
					if (hand.calculateValue() < 21 && hand.isStay() == false) {
						System.out.println(player.getName() + " Hand #" + (handIndex + 1) + ", (H)it or (S)tay ?");

						response = myScanner.nextLine();
						// if the player selects hit, add a hit counter
						if (response.toUpperCase().equals("H")) {
							hand.setHit(true);
							System.out.println(player.getName() + " Hits");
						} else {
							hand.setStay(true);
							System.out.println(player.getName() + " Stays");
						}
					}
				}
			}
		}

		return players;
	}// end hitOrStay

// *******************ADD PLAYERS

	public static ArrayList<Player> addPlayers(int numOfHumans) {

		// add players to the game
		ArrayList<Player> players = new ArrayList<Player>();
		String playerName;
		Scanner myScanner = new Scanner(System.in);
		// Add the house at 0
		players.add(new Player("House", true));

		for (int i = 0; i < numOfHumans; i++) {
			System.out.println("Enter Player " + (i + 1) + " name.");
			playerName = myScanner.nextLine();
			Player player = new Player(playerName, false);
			players.add(player);
			// System.out.println(playerName + " added.");

		}
		return players;
	}

	// print greeting message
	public static void printGreeting() {
		System.out.println("          $$$$$$$$$$$$$$$$$$$$$");
		System.out.println("          $$$$ WELCOME TO ANDR$W'$ CA$INO $$$$");
		System.out.println("          Now accepting Venmo!");
		System.out.println("          $$$$$$$$$$$$$$$$$$$$$");
		System.out.println();
		System.out.println();

	}

//*************BUILD DECK generate a deck of cards
	public static ArrayList<Card> buildDeck() {

		// possible suits
		char[] suits = { '\u2660', '\u2665', '\u2666', '\u2663' };
		char[] displays = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', '\u2469', 'J', 'Q', 'K' };
		int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10 };

		ArrayList<Card> deckOfCards = new ArrayList<Card>();

		// iterate through possible suits and values
		for (char suitChar : suits) {
			for (int i = 0; i < displays.length; i++) {
				Card newCard = new Card(suitChar, values[i], displays[i]);
				deckOfCards.add(newCard);
				// This prints out all the cards. got kinda obnoxious
				// System.out.print("[" + newCard.getDisplay() + "" + newCard.getSuit() + "]");
			}
			// System.out.println();
		}

		System.out.println("**Shuffling**");

		// shuffle the deck
		Collections.shuffle(deckOfCards);

		return deckOfCards;

	}

// *********ENTER PROMPT
	// prompt player to press enter
	public static void enterPrompt() {
		Scanner myScanner = new Scanner(System.in);
		System.out.println("Press Enter to Continue.");
		myScanner.nextLine();

	}

// ********* DEAL CARDS
	// initial deal of cards to start the round
	public static ArrayList<Player> dealCards(ArrayList<Player> players, ArrayList<Card> deck) {
		ArrayList<Player> updatedPlayers = new ArrayList<Player>();

		// for each player add two cards to their first hand position
		for (Player player : players) {
			player.clearHands();

			Hand hand = new Hand(deck.remove(0), deck.remove(0));
			player.setHand(hand);

			// return the updated player states to the Controller
			updatedPlayers.add(player);
		}
		updateDeck(deck);
		return updatedPlayers;
	}// dealCards

//***************************
	public static void updateDeck(ArrayList<Card> deck) {
		newDeckCards = deck;
	}

//************************

//display the hands of all players
	public static void displayHands(List<Player> players) {
		int handValue = 0;
		List<String> playersNames = new ArrayList<String>();
		for (Player player : players) {

			System.out.printf("%-20s", player.getName());
		}
		System.out.println();

		for (Player player : players) {
			String handDisplayString = "";

			for (Hand hand : player.getHands()) {
				for (Card card : hand.cards) {

					handDisplayString += "[" + card.getDisplay();
					handDisplayString += card.getSuit() + "]";
				}
				System.out.printf("%-20s", handDisplayString);
			}

		}

	}

////***************************

////***********

} // Class end
