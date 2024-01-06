package ui;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import leaderBoard.RankedQueue;
import leaderBoard.TopTierElo;

/**
 * Visual representation of a control panel to decide which players of which
 * queue should be visible.
 * 
 * @author Gabriel Glaser
 */
public final class QueueAndEloControl extends JPanel {
	private static final RankedQueue DEFAULT_INITIALLY_SELECTED_QUEUE = RankedQueue.SOLO_DUO;
	private static final TopTierElo DEFAULT_INITIALLY_SELECTED_ELO = TopTierElo.CHALLENGER;

	private final JButton soloDuoRankedButton = CustomizedSwing.getJButton("Solo/Duo");
	private final JButton flexRankedButton = CustomizedSwing.getJButton("Flex");
	private final JButton challengerButton = CustomizedSwing.getJButton("Challenger");
	private final JButton grandmasterButton = CustomizedSwing.getJButton("Grandmaster");
	private final JButton masterButton = CustomizedSwing.getJButton("Master");
	private final QueueAndEloDisplay queueAndEloDisplay;
	private final AllLeaderBoardsUI multiLeaderBoardUI;

	private RankedQueue selectedQueue = DEFAULT_INITIALLY_SELECTED_QUEUE;
	private TopTierElo selectedElo = DEFAULT_INITIALLY_SELECTED_ELO;

	public QueueAndEloControl(final QueueAndEloDisplay queueAndEloDisplay,
			final AllLeaderBoardsUI multiLeaderBoardUI) {
		super();
		this.queueAndEloDisplay = queueAndEloDisplay;
		this.multiLeaderBoardUI = multiLeaderBoardUI;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addControlButtons();
		addActionListenersToButtonsForControlOnClick();
		updateUIAccordingToSelectedQueueAndElo();
	}

	private void addControlButtons() {
		final JPanel queueButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		queueButtonsPanel.add(soloDuoRankedButton);
		queueButtonsPanel.add(flexRankedButton);

		final JPanel eloButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		eloButtonsPanel.add(challengerButton);
		eloButtonsPanel.add(grandmasterButton);
		eloButtonsPanel.add(masterButton);

		add(queueButtonsPanel);
		add(eloButtonsPanel);
	}

	private void updateUIAccordingToSelectedQueueAndElo() {
		setButtonsEnabledAccordingToSelectedQueueAndElo();
		queueAndEloDisplay.updateLabels(selectedQueue, selectedElo);
		multiLeaderBoardUI.showPlayersOfQueueAndElo(selectedQueue, selectedElo);
	}

	private void setButtonsEnabledAccordingToSelectedQueueAndElo() {
		soloDuoRankedButton.setEnabled(selectedQueue != RankedQueue.SOLO_DUO);
		flexRankedButton.setEnabled(selectedQueue != RankedQueue.FLEX);
		challengerButton.setEnabled(selectedElo != TopTierElo.CHALLENGER);
		grandmasterButton.setEnabled(selectedElo != TopTierElo.GRANDMASTER);
		masterButton.setEnabled(selectedElo != TopTierElo.MASTER);
	}

	private void addActionListenersToButtonsForControlOnClick() {
		soloDuoRankedButton.addActionListener((click) -> {
			selectedQueue = RankedQueue.SOLO_DUO;
			updateUIAccordingToSelectedQueueAndElo();
		});
		flexRankedButton.addActionListener((click) -> {
			selectedQueue = RankedQueue.FLEX;
			updateUIAccordingToSelectedQueueAndElo();
		});
		challengerButton.addActionListener((click) -> {
			selectedElo = TopTierElo.CHALLENGER;
			updateUIAccordingToSelectedQueueAndElo();
		});
		grandmasterButton.addActionListener((click) -> {
			selectedElo = TopTierElo.GRANDMASTER;
			updateUIAccordingToSelectedQueueAndElo();
		});
		masterButton.addActionListener((click) -> {
			selectedElo = TopTierElo.MASTER;
			updateUIAccordingToSelectedQueueAndElo();
		});
	}
}
