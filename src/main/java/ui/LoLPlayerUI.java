package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import leaderBoard.LoLPlayer;
import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * Class which depicts all properties of a given LoLPlayer object.
 * 
 * @author Gabriel Glaser
 */
public final class LoLPlayerUI extends JPanel {
	private static final int PREFERRED_HEIGHT = 30;
	private static final Dimension PREFERRED_SIZE_OF_RANK_LABEL = new Dimension(100, PREFERRED_HEIGHT);
	private static final Dimension PREFERRED_SIZE_OF_SUMMONER_NAME_LABEL = new Dimension(200, PREFERRED_HEIGHT);
	private static final Dimension PREFERRED_SIZE_OF_WIN_RATE_GAMES_LABEL = new Dimension(160, PREFERRED_HEIGHT);
	private static final Dimension PREFERRED_SIZE_OF_LP_LABEL = new Dimension(75, PREFERRED_HEIGHT);
	private static final Dimension PREFERRED_SIZE_OF_RANK_UP_DOWN_LABEL = new Dimension(50, PREFERRED_HEIGHT);

	private final JLabel rankIndexLabel;
	private final JLabel summonerNameLabel;
	private final JLabel rankUpDownLabel;
	private final JLabel lpLabel;
	private final JLabel winRateAndGamesLabel;

	public LoLPlayerUI(final int relativeRankInElo, final LoLPlayer lolPlayer) {
		super();
		this.rankIndexLabel = CustomizedSwing.getJLabel(getRankIndex(lolPlayer, relativeRankInElo) + ".");
		this.summonerNameLabel = CustomizedSwing.getJLabel(lolPlayer.getSummonerName());
		this.lpLabel = CustomizedSwing.getJLabel(String.valueOf(lolPlayer.getLeaguePoints()));
		final String winRate = String.valueOf((float) Math.round(lolPlayer.getWinRate() * 100 * 10) / 10);
		this.winRateAndGamesLabel = CustomizedSwing.getJLabel(winRate + "%/" + lolPlayer.getNumberOfGames());
		if (lolPlayer.willRankUp()) {
			rankUpDownLabel = CustomizedSwing.getJLabel(Images.RANK_UP_ICON);
		} else if (lolPlayer.willRankDown()) {
			rankUpDownLabel = CustomizedSwing.getJLabel(Images.RANK_DOWN_ICON);
		} else {
			rankUpDownLabel = new JLabel();
		}
		setup();
	}

	private void setup() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		setupLabels();
		add(rankIndexLabel);
		add(summonerNameLabel);
		add(winRateAndGamesLabel);
		add(lpLabel);
		add(rankUpDownLabel);
	}

	private int getRankIndex(final LoLPlayer lolPlayer, final int relativeRankInElo) {
		final int maximumChallengerPlayers = lolPlayer.getQueue() == RankedQueue.SOLO_DUO ? 300 : 200;
		final int maximumGrandmasterPlusPlayers = lolPlayer.getQueue() == RankedQueue.SOLO_DUO ? 1000 : 700;
		if (lolPlayer.getElo() == TopTierElo.GRANDMASTER) {
			return relativeRankInElo + maximumChallengerPlayers + 1;
		} else if (lolPlayer.getElo() == TopTierElo.MASTER) {
			return relativeRankInElo + maximumGrandmasterPlusPlayers + 1;
		} else {
			return relativeRankInElo + 1;
		}
	}

	private void setupLabels() {
		rankIndexLabel.setPreferredSize(PREFERRED_SIZE_OF_RANK_LABEL);
		summonerNameLabel.setPreferredSize(PREFERRED_SIZE_OF_SUMMONER_NAME_LABEL);
		rankUpDownLabel.setPreferredSize(PREFERRED_SIZE_OF_RANK_UP_DOWN_LABEL);
		lpLabel.setPreferredSize(PREFERRED_SIZE_OF_LP_LABEL);
		winRateAndGamesLabel.setPreferredSize(PREFERRED_SIZE_OF_WIN_RATE_GAMES_LABEL);
	}
}
