package Model;

import Support.DoubleLinkedDeque;

public class Trick
{
	protected Card.Suit trump;
	protected Card firstCard;
	protected Player winner;
	protected DoubleLinkedDeque<Card> cardsOnTable;

	/**
	 * Constructor for a new Trick, initialized with the trump suit.
	 * @param trump
	 */
	public Trick(Card.Suit trump)
	{
		this.trump = trump;
		cardsOnTable = new DoubleLinkedDeque<>();
	}

	/**
	 * Adds cards to the trick, and evaluates whether the card is higher or lower than the current highest card.
	 * After all cards have been added, the card at the front of the cardsOnTable linked queue is the highest, and its player wins the trick.
	 * @param player
	 * @param card
	 */
	public void addCard(Player player, Card card)
	{
		if (cardsOnTable.isEmpty())
		{
			this.winner = player;
			this.firstCard = card;
			cardsOnTable.enqueueFront(card);
		}
		else if (card.suit != this.firstCard.getSuit() && card.suit!=trump)
			cardsOnTable.enqueue(card);
		else if (card.compareTo(cardsOnTable.peekFront())>0)
		{
			cardsOnTable.enqueueFront(card);
			this.winner = player;
		}
		else
			cardsOnTable.enqueue(card);
	} // end addCard()

	public Player getWinner()
	{
		return winner;
	}

	@Override
	public String toString()
	{
		return "Trick{" +
				"cardsOnTable=" + cardsOnTable +
				'}';
	}
} // end Trick