package Model;

import java.util.ArrayList;
import java.util.Collections;

import Model.Card;

/*
 * Deck has 52 cards: 50 (one for each territory) + 2 (wild cards)
 * NOTE: Deck class is a singleton, should never have more than one! :) 
 */
public class Deck {

	private ArrayList<Card> riskDeck;
	private int size;
	private static Deck uniqueDeck;

	private Deck() {
		riskDeck = new ArrayList<Card>();
		fillDeck(riskDeck);
		shuffle();
		size = 52;
	}// end constructor

	public static synchronized Deck getInstance() {
		if (uniqueDeck == null)
			uniqueDeck = new Deck();
		return uniqueDeck;
	}// end getInstance

	public void shuffle() {
		riskDeck.clear();
		fillDeck(riskDeck);
		Collections.shuffle(riskDeck);
		size = 52;
	}// end shuffle

	// returns null if the deck has run out of cards.
	public Card deal() {
		if (size > 0) {
			Card result;
			result = riskDeck.get(size - 1);
			riskDeck.remove(size - 1);
			size--;
			return result;
		} else
			return null;
	}// end deal

	public int getSize() {
		return size;
	}// end getSize

	// possible units: infantry, cavalry, artillery. Add all territories
	// (countries) and 2 wild cards.
	private void fillDeck(ArrayList<Card> deck) {
		deck.add(new Card("The Wall", "infantry"));
		deck.add(new Card("Skagos", "cavalry"));
		deck.add(new Card("Wolfswood", "artillery"));
		deck.add(new Card("Winterfell", "infantry"));
		deck.add(new Card("The Rills", "cavalry"));
		deck.add(new Card("The Neck", "artillery"));
		deck.add(new Card("The Flint Cliffs", "infantry"));
		deck.add(new Card("The Grey Cliffs", "cavalry"));
		deck.add(new Card("The Vale", "artillery"));
		deck.add(new Card("Riverlands", "infantry"));
		deck.add(new Card("Iron Islands", "cavalry"));
		deck.add(new Card("Westerlands", "artillery"));
		deck.add(new Card("Crownlands", "infantry"));
		deck.add(new Card("The Reach", "cavalry"));
		deck.add(new Card("Shield Lands", "artillery"));
		deck.add(new Card("Whispering Sound", "infantry"));
		deck.add(new Card("Storm Lands", "cavalry"));
		deck.add(new Card("Red Mountains", "artillery"));
		deck.add(new Card("Dorne", "infantry"));
		deck.add(new Card("Braavosi Coastland", "cavalry"));
		deck.add(new Card("Andalos", "artillery"));
		deck.add(new Card("Hills of Norvos", "infantry"));
		deck.add(new Card("Rhoyne Lands", "cavalry"));
		deck.add(new Card("Forrest of Qohor", "artillery"));
		deck.add(new Card("The Golden Fields", "infantry"));
		deck.add(new Card("The Disputed Lands", "cavalry"));
		deck.add(new Card("Rhoynian Veld", "artillery"));
		deck.add(new Card("Sar Mell", "infantry"));
		deck.add(new Card("Western Waste", "cavalry"));
		deck.add(new Card("Sea of Sighs", "artillery"));
		deck.add(new Card("Elyria", "infantry"));
		deck.add(new Card("Valyria", "cavalry"));
		deck.add(new Card("Sarnor", "artillery"));
		deck.add(new Card("Parched Fields", "infantry"));
		deck.add(new Card("Abandoned Lands", "cavalry"));
		deck.add(new Card("Western Grass Sea", "artillery"));
		deck.add(new Card("Kingdoms of the Jfeqevron", "infantry"));
		deck.add(new Card("Eastern Grass Sea", "cavalry"));
		deck.add(new Card("The Footprint", "artillery"));
		deck.add(new Card("Vaes Dothrak", "infantry"));
		deck.add(new Card("Realms of Jhogrvin", "cavalry"));
		deck.add(new Card("Ibben", "artillery"));
		deck.add(new Card("Painted Mountains", "infantry"));
		deck.add(new Card("Slaver's Bay", "cavalry"));
		deck.add(new Card("Lhazar", "artillery"));
		deck.add(new Card("Samyrian Hills", "infantry"));
		deck.add(new Card("Bayasabhad", "cavalry"));
		deck.add(new Card("Ghiscar", "artillery"));
		deck.add(new Card("The Red Waste", "infantry"));
		deck.add(new Card("Qarth", "cavalry"));
		deck.add(new Card("WILD", "WILD"));
		deck.add(new Card("WILD", "WILD"));
	}// end fillDeck

}// end Deck Class
