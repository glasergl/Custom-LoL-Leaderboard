package leaderBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the leaderboard of a single queue which encapsulates
 * its challenger -, grandmaster - and master players.
 * 
 * Furthermore, passing the respective players to this class leads to two things
 * being calculated which will be reflected in the respective player object. On
 * the one hand, which players will rank up or down from their elo. On the other
 * hand, what is the cutoff lp amount to reach challenger or grandmaster. This
 * is the lp of the last player which currently would be in the respective elo.
 * For instance, the cutoff to challenger in solo/duo is the lp of the player at
 * position 300 considering all players in all three top tier elos.
 * 
 * @author Gabriel Glaser
 */
public final class LeaderBoard {
	private static final int MINIMUM_LP_FOR_CHALLENGER = 500;
	private static final int MINIMUM_LP_FOR_GRANDMASTER = 200;

	private final RankedQueue queue;
	private final int maximumNumberOfChallengerPlayers;
	private final int maximumNumberOfGrandmasterPlusPlayers;
	private final List<LoLPlayer> challengerPlayers;
	private final List<LoLPlayer> grandmasterPlayers;
	private final List<LoLPlayer> masterPlayers;
	private final List<LoLPlayer> allPlayers;
	private final int lpCutoffForChallenger;
	private final int lpCutoffForGrandmaster;

	public LeaderBoard(final RankedQueue queue, final List<LoLPlayer> challengerPlayers,
			final List<LoLPlayer> grandmasterPlayers, final List<LoLPlayer> masterPlayers) {
		super();
		this.queue = queue;
		this.maximumNumberOfChallengerPlayers = (queue == RankedQueue.SOLO_DUO ? 300 : 200);
		this.maximumNumberOfGrandmasterPlusPlayers = (queue == RankedQueue.SOLO_DUO ? 1000 : 700);
		this.challengerPlayers = challengerPlayers;
		this.grandmasterPlayers = grandmasterPlayers;
		this.masterPlayers = masterPlayers;
		this.allPlayers = calculateAllPlayers();
		determineRankUpAndRankDownForAllPlayers();
		this.lpCutoffForChallenger = calculateChallengerLPCutoff();
		this.lpCutoffForGrandmaster = calculateGrandmasterLPCutoff();
	}

	private List<LoLPlayer> calculateAllPlayers() {
		final ArrayList<LoLPlayer> allPlayers = new ArrayList<>(
				challengerPlayers.size() + grandmasterPlayers.size() + masterPlayers.size());
		allPlayers.addAll(challengerPlayers);
		allPlayers.addAll(grandmasterPlayers);
		allPlayers.addAll(masterPlayers);
		Collections.sort(allPlayers);
		return allPlayers;
	}

	private void determineRankUpAndRankDownForAllPlayers() {
		for (int i = 0; i < allPlayers.size(); i++) {
			final LoLPlayer player = allPlayers.get(i);
			final boolean willRankUpToGrandmaster = player.getElo() == TopTierElo.MASTER
					&& isEligableForGrandmaster(player);
			final boolean willRankUpToChallenger = player.getElo() == TopTierElo.GRANDMASTER
					&& isEligableForChallenger(player);
			player.setWillRankUp(willRankUpToGrandmaster || willRankUpToChallenger);

			final boolean willRankDownToGrandmaster = player.getElo() == TopTierElo.CHALLENGER
					&& !isEligableForChallenger(player);
			final boolean willRankDownToMaster = player.getElo() == TopTierElo.GRANDMASTER
					&& !isEligableForGrandmaster(player);
			player.setWillRankDown(willRankDownToGrandmaster || willRankDownToMaster);
		}
	}

	private int calculateChallengerLPCutoff() {
		if (allPlayers.size() >= maximumNumberOfChallengerPlayers) {
			final LoLPlayer playerAtCutoff = allPlayers.get(maximumNumberOfChallengerPlayers - 1);
			return Math.max(MINIMUM_LP_FOR_CHALLENGER, playerAtCutoff.getLeaguePoints());
		} else {
			return MINIMUM_LP_FOR_CHALLENGER;
		}
	}

	private int calculateGrandmasterLPCutoff() {
		if (allPlayers.size() >= maximumNumberOfGrandmasterPlusPlayers) {
			final LoLPlayer playerAtCutoff = allPlayers.get(maximumNumberOfGrandmasterPlusPlayers - 1);
			return Math.max(MINIMUM_LP_FOR_GRANDMASTER, playerAtCutoff.getLeaguePoints());
		} else {
			return MINIMUM_LP_FOR_GRANDMASTER;
		}
	}

	private boolean isEligableForChallenger(final LoLPlayer player) {
		final int rankIndex = allPlayers.indexOf(player) + 1;
		return rankIndex <= maximumNumberOfChallengerPlayers && player.getLeaguePoints() >= MINIMUM_LP_FOR_CHALLENGER;
	}

	private boolean isEligableForGrandmaster(final LoLPlayer player) {
		final int rankIndex = allPlayers.indexOf(player) + 1;
		return rankIndex <= maximumNumberOfGrandmasterPlusPlayers
				&& player.getLeaguePoints() >= MINIMUM_LP_FOR_GRANDMASTER;
	}

	public RankedQueue getQueue() {
		return queue;
	}

	public List<LoLPlayer> getChallengerPlayers() {
		return challengerPlayers;
	}

	public List<LoLPlayer> getGrandmasterPlayers() {
		return grandmasterPlayers;
	}

	public List<LoLPlayer> getMasterPlayers() {
		return masterPlayers;
	}

	public List<LoLPlayer> getAllPlayers() {
		return allPlayers;
	}

	public int getChallengerLPCutOff() {
		return lpCutoffForChallenger;
	}

	public int getGrandmasterLPCutOff() {
		return lpCutoffForGrandmaster;
	}
}
