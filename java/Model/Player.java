package Model;

import Support.LinkedStack;
import Support.SortedABList;

public class Player
{
	public enum Position {North, East, South, West}

	protected final Position position;
	protected SortedABList<Card> hand;
	protected boolean isDealer;
	protected LinkedStack<Trick> tricksTaken;

	/**
	 * Constructor. Initializes player position, hand, isDealer, and tricksTaken.
	 * @param position
	 */
	public Player (Position position)
	{
		this.position = position;
		this.hand = new SortedABList<>(Card.groupBySuitComparator());
		this.isDealer = false;
		this.tricksTaken = new LinkedStack<>();
	}

	/**
	 * Resets the hand and trick count.
	 */
	public void reset()
	{
		this.hand = new SortedABList<>();
		this.tricksTaken = new LinkedStack<>();
	}

	/**
	 * When player wins a trick, this method stores it into the tricksTaken variable for this player until the hand is over.
	 * @param trick
	 */
	public void takeTrick(Trick trick)
	{
		this.tricksTaken.push(trick);
	}

	public void pickupCard(Card card)
	{
		this.hand.add(card);
	}

	public Card playCard(int index)
	{
		return hand.remove(index);
	}

	public boolean isDealer()
	{
		return isDealer;
	}

	public void setDealer(boolean dealer)
	{
		isDealer = dealer;
	}

	public Position getPosition()
	{
		return position;
	}

	public SortedABList<Card> getHand()
	{
		return hand;
	}

	public LinkedStack<Trick> getTricksTaken()
	{
		return tricksTaken;
	}

	/**
	 * @return String with player position and a list of all cards currently in hand.
	 */
	@Override
	public String toString()
	{
		String string;
		if (isDealer)
			string = "[D] " + position;
		else
			string = "" + position;

		if (hand.isEmpty())
		{
			string = string + ":\tNo cards in hand";
			return string;
		}
		else
		{
			string = string + ":";
			int index = 1;
			for (Card card : hand)
			{
				string = string + "\t[" + index + "] " + card.toString() +"\t";
				index++;
			}
			return string;
		}
	}












}