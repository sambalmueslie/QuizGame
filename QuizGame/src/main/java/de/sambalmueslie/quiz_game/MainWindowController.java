package de.sambalmueslie.quiz_game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MainWindowController {
	@FXML
	public void buttonPressed(final ActionEvent event) {
		final Button button = (Button) event.getSource();

		if (button == answerA) {
			answerA.setStyle("-fx-background-image: url('answer2.png');");
		} else if (button == answerB) {
			answerB.setStyle("-fx-background-image: url('answer3.png');");
		} else if (button == answerC) {
			answerC.setStyle("-fx-background-image: url('answer4.png');");
		} else if (button == answerD) {
			answerA.setStyle("-fx-background-image: url('answer1.png');");
			answerB.setStyle("-fx-background-image: url('answer1.png');");
			answerC.setStyle("-fx-background-image: url('answer1.png');");
		}
	}

	@FXML
	private Button answerA;
	@FXML
	private Button answerB;
	@FXML
	private Button answerC;
	@FXML
	private Button answerD;
	@FXML
	private Label clock;
	@FXML
	private ListView<String> index;
	@FXML
	private Label lifelineAudience;
	@FXML
	private Label lifelineFiftyFifty;
	@FXML
	private Label lifelineTel;
	@FXML
	private Label question;
}
