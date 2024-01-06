package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import leaderBoard.LeaderBoard;
import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * Visual representation of a given queue and elo. Also contains further
 * elements like the time the leaderboard was extracted from Riot or what are
 * the minimum LP to promote to a certain elo.
 * 
 * @author Gabriel Glaser
 */
public class QueueAndEloDisplay extends JPanel {
	private final LeaderBoard soloDuoPlayers;
	private final LeaderBoard flexPlayers;
	private final JLabel eloIconLabel = CustomizedSwing.getJLabel();
	private final JLabel leaderboardTitleLabel = CustomizedSwing.getJLabel();
	private final JLabel queryFinishTimeLabel = CustomizedSwing.getJLabel();
	private final JLabel boundaryLabel = CustomizedSwing.getJLabel();

	public QueueAndEloDisplay(final LeaderBoard soloDuoPlayers, final LeaderBoard flexPlayers,
			final String queryFinishTime) {
		super();
		this.soloDuoPlayers = soloDuoPlayers;
		this.flexPlayers = flexPlayers;
		setup(queryFinishTime);
	}

	public void updateLabels(final RankedQueue queue, final TopTierElo elo) {
		leaderboardTitleLabel.setText(queue.toString() + " - " + elo.toString());
		if (elo == TopTierElo.CHALLENGER) {
			boundaryLabel.setText("");
			eloIconLabel.setIcon(Images.CHALLENGER_ICON);
		} else if (elo == TopTierElo.GRANDMASTER) {
			final int challengerCutOff = queue == RankedQueue.SOLO_DUO ? soloDuoPlayers.getChallengerLPCutOff()
					: flexPlayers.getChallengerLPCutOff();
			boundaryLabel.setText("Minimum LP for Challenger: " + challengerCutOff);
			eloIconLabel.setIcon(Images.GRANDMASTER_ICON);
		} else {
			final int grandmasterCutOff = queue == RankedQueue.SOLO_DUO ? soloDuoPlayers.getGrandmasterLPCutOff()
					: flexPlayers.getGrandmasterLPCutOff();
			boundaryLabel.setText("Minimum LP for Grandmaster: " + grandmasterCutOff);
			eloIconLabel.setIcon(Images.MASTER_ICON);
		}
	}

	private void setup(final String queryFinishTime) {
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		final JPanel leaderboardTitleLabelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		leaderboardTitleLabelWrapper.add(leaderboardTitleLabel);
		leaderboardTitleLabelWrapper.add(eloIconLabel);
		add(leaderboardTitleLabelWrapper);

		final JPanel dateLabelWrapper = new JPanel();
		dateLabelWrapper.add(queryFinishTimeLabel);
		add(dateLabelWrapper);

		final JPanel boundaryLabelWrapper = new JPanel();
		boundaryLabelWrapper.add(boundaryLabel);
		add(boundaryLabelWrapper);
		setupLabels();
		queryFinishTimeLabel.setText(queryFinishTime);
	}

	private void setupLabels() {
		leaderboardTitleLabel.setFont(leaderboardTitleLabel.getFont().deriveFont(Font.BOLD).deriveFont(45.0f));
		boundaryLabel.setPreferredSize(new Dimension(400, 30));
		boundaryLabel.setFont(boundaryLabel.getFont().deriveFont(Font.ITALIC));
		eloIconLabel.setPreferredSize(new Dimension(175, 175));
	}

}
