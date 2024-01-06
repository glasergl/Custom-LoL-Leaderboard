package leaderBoard.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import leaderBoard.LeaderBoard;
import leaderBoard.LoLPlayer;
import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * @author glasergl
 */
public class TestDetermineRankUpAndRankDown {
	/**
	 * Tests whether a leaderboard which doesn't contain a player who will rank up
	 * or down is correctly reflected in the LoLPlayer objects.
	 */
	@Test
	public void testLeaderBoardWithNoRankUpOrRankDown() {
		final LeaderBoard leaderBoard = getExampleLeaderBoardWithNoRankUpOrRankDown();
		for (final LoLPlayer lolPlayer : leaderBoard.getAllPlayers()) {
			assertFalse(lolPlayer.willRankUp());
			assertFalse(lolPlayer.willRankDown());
		}
	}

	/**
	 * Instantiates a leaderboard with 1500 players, 500 master players, 700
	 * grandmaster players and 300 challenger players. Each player has the amount of
	 * lp of the player's rank in the leaderboard. This means master the master
	 * players have the lp 1 to 500, the grandmaster players have the lp from 501 to
	 * 1200 and the challenger players have the lp from 1201 to 1500.
	 * 
	 * @return The leaderboard with 1500 players.
	 */
	private static LeaderBoard getExampleLeaderBoardWithNoRankUpOrRankDown() {
		final RankedQueue queue = RankedQueue.SOLO_DUO;
		final List<LoLPlayer> masterPlayers = new ArrayList<>(500);
		final List<LoLPlayer> grandmasterPlayers = new ArrayList<>(700);
		final List<LoLPlayer> challengerPlayers = new ArrayList<>(300);
		for (int i = 1; i <= 500; i++) {
			masterPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.MASTER, 0, 0.0f));
		}
		for (int i = 501; i <= 1200; i++) {
			grandmasterPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.GRANDMASTER, 0, 0.0f));
		}
		for (int i = 1201; i <= 1500; i++) {
			challengerPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.CHALLENGER, 0, 0.0f));
		}
		Collections.sort(masterPlayers);
		Collections.sort(grandmasterPlayers);
		Collections.sort(challengerPlayers);
		return new LeaderBoard(queue, challengerPlayers, grandmasterPlayers, masterPlayers);
	}

	/**
	 * Tests whether a leaderboard which contains 20 grandmaster players which are
	 * better than challenger players are correctly reflected as rank up.
	 * Furthermore, the last 20 challenger player should reflect rank down.
	 */
	@Test
	public void testLeaderBoardWithRankUpAndRankDownFromGrandmasterToChallenger() {
		final LeaderBoard leaderBoard = getExampleLeaderBoardWith20RankUpAndRankDownFromGrandmasterToChallenger();
		final List<LoLPlayer> grandmasterPlayers = leaderBoard.getGrandmasterPlayers();
		for (int i = 0; i < 20; i++) {
			final LoLPlayer grandmasterPlayer = grandmasterPlayers.get(i);
			assertTrue(grandmasterPlayer.willRankUp());
			assertFalse(grandmasterPlayer.willRankDown());
		}
		final List<LoLPlayer> challengerPlayers = leaderBoard.getChallengerPlayers();
		for (int i = challengerPlayers.size() - 1; i >= challengerPlayers.size() - 20; i--) {
			final LoLPlayer challengerPlayer = challengerPlayers.get(i);
			assertFalse(challengerPlayer.willRankUp());
			assertTrue(challengerPlayer.willRankDown());
		}

		for (int i = 20; i < 700; i++) {
			final LoLPlayer grandmasterPlayer = grandmasterPlayers.get(i);
			assertFalse(grandmasterPlayer.willRankUp());
			assertFalse(grandmasterPlayer.willRankDown());
		}
		for (int i = 0; i < 300 - 20; i++) {
			final LoLPlayer challengerPlayer = challengerPlayers.get(i);
			assertFalse(challengerPlayer.willRankUp());
			assertFalse(challengerPlayer.willRankDown());
		}
		final List<LoLPlayer> masterPlayers = leaderBoard.getMasterPlayers();
		for (final LoLPlayer masterPlayer : masterPlayers) {
			assertFalse(masterPlayer.willRankUp());
			assertFalse(masterPlayer.willRankDown());
		}
	}

	/**
	 * Returns a similar leaderboard in comparison as the initial one with 1500
	 * players. The difference is that the best 20 grandmaster players have an
	 * amount of lp that is higher than the lp of each individual challenger player.
	 * This means, they should rank up while the last 20 challenger players should
	 * rank down.
	 * 
	 * @return The leaderboard with 1500 players.
	 */
	private static LeaderBoard getExampleLeaderBoardWith20RankUpAndRankDownFromGrandmasterToChallenger() {
		final RankedQueue queue = RankedQueue.SOLO_DUO;
		final List<LoLPlayer> masterPlayers = new ArrayList<>(500);
		final List<LoLPlayer> grandmasterPlayers = new ArrayList<>(700);
		final List<LoLPlayer> challengerPlayers = new ArrayList<>(300);
		for (int i = 1; i <= 500; i++) {
			masterPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.MASTER, 0, 0.0f));
		}
		for (int i = 501; i <= 1180; i++) {
			grandmasterPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.GRANDMASTER, 0, 0.0f));
		}
		for (int i = 1181; i <= 1200; i++) {
			grandmasterPlayers.add(new LoLPlayer("Player " + i, 1300 + i, queue, TopTierElo.GRANDMASTER, 0, 0.0f));
		}
		for (int i = 1201; i <= 1500; i++) {
			challengerPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.CHALLENGER, 0, 0.0f));
		}
		Collections.sort(masterPlayers);
		Collections.sort(grandmasterPlayers);
		Collections.sort(challengerPlayers);
		return new LeaderBoard(queue, challengerPlayers, grandmasterPlayers, masterPlayers);
	}

	/**
	 * Tests whether a leaderboard which contains 20 master players which are better
	 * than grandmaster players are correctly reflected as rank up. Furthermore, the
	 * last 20 grandmaster player should reflect rank down.
	 */
	@Test
	public void testLeaderBoardWithRankUpAndRankDownFromMasterToGrandmaster() {
		final LeaderBoard leaderBoard = getExampleLeaderBoardWith20RankUpAndRankDownFromMasterToGrandmaster();
		final List<LoLPlayer> masterPlayers = leaderBoard.getMasterPlayers();
		for (int i = 0; i < 20; i++) {
			final LoLPlayer masterPlayer = masterPlayers.get(i);
			assertTrue(masterPlayer.willRankUp());
			assertFalse(masterPlayer.willRankDown());
		}
		final List<LoLPlayer> grandmasterPlayers = leaderBoard.getGrandmasterPlayers();
		for (int i = grandmasterPlayers.size() - 1; i >= grandmasterPlayers.size() - 20; i--) {
			final LoLPlayer grandmasterPlayer = grandmasterPlayers.get(i);
			assertFalse(grandmasterPlayer.willRankUp());
			assertTrue(grandmasterPlayer.willRankDown());
		}

		for (int i = 20; i < 500; i++) {
			final LoLPlayer masterPlayer = masterPlayers.get(i);
			assertFalse(masterPlayer.willRankUp());
			assertFalse(masterPlayer.willRankDown());
		}
		for (int i = 0; i < 700 - 20; i++) {
			final LoLPlayer grandmasterPlayer = grandmasterPlayers.get(i);
			assertFalse(grandmasterPlayer.willRankUp());
			assertFalse(grandmasterPlayer.willRankDown());
		}
		final List<LoLPlayer> challengerPlayers = leaderBoard.getChallengerPlayers();
		for (final LoLPlayer challengerPlayer : challengerPlayers) {
			assertFalse(challengerPlayer.willRankUp());
			assertFalse(challengerPlayer.willRankDown());
		}
	}

	/**
	 * Returns a similar leaderboard in comparison as the initial one with 1500
	 * players. The difference is that the best 20 master players have an amount of
	 * lp that is higher than the lp of each individual challenger play. This means,
	 * they should rank up while the last 20 grandmaster players should rank down.
	 * 
	 * @return The leaderboard with 1500 players.
	 */
	private static LeaderBoard getExampleLeaderBoardWith20RankUpAndRankDownFromMasterToGrandmaster() {
		final RankedQueue queue = RankedQueue.SOLO_DUO;
		final List<LoLPlayer> masterPlayers = new ArrayList<>(500);
		final List<LoLPlayer> grandmasterPlayers = new ArrayList<>(700);
		final List<LoLPlayer> challengerPlayers = new ArrayList<>(300);
		for (int i = 1; i <= 480; i++) {
			masterPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.MASTER, 0, 0.0f));
		}
		for (int i = 481; i <= 500; i++) {
			masterPlayers.add(new LoLPlayer("Player " + i, 700 + i, queue, TopTierElo.MASTER, 0, 0.0f));
		}
		for (int i = 501; i <= 1200; i++) {
			grandmasterPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.GRANDMASTER, 0, 0.0f));
		}
		for (int i = 1201; i <= 1500; i++) {
			challengerPlayers.add(new LoLPlayer("Player " + i, i, queue, TopTierElo.CHALLENGER, 0, 0.0f));
		}
		Collections.sort(masterPlayers);
		Collections.sort(grandmasterPlayers);
		Collections.sort(challengerPlayers);
		return new LeaderBoard(queue, challengerPlayers, grandmasterPlayers, masterPlayers);
	}
}
