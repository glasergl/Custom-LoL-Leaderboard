package leaderBoard;

import java.util.Optional;

/**
 * This class represents a single player of league of legends which occurs in a
 * leaderbord. This means that the elo of the player is at least master for any
 * queue.
 * 
 * Note that LoLPlayer objects are instantiated per queue which means if a
 * player reached the leaderboard in both queues (solo/duo and flex), the player
 * will be instantiated twice.
 * 
 * Finally, each player has two special characteristics which are whether the
 * player will rank up or down. However, this can only be calculated when all
 * players have been retrieved. Hence, the fields will be set later during
 * instantiation of a Leaderboard object.
 * 
 * @author glasergl
 */
public final class LoLPlayer implements Comparable<LoLPlayer> {
	private final String summonerName;
	private final int leaguePoints;
	private final RankedQueue queue;
	private final TopTierElo elo;
	private final int numberOfGames;
	private final float winRate;

	private Optional<Boolean> willRankUpWrapper = Optional.empty();
	private Optional<Boolean> willRankDownWrapper = Optional.empty();

	public LoLPlayer(final String summonerName, final int leaguePoints, final RankedQueue queueType,
			final TopTierElo eloType, final int numberOfGames, final float winRate) {
		super();
		this.summonerName = summonerName;
		this.leaguePoints = leaguePoints;
		this.queue = queueType;
		this.elo = eloType;
		this.numberOfGames = numberOfGames;
		this.winRate = winRate;
	}

	public void setWillRankUp(final boolean willRankUp) {
		this.willRankUpWrapper = Optional.of(willRankUp);
	}

	public void setWillRankDown(final boolean willRankDown) {
		this.willRankDownWrapper = Optional.of(willRankDown);
	}

	public String getSummonerName() {
		return summonerName;
	}

	public int getLeaguePoints() {
		return leaguePoints;
	}

	public RankedQueue getQueue() {
		return queue;
	}

	public TopTierElo getElo() {
		return elo;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public float getWinRate() {
		return winRate;
	}

	public boolean willRankUp() {
		if (!willRankUpWrapper.isPresent()) {
			throw new IllegalStateException("Whether player will rank up has not been determined yet!");
		} else {
			return willRankUpWrapper.get();
		}
	}

	public boolean willRankDown() {
		if (!willRankDownWrapper.isPresent()) {
			throw new IllegalStateException("Whether player will rank down has not been determined yet!");
		} else {
			return willRankDownWrapper.get();
		}
	}

	@Override
	public int compareTo(final LoLPlayer player) {
		return -Integer.compare(leaguePoints, player.getLeaguePoints());
	}

	@Override
	public String toString() {
		return summonerName + ": " + leaguePoints + " LP " + queue + "-" + elo + " rankUp: " + willRankUp()
				+ ", rankDown: " + willRankDown();
	}
}
