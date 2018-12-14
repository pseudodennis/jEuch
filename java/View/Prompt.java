package View;



import Model.Card;
import Model.Player;
import Model.Score;
import Model.Table;
import Support.LLNode;
import java.util.Scanner;
import static Model.Card.Rank.Ace;		// used as default card rank when declaring the trump suit
import static Model.Card.Suit.*;

public class Prompt
{
	public static String welcome()
	{
		return "WELCOME TO jEUCH!\n" +
				"Euchre /ˈjuːkər/ or eucre, an offshoot of Juckerspiel, is a trick-taking card game played with four people in two partnerships with a deck of 24 standard playing cards." +
				"\n\n\nLet's begin!";
	}
	/**
	 * Prompts the user to have the dealer pick up the top card (thus declaring trump suit) or pass
	 * @param card
	 * @return boolean for pass or pickup answer
	 */
	public static boolean callTrump(Card card)
	{
		String answer;
		Scanner scanner = new Scanner(System.in);

		System.out.print("Do you wish to have the dealer pickup the [" + card.toString() + "] and declare [" + card.getSuit() + "s] as trump? [y/n]\t");
		answer = scanner.nextLine();

		if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
			return true;
		else
			return false;
		// TODO: input validation
	}

	/**
	 * After the initial round, players can declare any suit (other than the initial card-suit) to be trump.
	 * @param card
	 * @return Card with the declared trump value.
	 */
	public static Card callTrump2(Card card)
	{
		Card trumpCard;
		String answer;
		Scanner scanner = new Scanner(System.in);
		boolean isValid = true;

		do
		{
			System.out.print("Which suit would you like for trump? [type suit to pick, or anything else to pass]\t");
			answer = scanner.nextLine();

			if (answer.equalsIgnoreCase(card.getSuit().toString()))
			{
				System.out.print("Original suit cannot be chosen! ");
				isValid = false;
			}

		} while (!isValid);

		if (answer.equalsIgnoreCase("spade") || answer.equalsIgnoreCase("spades"))
			trumpCard = new Card(Ace, Spade);
		else if (answer.equalsIgnoreCase("diamond") || answer.equalsIgnoreCase("diamonds"))
			trumpCard = new Card(Ace, Diamond);
		else if (answer.equalsIgnoreCase("club") || answer.equalsIgnoreCase("clubs"))
			trumpCard = new Card(Ace, Club);
		else if (answer.equalsIgnoreCase("heart") || answer.equalsIgnoreCase("hearts"))
			trumpCard = new Card(Ace, Heart);
		else
			return null;

		return trumpCard;
	}


	/**
	 * Prompts dealer to pick which card to discard upon picking up the trump card.
	 * @param player
	 * @return int with card selection
	 */
	public static int removeCard(Player player)
	{
		int answer;
		Scanner scanner = new Scanner(System.in);

		System.out.println("Which card would you like to remove?\t");
		System.out.print(player.toString());
		answer = scanner.nextInt()-1;
		return answer;
	}

	/**
	 * Prompts player for card to play.
	 * @param player
	 * @return int with card selection
	 */
	public static int playCard(Player player, Card card)
	{
		int answer;
		Scanner scanner = new Scanner(System.in);

		System.out.println("\n" + player.toString());
		System.out.print("Which card would you like to play?\t(" + card.getSuit() + "s is trump)\t");
		answer = scanner.nextInt()-1;
		return answer;
	}

	/**
	 * Enjoy your hard-won victory.
	 * @param score
	 */
	public static void congratsWinner(Score score)
	{
		if (score.getGameWinner()!=null)
		{
			System.out.println();
			System.out.println("*****************************************************************************");
			System.out.println("Congrats, " + score.getGameWinner() + " team, you've won the Euchre game!");


		}
		else
			System.out.println("Sorry, no winner yet!");
	}

	public static void showScore(Score score)
	{
		System.out.println("-----------------");
		System.out.println(score.getScore());
	}

	public static void wonTrick(Player player)
	{
		System.out.println(player.getPosition() + " won the trick");
	}

	/**
	 * Displays the running tally of how many tricks each player has taken in the hand so far.
	 * @param table
	 */
	public static void trickCount(Table table)
	{
		LLNode<Player> currentPlayer = table.getPlayers().getFront();
		while (currentPlayer != null)
		{
			System.out.println(currentPlayer.getInfo().getPosition() + ":\t" + currentPlayer.getInfo().getTricksTaken().getSize());
			currentPlayer = currentPlayer.getLink();
		}
	}
}


