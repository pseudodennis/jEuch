package Model;

// Modified from Card.java by Dale/Joyce/Weems Chapter 6

import java.util.Comparator;

public class Card implements Comparable<Card>
{
	public enum Rank {Nine, Ten, Jack, Queen, King, Ace}						// only six ranks used in Euchre
	public enum Suit {Heart, Club, Diamond, Spade}
	public enum Color {Black, Red}

	protected final Rank rank;
	protected final Suit suit;
	protected Color color;

	protected int value;

	/**
	 * Constructor. Sets the color based on the suit.
	 * @param rank
	 * @param suit
	 */
	public Card(Rank rank, Suit suit)
	{
		this.rank = rank;
		this.suit = suit;
		this.value = this.rank.ordinal()+10;
		if (this.suit==Suit.Spade || this.suit==Suit.Club)
			this.color = Color.Black;
		else if (this.suit==Suit.Heart || this.suit==Suit.Diamond)
			this.color = Color.Red;
	}

	/**
	 * Since the value changes after trump is determined, allows for setting value.
	 * The highest cards in Euchre are the "right bower" and "left bower",
	 * or the Jack of the trump suit and the Jack of the same-color trump suit, respectively.
	 * (i.e. if Spades is trump, the right bower is the Jack of Spades and the left bower is the Jack of Clubs)
	 * @param trump
	 */
	public void setValueTrump(Card trump)
	{
		if (this.suit == trump.getSuit())
		{
			this.value += 10;
			if (this.rank == Rank.Jack)
				this.value += 20;
		}
		else if (this.color == trump.color && this.suit != trump.getSuit() && this.rank==Rank.Jack)
			this.value +=20;
	}

	public void resetValue()
	{
		this.value = this.rank.ordinal()+10;
	}
	public Rank getRank() { return rank; }
	public Suit getSuit() { return suit; }

	@Override
	public boolean equals(Object obj)
	// Returns true if 'obj' is a Card with same rank
	// as this Card, otherwise returns false.
	{
		if (obj == this)
			return true;
		else
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		else
		{
			Card c = (Card) obj;
			return (this.rank == c.rank);
		}
	}

	/**
	 * Compares this Card with 'other' for order. Returns a
	 * negative integer, zero, or a positive integer as this object
	 * is less than, equal to, or greater than 'other'.
	 * @param other
	 * @return int
	 */
	public int compareTo(Card other)
	{
		return (this.value - other.value);
	}

	/**
	 * For display ordering of cards in a player's hand.
	 * Groups cards by suit, then orders by rank.
	 * @return Comparator<Card>
	 */
	public static Comparator<Card> groupBySuitComparator()
	{
		return new Comparator<Card>()
		{
			@Override
			public int compare(Card o2, Card o1)
			{
				return (((((o1.suit.ordinal())+1)*10) + (o1.rank.ordinal())) - ((((o2.suit.ordinal())+1)*10) + (o2.rank.ordinal())) );
			}
		};
	}

	/**
	 * The use of tabs can be sometimes hard to read, but helpful when printing out an entire deck.
	 * @return String
	 */
	@Override
	public String toString()
	{
		return suit + " " + rank;
		/*
			if (this.rank == Rank.Ten || this.rank == Rank.Ace)
				return suit + "\t" + rank + "\t";
			else
				return suit + "\t" + rank;
		*/
	}
}
