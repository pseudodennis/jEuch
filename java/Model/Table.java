package Model;

import Support.LLNode;
import Support.LinkedQueue;

public class Table
{
	protected Deck deck ;
	protected LinkedQueue<Player> players;
	protected Card trumpCard;
	protected Player playerWhoCalledTrump;
	protected Trick trick;
	protected Player dealer;

	/**
	 * Constructor for Table. Initializes new deck, players, sets dealer.
	 */
	public Table()
	{
		this.deck = new Deck();
		this.players = new LinkedQueue<>();
		this.players = generatePlayers();
		this.dealer = players.getRear().getInfo();
		players.getRear().getInfo().setDealer(true);
	}

	/**
	 * Resets the table to get ready for the next hand.
	 * Resets deck, trumpCard, playerWhoCalledTrump, and trick.
	 */
	public void resetNext()
	{
		this.deck = new Deck();
		this.trumpCard = null;
		this.playerWhoCalledTrump = null;
		this.trick = null;

		LLNode<Player> currentPlayer = players.getFront();
		while (currentPlayer != null)
		{
			currentPlayer.getInfo().reset();
			currentPlayer = currentPlayer.getLink();
		}

		nextDealer();
	}

	/**
	 * Generates players. See README for ADT rationale.
	 * @return LinkedQueue<Player>
	 */
	protected static LinkedQueue<Player> generatePlayers()
	{
		LinkedQueue<Player> newPlayers = new LinkedQueue<>();
		for (Player.Position position : Player.Position.values())
		{
			newPlayers.enqueue(new Player(position));
		}
		return newPlayers;
	}

	/**
	 * Moves the dealer to the next player by dequeue/enqueue the Player from the player queue.
	 */
	public void nextDealer()
	{
		players.getRear().getInfo().setDealer(false);
		this.dealer = players.dequeue();
		players.enqueue(dealer);
		players.getRear().getInfo().setDealer(true);
	}

	public LinkedQueue<Player> getPlayers()
	{
		return players;
	}

	public Deck getDeck()
	{
		return deck;
	}

	public Player getDealer()
	{
		return dealer;
	}

	public void setDealer(Player dealer)
	{
		this.dealer = dealer;
	}

	/**
	 * Deals out a complete hand to every player.
	 * @param sizeOfHand
	 */
	public void dealAll(int sizeOfHand)
	{
		for (int i=0; i<sizeOfHand; i++)
		{
			LLNode<Player> currentPlayer = players.getFront();
			while (currentPlayer.getLink() != null)
			{
				currentPlayer.getInfo().pickupCard(deck.dealNext());
				currentPlayer = currentPlayer.getLink();
			}
			currentPlayer.getInfo().pickupCard(deck.dealNext());
		}
	}

	public Card getTrumpCard()
	{
		return trumpCard;
	}

	public void setTrumpCard(Card trumpCard)
	{
		this.trumpCard = trumpCard;
	}

	public Trick getTrick()
	{
		return trick;
	}

	/**
	 * Takes a card from a player and adds it to the trick.
	 * @param player
	 * @param card
	 */
	public void addToTrick(Player player, Card card)
	{
		this.trick.addCard(player, card);
	}

	public void newTrick()
	{
		this.trick = new Trick(trumpCard.suit);
	}


	public Player getPlayerWhoCalledTrump()
	{
		return playerWhoCalledTrump;
	}

	public void setPlayerWhoCalledTrump(Player playerWhoCalledTrump)
	{
		this.playerWhoCalledTrump = playerWhoCalledTrump;
	}

	@Override
	public String toString()
	{
		String string = "Table\t----------------------------";
		return recStringify(this.players.getFront(), string);
	}

	public String recStringify(LLNode<Player> node, String str)
	{
		if (node == null)
		{
			return str;
		}
		else
		{
			str = str + "\n" + node.getInfo().toString();
			return recStringify(node.getLink(), str);
		}
	}

}


