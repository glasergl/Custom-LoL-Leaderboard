package riotApiConnection;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONObject;

import leaderBoard.LoLPlayer;
import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * This class makes all necessary queries to retrieve all leaderboard players
 * for Solo/Duo and Flex. Furthermore, this class also stores the time where the
 * particular state has been observed.
 * 
 * @author Gabriel Glaser
 */
public final class RiotAPIQueryForAllTopTierPlayers {
	private final List<LoLPlayer> soloDuoChallengerPlayers;
	private final List<LoLPlayer> soloDuoGrandmasterPlayers;
	private final List<LoLPlayer> soloDuoMasterPlayers;
	private final List<LoLPlayer> flexChallengerPlayers;
	private final List<LoLPlayer> flexGrandmasterPlayers;
	private final List<LoLPlayer> flexMasterPlayers;
	private final Calendar queryFinishTime;

	public RiotAPIQueryForAllTopTierPlayers() throws IOException {
		super();
		this.soloDuoChallengerPlayers = retrievePlayersWithRiotAPI(RankedQueue.SOLO_DUO, TopTierElo.CHALLENGER);
		this.soloDuoGrandmasterPlayers = retrievePlayersWithRiotAPI(RankedQueue.SOLO_DUO, TopTierElo.GRANDMASTER);
		this.soloDuoMasterPlayers = retrievePlayersWithRiotAPI(RankedQueue.SOLO_DUO, TopTierElo.MASTER);
		this.flexChallengerPlayers = retrievePlayersWithRiotAPI(RankedQueue.FLEX, TopTierElo.CHALLENGER);
		this.flexGrandmasterPlayers = retrievePlayersWithRiotAPI(RankedQueue.FLEX, TopTierElo.GRANDMASTER);
		this.flexMasterPlayers = retrievePlayersWithRiotAPI(RankedQueue.FLEX, TopTierElo.MASTER);
		this.queryFinishTime = Calendar.getInstance();
		Collections.sort(soloDuoChallengerPlayers);
		Collections.sort(soloDuoGrandmasterPlayers);
		Collections.sort(soloDuoMasterPlayers);
		Collections.sort(flexChallengerPlayers);
		Collections.sort(flexGrandmasterPlayers);
		Collections.sort(flexMasterPlayers);
	}

	private List<LoLPlayer> retrievePlayersWithRiotAPI(final RankedQueue queue, final TopTierElo elo)
			throws IOException {
		final RiotAPIQueryForPlayers riotAPIConnection = new RiotAPIQueryForPlayers(queue, elo);
		final JSONObject queryResult = riotAPIConnection.getResult();
		final LeaderBoardJSONResultParser apiResultParser = new LeaderBoardJSONResultParser(queryResult, queue, elo);
		return apiResultParser.getPlayers();
	}

	public List<LoLPlayer> getSoloDuoChallengerPlayers() {
		return soloDuoChallengerPlayers;
	}

	public List<LoLPlayer> getSoloDuoGrandmasterPlayers() {
		return soloDuoGrandmasterPlayers;
	}

	public List<LoLPlayer> getSoloDuoMasterPlayers() {
		return soloDuoMasterPlayers;
	}

	public List<LoLPlayer> getFlexChallengerPlayers() {
		return flexChallengerPlayers;
	}

	public List<LoLPlayer> getFlexGrandmasterPlayers() {
		return flexGrandmasterPlayers;
	}

	public List<LoLPlayer> getFlexMasterPlayers() {
		return flexMasterPlayers;
	}

	/**
	 * @return The time the query finished in a string formatted with date, time and
	 *         timezone.
	 */
	public String getQueryFinishTime() {
		final String year = fillTo2Characters(queryFinishTime.get(Calendar.YEAR));
		final String month = fillTo2Characters(queryFinishTime.get(Calendar.MONTH) + 1);
		final String day = fillTo2Characters(queryFinishTime.get(Calendar.DAY_OF_MONTH));
		final String hour = fillTo2Characters(queryFinishTime.get(Calendar.HOUR_OF_DAY));
		final String minute = fillTo2Characters(queryFinishTime.get(Calendar.MINUTE));
		final TimeZone timeZone = queryFinishTime.getTimeZone();
		final String timeZoneAbbreviation = timeZone.getDisplayName(false, TimeZone.SHORT,
				Locale.getDefault(Locale.Category.DISPLAY));
		return year + "-" + month + "-" + day + ", " + hour + ":" + minute + "/" + timeZoneAbbreviation;
	}

	/**
	 * @param number
	 * @return String which represents the given number. If the number has only one
	 *         digit, then a 0 is prepended.
	 */
	private String fillTo2Characters(final int number) {
		if (number < 10) {
			return "0" + number;
		} else {
			return String.valueOf(number);
		}
	}
}
