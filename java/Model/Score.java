package Model;

import Support.LLNode;
import static Model.Player.Position.*;

public class Score
{
	protected int NSGameScore;
	protected int EWGameScore;
	protected String gameWinner;
	protected int victory;

	/**
	 * Constructor for the Score class, which is essentially two score accumulators, one for each team.
	 */
	public Score(int victory)
	{
		this.EWGameScore = 0;
		this.NSGameScore = 0;
		this.victory = victory;
	}

	/**
	 * Constructor for the Score class, which is essentially two score accumulators, one for each team.
	 * Default victory score is 10.
	 */
	public Score()
	{
		this.EWGameScore = 0;
		this.NSGameScore = 0;
		this.victory = 10;
	}

	/**
	 * Tallies the number of tricks on the table, then increments the score based on which team took the most
	 * @param table
	 */
	public void updateScore(Table table)
	{
		int NSTricks = 0;
		int EWTricks = 0;

		LLNode<Player> currentPlayer = table.players.getFront();
		while (currentPlayer != null)
		{
			if (currentPlayer.getInfo().getPosition()==North || currentPlayer.getInfo().getPosition()==South)
				NSTricks += currentPlayer.getInfo().tricksTaken.getSize();
			else if (currentPlayer.getInfo().getPosition()==East || currentPlayer.getInfo().getPosition()==West)
				EWTricks += currentPlayer.getInfo().tricksTaken.getSize();
			currentPlayer = currentPlayer.getLink();
		}
		if (NSTricks > EWTricks)
			NSGameScore++;
		else
			EWGameScore++;
	} // TODO: allow for different scores based on margin of victory and who called trump

	public String getScore()
	{
		String score = 	"North/South:\t" + NSGameScore + "\nEast/West:  \t" + EWGameScore;
		return score;
	}

	/**
	 * Checks to see if either team has reached the winning score threshold.
	 * @return boolean
	 */
	public boolean checkWin()
	{
		if (NSGameScore >= victory)
		{
			gameWinner = "North/South";
			return true;
		}
		else if (EWGameScore >= victory)
		{
			gameWinner = "East/West";
			return true;
		}
		else
			return false;
	}

	public String getGameWinner()
	{
		return gameWinner;
	}
}
