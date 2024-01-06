package ui;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import leaderBoard.LoLPlayer;
import leaderBoard.TopTierElo;

/**
 * This class shows all given LoLPlayer objects in a single column. If the elo
 * of the given players is challenger, the first 10 players are highlighted.
 * 
 * @author glasergl
 */
public class LoLPlayersUI extends JPanel {
	private static final int SEPARATOR_HEIGHT = 5;
	private static final int TOP_10_SEPARATOR = 30;
	private static final int LEFT_RIGHT_MARGIN = 5;
	private static final int BOTTOM_MARGIN = 150;

	private final TopTierElo elo;
	private final List<LoLPlayer> lolPlayers;

	public LoLPlayersUI(final TopTierElo elo, final List<LoLPlayer> lolPlayers) {
		super();
		this.elo = elo;
		this.lolPlayers = lolPlayers;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		createLoLPlayerUIs();
	}

	private void createLoLPlayerUIs() {
		for (int rankIndex = 0; rankIndex < lolPlayers.size(); rankIndex++) {
			final LoLPlayer lolPlayer = lolPlayers.get(rankIndex);
			final LoLPlayerUI lolPlayerUI = new LoLPlayerUI(rankIndex, lolPlayer);
			final JPanel wrapperToRespectPreferredSize = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
			wrapperToRespectPreferredSize.add(lolPlayerUI);
			add(wrapperToRespectPreferredSize);
			if (rankIndex == 9 && elo == TopTierElo.CHALLENGER) {
				lolPlayerUI.setBorder(BorderFactory.createEmptyBorder(SEPARATOR_HEIGHT, LEFT_RIGHT_MARGIN,
						TOP_10_SEPARATOR, LEFT_RIGHT_MARGIN));
			} else if (rankIndex < lolPlayers.size() - 1) {
				lolPlayerUI.setBorder(
						BorderFactory.createEmptyBorder(SEPARATOR_HEIGHT, LEFT_RIGHT_MARGIN, 0, LEFT_RIGHT_MARGIN));
			} else {
				lolPlayerUI.setBorder(BorderFactory.createEmptyBorder(SEPARATOR_HEIGHT, LEFT_RIGHT_MARGIN,
						BOTTOM_MARGIN, LEFT_RIGHT_MARGIN));
			}
		}
	}
}
