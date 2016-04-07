package de.sambalmueslie.quiz_game;

import java.net.URL;
import java.util.ResourceBundle;

import de.sambalmueslie.quiz_game.controller.GameController;
import de.sambalmueslie.quiz_game.data.*;
import de.sambalmueslie.quiz_game.view.IndexListCell;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.util.Duration;

public class MainWindowController implements Initializable {

	/**
	 * Constructor.
	 */
	public MainWindowController() {
		final Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> mainLoop()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	@FXML
	public void buttonPressed(final ActionEvent event) {
		final Button button = (Button) event.getSource();

		int answer = -1;
		if (button == answerA) {
			answer = 0;
		} else if (button == answerB) {
			answer = 1;
		} else if (button == answerC) {
			answer = 2;
		} else if (button == answerD) {
			answer = 3;
		}

		if (answer < 0) return;

		gameController.handleAnswer(answer);
		updateQuestionAndAnswers();
		event.consume();
	}

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		answerA.setFocusTraversable(false);
		answerB.setFocusTraversable(false);
		answerC.setFocusTraversable(false);
		answerD.setFocusTraversable(false);
		index.setCellFactory(listView -> new IndexListCell());
	}

	void handleKeyTyped(final KeyEvent e) {
		if (e.getCode() == KeyCode.SPACE || e.getCharacter().equals(" ")) {
			gameController.handleUserInteraction();
			updateQuestionAndAnswers();
		} else if (e.getCode() == KeyCode.ESCAPE) {
			System.exit(0);
		} else if (e.getCode() == KeyCode.E || e.getCharacter().equals("e")) {
			gameController.handleExitGame();
		}

		e.consume();
	}

	/**
	 * @param gameController
	 *            the gameController to set
	 */
	void setGameController(final GameController gameController) {
		this.gameController = gameController;
	}

	/**
	 * Set the {@link Model}.
	 *
	 * @param model
	 *            the model
	 */
	void setModel(final Model model) {
		this.model = model;
		gameController.setModel(model);

		final ObservableList<Index> items = index.getItems();
		items.addAll(model.getIndexs());

		updateQuestionAndAnswers();
	}

	private Button createLifeLineBtn(final LifeLine lifeLine) {
		final String text = lifeLine.getType() == LifeLineType.AUDIENCE ? "Pub" : "50:50";
		final Button button = new Button(text);
		button.setAlignment(Pos.CENTER);
		button.setPrefHeight(100.0);
		button.setPrefWidth(100.0);
		button.setOnAction(e -> reqUsageLifeLine(e, lifeLine));
		button.getStyleClass().add("lbl_lifeline");
		button.setDisable(false);
		return button;
	}

	/**
	 * The main loop of the game.
	 */
	private void mainLoop() {
		if (gameController == null) return;
		gameController.handleGameLoop();
		clock.setText(Integer.toString(gameController.getRemainingTime()));
		updateQuestionAndAnswers();
	}

	private void reqUsageLifeLine(final ActionEvent e, final LifeLine lifeLine) {
		gameController.requestLifeLineUsage(lifeLine);
	}

	/**
	 * Set the answer {@link Button}.
	 *
	 * @param answerButton
	 *            the button
	 * @param answer
	 *            the {@link Answer}
	 */
	private void setAnswer(final Button answerButton, final Answer answer) {
		if (answer == null) {
			answerButton.setText(null);
			answerButton.setStyle("-fx-background-image: url('answer1.png');");
			return;
		}
		if (!answer.isVisible()) {
			answerButton.setText(null);
		} else {
			final String text = answer.getPrefix() + ": " + answer.getText();
			answerButton.setText(text);
		}
		switch (answer.getState()) {
		case IDLE:
			answerButton.setStyle("-fx-background-image: url('answer1.png');");
			break;
		case SELECTED:
			answerButton.setStyle("-fx-background-image: url('answer2.png');");
			break;
		case WRONG:
			answerButton.setStyle("-fx-background-image: url('answer3.png');");
			break;
		case RIGHT:
			answerButton.setStyle("-fx-background-image: url('answer4.png');");
			break;
		default:
			break;
		}

	}

	private void updateQuestionAndAnswers() {
		question.setText(gameController.getQuestionText());
		setAnswer(answerA, gameController.getAnswer(0));
		setAnswer(answerB, gameController.getAnswer(1));
		setAnswer(answerC, gameController.getAnswer(2));
		setAnswer(answerD, gameController.getAnswer(3));

		index.getSelectionModel().select(gameController.getCurrentIndex());
		lifelinePanel.getChildren().clear();
		model.getLifeLines().stream().filter(LifeLine::isAvailable).map(this::createLifeLineBtn).forEach(lifelinePanel.getChildren()::add);
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
	/** the {@link GameController}. */
	private GameController gameController;
	@FXML
	private ListView<Index> index;
	@FXML
	private FlowPane lifelinePanel;
	private Model model;
	@FXML
	private Label question;
}
