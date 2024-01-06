package leaderBoard;

/**
 * Top tier elos which are part of a leaderboard.
 * 
 * @author Gabriel Glaser
 */
public enum TopTierElo {
	MASTER, GRANDMASTER, CHALLENGER;

	@Override
	public String toString() {
		if (this == CHALLENGER) {
			return "Challenger";
		} else if (this == GRANDMASTER) {
			return "Grandmaster";
		} else {
			return "Master";
		}
	}
}
