package leaderBoard;

/**
 * All queues which contain an elo system and leaderboard.
 * 
 * @author Gabriel Glaser
 */
public enum RankedQueue {
	SOLO_DUO, FLEX;

	@Override
	public String toString() {
		if (this == SOLO_DUO) {
			return "Solo/Duo";
		} else {
			return "Flex";
		}
	}
}
