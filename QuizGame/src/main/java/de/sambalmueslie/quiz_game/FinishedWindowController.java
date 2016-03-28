package de.sambalmueslie.quiz_game;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FinishedWindowController {
	/**
	 * Setup.
	 *
	 * @param won
	 *            is won
	 * @param timeout
	 *            is timeout
	 * @param prize
	 *            the prize
	 */
	public void setup(final boolean won, final boolean timeout, final int prize) {
		if (won) {
			result.setText("Herzlichen Glückwunsch");
		} else if (timeout) {
			result.setText("Leider zu langsam");
		} else {
			result.setText("Leider falsche Antwort");
		}
		win.setText("Ihr Gewinn beträgt: " + prize + " €");
	}

	@FXML
	private Label result;
	@FXML
	private Label win;
}
