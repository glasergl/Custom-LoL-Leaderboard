package ui;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import leaderBoard.LeaderBoard;

/**
 * JFrame which wraps up all needed components to display the leaderboard and
 * control its content.
 * 
 * @author glasergl
 */
public class MainFrame extends JFrame {
	private static final String FRAME_TITLE = "Custom LoL Leaderboard";

	public MainFrame(final LeaderBoard soloDuoPlayers, final LeaderBoard flexPlayers,
			final String queryFinishTime) {
		super(FRAME_TITLE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setIconImage(Images.FRAME_ICON);

		final QueueAndEloDisplay queueAndEloDisplay = new QueueAndEloDisplay(soloDuoPlayers, flexPlayers,
				queryFinishTime);
		final AllLeaderBoardsUI multiLeaderBoardUI = new AllLeaderBoardsUI(soloDuoPlayers, flexPlayers);
		final QueueAndEloControl queueAndEloControl = new QueueAndEloControl(queueAndEloDisplay, multiLeaderBoardUI);
		final Container contentPane = getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.add(queueAndEloControl);
		contentPane.add(queueAndEloDisplay);
		contentPane.add(multiLeaderBoardUI);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
