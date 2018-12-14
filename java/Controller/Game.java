package Controller;

import Model.Card;
import Model.Player;
import Model.Score;
import Model.Table;
import Support.LLNode;
import View.Prompt;

public class Game
{
	protected Score score;
	protected Table table;

	/**
	 * Runs the game by executing the other methods in this class.
	 */
	public void run()
	{
		startGame();
		while (score.getGameWinner()==null)
		{
			dealCards();
			callTrump();
			playHand();
			updateScore();
			reset();
		}
	} // end run()

	/**
	 * Starts the game.
	 */
	protected void startGame()
	{
		System.out.println(Prompt.welcome());

		// initialize new table, and score
		table = new Table();
		score = new Score(2);
	}

	/**
	 * Shuffles and distributes cards to all the players.
	 */
	protected void dealCards()
	{
		table.getDeck().shuffle();
		System.out.println("\n\nShuffling and dealing the cards...");
		table.dealAll(5);

		// System.out.println(table.toString()); 								// display everyone's hands for testing and verification

		// reveal the top card of the kitty
		System.out.println("The top card of the kitty is turned up to be:\t[" + table.getDeck().revealTop() + "]");
	}

	/**
	 * The first phase of each hand is calling Trump. Sets value of each Card accordingly.
	 */
	protected void callTrump()
	{
		LLNode<Player> currentPlayer = table.getPlayers().getFront();
		boolean trumpYet = false;
		boolean completeRound = false;

		// ROUND 1: ask players if they want dealer to pickup the top card and declare its suit as trump
		while (!completeRound && !trumpYet)								// cycle through all the players
		{
			System.out.println();
			System.out.println(currentPlayer.getInfo().toString());
			// System.out.print(currentPlayer.getInfo().getPosition() + ":\t");
			if (Prompt.callTrump(table.getDeck().revealTop()))					// ask if they want dealer to pick up the card and declare it the trump suit
			{
				int index = Prompt.removeCard(table.getDealer());				// ask dealer which card to discard
				table.getDealer().playCard(index);								// dealer removes card
				table.setTrumpCard(table.getDeck().revealTop());				// set trump value for table
				table.setPlayerWhoCalledTrump(currentPlayer.getInfo());
				table.getDealer().pickupCard(table.getDeck().dealNext());		// dealer picks up trump card
				trumpYet = true;
			}
			if (currentPlayer.getLink()==null)									// if last player reached, don't ask any more
				completeRound = true;
			else
				currentPlayer = currentPlayer.getLink();
		}

		// ROUND 2, if needed: ask players which suit they want as trump (other than the previous top card)
		currentPlayer = table.getPlayers().getFront();

		Card trumpCard;									// TODO: replace the trumpCard variable with a direct reference to table.trump
		while (!trumpYet)								// keep cycling until someone declares a trump suit
		{
			System.out.println();
			System.out.println(currentPlayer.getInfo().toString());
			trumpCard = Prompt.callTrump2(table.getDeck().revealTop());

			if (trumpCard != null)
			{
				table.setTrumpCard(trumpCard);				// set trump value for table
				table.setPlayerWhoCalledTrump(currentPlayer.getInfo());
				trumpYet = true;
			}

			if (currentPlayer.getLink()==null)									// if the last player is reached, start over
				currentPlayer = table.getPlayers().getFront();
			else																// otherwise go to the next player
				currentPlayer = currentPlayer.getLink();
		}

		// Update values of cards based on trump value
		currentPlayer = table.getPlayers().getFront();
		while (currentPlayer != null)
		{
			for (Card card : currentPlayer.getInfo().getHand())
			{
				card.resetValue();
				card.setValueTrump(table.getTrumpCard());
			}
			currentPlayer = currentPlayer.getLink();
		}



	} // end callTrump()

	/**
	 * Plays through an entire hand, attributing tricks won.
	 */
	protected void playHand()
	{
		LLNode<Player> currentPlayer = table.getPlayers().getFront();			// start with the player to the "left" of the dealer

		while (!currentPlayer.getInfo().getHand().isEmpty())					// while there are cards left to be played
		{
			table.newTrick();													// setup a new trick
			for (int i = 0; i < table.getPlayers().size(); i++)					// make sure everyone plays
			{
				Card playerCard = currentPlayer.getInfo().playCard(Prompt.playCard(currentPlayer.getInfo(), table.getTrumpCard()));
				table.addToTrick(currentPlayer.getInfo(), playerCard);
				// go to the next player; if at the end of list go back to the front
				if (currentPlayer.getLink() != null)
					currentPlayer = currentPlayer.getLink();
				else
					currentPlayer = table.getPlayers().getFront();
			}
			// winner takes the trick
			Player winner = table.getTrick().getWinner();
			winner.takeTrick(table.getTrick());
			Prompt.wonTrick(winner);
			Prompt.trickCount(table);


			// set current player to the winner of the last trick
			while (currentPlayer.getInfo() != winner)
			{
				if (currentPlayer.getLink() != null)
					currentPlayer = currentPlayer.getLink();
				else
					currentPlayer = table.getPlayers().getFront();
			}

		}
	}

	/**
	 * Updates the score and checks for a winner. Euchre is played until the first team reaches 10 points, but for demonstration purposes the winning score here is set at 2.
	 */
	public void updateScore()
	{
		score.updateScore(table);
		Prompt.showScore(score);
		if (score.checkWin())
		{
			Prompt.congratsWinner(score);
			System.exit(0);
		}
	}

	/**
	 * Resets the table, deck, and players for the next round. Advances the dealer to the next player.
	 */
	public void reset()
	{
		table.resetNext();
	}



} // end Game
