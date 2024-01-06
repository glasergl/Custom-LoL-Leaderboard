package ui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import leaderBoard.LeaderBoard;
import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * Visual representation of all leaderboards with a CardLayout such that each
 * individual can be shown without having to remove and add them over and over
 * again. Note that, adding a big leaderboard, e.g., 10k+ players, needs long
 * processing by swing.
 * 
 * @author Gabriel Glaser
 */
public final class AllLeaderBoardsUI extends JPanel {
	private static final Dimension PREFERRED_SIZE_OF_LEADER_BOARD_UI = new Dimension(800, 600);

	private final CardLayout cardLayout = new CardLayout();

	public AllLeaderBoardsUI(final LeaderBoard soloDuoPlayers, final LeaderBoard flexPlayers) {
		super();
		setLayout(cardLayout);
		final LoLPlayersUI soloDuoChallengerPlayersUI = new LoLPlayersUI(TopTierElo.CHALLENGER,
				soloDuoPlayers.getChallengerPlayers());
		final LoLPlayersUI soloDuoGrandmasterPlayersUI = new LoLPlayersUI(TopTierElo.GRANDMASTER,
				soloDuoPlayers.getGrandmasterPlayers());
		final LoLPlayersUI soloDuoMasterPlayersUI = new LoLPlayersUI(TopTierElo.MASTER,
				soloDuoPlayers.getMasterPlayers());
		final LoLPlayersUI flexChallengerPlayersUI = new LoLPlayersUI(TopTierElo.CHALLENGER,
				flexPlayers.getChallengerPlayers());
		final LoLPlayersUI flexGrandmasterPlayersUI = new LoLPlayersUI(TopTierElo.GRANDMASTER,
				flexPlayers.getGrandmasterPlayers());
		final LoLPlayersUI flexMasterPlayersUI = new LoLPlayersUI(TopTierElo.MASTER, flexPlayers.getMasterPlayers());

		add(createScrollWrapper(soloDuoChallengerPlayersUI),
				RankedQueue.SOLO_DUO.toString() + TopTierElo.CHALLENGER.toString());
		add(createScrollWrapper(soloDuoGrandmasterPlayersUI),
				RankedQueue.SOLO_DUO.toString() + TopTierElo.GRANDMASTER.toString());
		add(createScrollWrapper(soloDuoMasterPlayersUI),
				RankedQueue.SOLO_DUO.toString() + TopTierElo.MASTER.toString());
		add(createScrollWrapper(flexChallengerPlayersUI),
				RankedQueue.FLEX.toString() + TopTierElo.CHALLENGER.toString());
		add(createScrollWrapper(flexGrandmasterPlayersUI),
				RankedQueue.FLEX.toString() + TopTierElo.GRANDMASTER.toString());
		add(createScrollWrapper(flexMasterPlayersUI), RankedQueue.FLEX.toString() + TopTierElo.MASTER.toString());
	}

	public void showPlayersOfQueueAndElo(final RankedQueue queue, final TopTierElo elo) {
		cardLayout.show(this, queue.toString() + elo.toString());
	}

	private JScrollPane createScrollWrapper(final LoLPlayersUI leaderBoardUI) {
		final JScrollPane scrollPaneWrapper = CustomizedSwing.wrapInJScrollPane(leaderBoardUI);
		scrollPaneWrapper.setPreferredSize(PREFERRED_SIZE_OF_LEADER_BOARD_UI);
		final JScrollBar verticalScrollBar = scrollPaneWrapper.getVerticalScrollBar();
		verticalScrollBar.setUnitIncrement(20);
		return scrollPaneWrapper;
	}
}
