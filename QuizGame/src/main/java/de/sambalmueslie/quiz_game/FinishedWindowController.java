package de.sambalmueslie.quiz_game;

import de.sambalmueslie.quiz_game.controller.GameFinishedReason;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FinishedWindowController {
	/**
	 * Setup.
	 *
	 * @param reason
	 *            the {@link GameFinishedReason}
	 * @param prize
	 *            the prize
	 */
	public void setup(final GameFinishedReason reason, final int prize) {
		if (reason == null) return;
		switch (reason) {
		case WON:
		case EXIT:
			result.setText("Herzlichen Glückwunsch");
			break;
		case TIMEOUT:
			result.setText("Leider zu langsam");
			break;
		case WRONG_ANSWER:
			result.setText("Leider falsche Antwort");
			break;
		default:
			break;
		}
		win.setText("Ihr Gewinn beträgt: " + prize + " €");
	}

	@FXML
	private Label result;
	@FXML
	private Label win;
}
