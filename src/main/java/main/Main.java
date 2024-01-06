package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import leaderBoard.LeaderBoard;
import leaderBoard.RankedQueue;
import riotApiConnection.RiotAPIQueryForAllTopTierPlayers;
import ui.MainFrame;

/**
 * Class which main method starts the program.
 * 
 * @author Gabriel Glaser
 */
public final class Main {
	private static final String ERROR_FILE_NAME = "error.log";

	/**
	 * First, the Riot API is queried for all players which should be depicted in
	 * the gui (challenger, grandmaster, master players for solo/duo and flex
	 * queue). After that, the results are shown via Swing.
	 * 
	 * @param args - unused.
	 * @throws IOException which will be passed to the custom
	 *                     UncaughtExceptionHandler.
	 */
	public static void main(final String[] args) throws IOException {
		Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
			throwable.printStackTrace();
			storeErrorInFile(throwable);
			showError(throwable);
		});
		final RiotAPIQueryForAllTopTierPlayers playersAPIQuery = new RiotAPIQueryForAllTopTierPlayers();
		final LeaderBoard soloDuoPlayers = new LeaderBoard(RankedQueue.SOLO_DUO,
				playersAPIQuery.getSoloDuoChallengerPlayers(), playersAPIQuery.getSoloDuoGrandmasterPlayers(),
				playersAPIQuery.getSoloDuoMasterPlayers());
		final LeaderBoard flexPlayers = new LeaderBoard(RankedQueue.FLEX, playersAPIQuery.getFlexChallengerPlayers(),
				playersAPIQuery.getFlexGrandmasterPlayers(), playersAPIQuery.getFlexMasterPlayers());
		SwingUtilities.invokeLater(() -> {
			new MainFrame(soloDuoPlayers, flexPlayers, playersAPIQuery.getQueryFinishTime());
		});
	}

	/**
	 * Stores the stacktrace of a given Throwable in an error log file.
	 * 
	 * @param throwable
	 */
	private static void storeErrorInFile(final Throwable throwable) {
		try {
			final PrintWriter errorLogFileWriter = new PrintWriter(ERROR_FILE_NAME);
			throwable.printStackTrace(errorLogFileWriter);
			errorLogFileWriter.flush();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Depicts the given throwable in a JOptionPane.
	 * 
	 * @param throwable
	 */
	private static void showError(final Throwable throwable) {
		JOptionPane.showMessageDialog(null, throwable.getClass().getName() + "\nCheck file \"" + ERROR_FILE_NAME
				+ "\" for more details. Don't show the error file in public, because it may contain your api key!",
				"Error", JOptionPane.ERROR_MESSAGE);
	}
}
