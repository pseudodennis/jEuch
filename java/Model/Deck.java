package Model;

// Modified from CardDeck.java by Dale/Joyce/Weems Chapter 6

import Support.LinkedStack;
import java.util.*;

public class Deck
{
	public static final int NUMCARDS = 24;	// Euchre is played with 24 cards, Nine through Ace
	protected LinkedStack<Card> deck;		// see README for ADT rationale

	/**
	 * Constructor. Generates a deck with every suit/rank combination.
	 */
	public Deck()
	{
		deck = new LinkedStack<Card>();
		for (Card.Suit suit : Card.Suit.values())
			for (Card.Rank rank : Card.Rank.values())
			{
				deck.push(new Card(rank, suit));
			}
	}

	/**
	 * Randomizes the order of the cards in the deck.
	 */
	public void shuffle()
	{
		ArrayList<Card> deckInHand = new ArrayList<>();
		while (!deck.isEmpty())
		{
			deckInHand.add(deck.top());
			deck.pop();
		}
		Collections.shuffle(deckInHand);
		deckInHand.forEach(card -> deck.push(card));
	}

	/**
	 * Returns true if there are still cards left to be dealt; otherwise, returns false.
	 * @return boolean
	 */
	public boolean hasNextCard()
	{
		return (!deck.isEmpty());
	}

	/**
	 * Returns the next card for the current 'deal'.
	 * @return Card
	 */
	public Card dealNext()
	{
		if (this.hasNextCard())
		{
			Card nextCard = deck.top();
			deck.pop();
			return nextCard;
		}
		else
			return null;
	}

	/**
	 * Returns the card on the top of the deck, such as when the top card is turned up on the kitty.
	 * @return Card
	 */
	public Card revealTop()
	{
		if (this.hasNextCard())
		{
			return deck.top();
		}
		else
			return null;
	}


	/**
	 * For testing.
	 * @return String displaying all the cards in the deck.
	 */
	public String toString()
	{
		LinkedStack<Card> newStack = new LinkedStack<>();
		String string = "Deck:\n\t";
		int i = 1;
		while (!deck.isEmpty())
		{
				string = string + deck.top() + "\t\t";
			if (i%6==0)
				string = string + "\n\t";
			newStack.push(deck.top());
			deck.pop();
			i++;
		}
		while (!newStack.isEmpty())
		{
			deck.push(newStack.top());
			newStack.pop();
		}
		return string;
	}

}
