package riotApiConnection;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import leaderBoard.LoLPlayer;
import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * This class will parse the resulted JSONObject of players of a single queue
 * and elo from the Riot API into a list of the custom datatype LoLPlayer.
 * 
 * @author glasergl
 */
public final class LeaderBoardJSONResultParser {
	private final JSONObject result;
	private final RankedQueue queue;
	private final TopTierElo elo;
	private final List<LoLPlayer> players;

	public LeaderBoardJSONResultParser(final JSONObject result, final RankedQueue queue, final TopTierElo elo) {
		super();
		this.result = result;
		this.queue = queue;
		this.elo = elo;
		this.players = new ArrayList<>();
		extractPlayers();
	}

	private void extractPlayers() {
		final JSONArray playerEntries = result.getJSONArray("entries");
		for (int i = 0; i < playerEntries.length(); i++) {
			final JSONObject playerEntry = playerEntries.getJSONObject(i);
			final LoLPlayer player = extractLoLPlayer(playerEntry);
			players.add(player);
		}

	}

	private LoLPlayer extractLoLPlayer(final JSONObject playerEntry) {
		final String summonerName = playerEntry.getString("summonerName");
		final int leaguePoints = playerEntry.getInt("leaguePoints");
		final int numberOfWins = playerEntry.getInt("wins");
		final int numberOfLoses = playerEntry.getInt("losses");
		final int numberOfGames = numberOfWins + numberOfLoses;
		return new LoLPlayer(summonerName, leaguePoints, queue, elo, numberOfGames,
				(float) numberOfWins / (float) numberOfGames);
	}

	public List<LoLPlayer> getPlayers() {
		return players;
	}
}
