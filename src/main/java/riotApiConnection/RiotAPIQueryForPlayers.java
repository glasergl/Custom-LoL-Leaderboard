package riotApiConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * This class makes an HTTP GET request upon instantiated to retrieve all
 * players of the given queue and elo. The result is available as a JSONObject.
 * 
 * @author Gabriel Glaser
 */
public final class RiotAPIQueryForPlayers {
	private static final String PROTOCOL_AND_DOMAIN = "https://euw1.api.riotgames.com";
	private static final String API = "/lol/league/v4/";

	private final RankedQueue queue;
	private final TopTierElo elo;
	private final JSONObject result;

	public RiotAPIQueryForPlayers(final RankedQueue queue, final TopTierElo elo) throws IOException {
		super();
		this.queue = queue;
		this.elo = elo;
		this.result = queryForResult();
	}

	private JSONObject queryForResult() throws IOException {
		final URL queryURL = new URL(PROTOCOL_AND_DOMAIN + getQueryString() + "?" + getAPIKeyField());
		final HttpURLConnection connection = (HttpURLConnection) queryURL.openConnection();
		final InputStreamReader reader = new InputStreamReader(connection.getInputStream());
		final BufferedReader bufferedReader = new BufferedReader(reader);
		final String queryResult = bufferedReader.readLine();
		bufferedReader.close();
		connection.disconnect();
		return new JSONObject(queryResult.toString());
	}

	private String getAPIKeyField() {
		final String API_KEY = System.getenv("RIOT_API_KEY_CUSTOM_LADDER");
		return "api_key=" + API_KEY;
	}

	private String getQueryString() {
		return API + getQueryStringForElo(elo) + "/by-queue/" + getQueryStringForQueue(queue);
	}

	private String getQueryStringForElo(final TopTierElo eloType) {
		if (eloType == TopTierElo.CHALLENGER) {
			return "challengerleagues";
		} else if (eloType == TopTierElo.GRANDMASTER) {
			return "grandmasterleagues";
		} else {
			return "masterleagues";
		}
	}

	private String getQueryStringForQueue(final RankedQueue queueType) {
		if (queueType == RankedQueue.SOLO_DUO) {
			return "RANKED_SOLO_5x5";
		} else {
			return "RANKED_FLEX_SR";
		}
	}

	public JSONObject getResult() {
		return result;
	}
}
