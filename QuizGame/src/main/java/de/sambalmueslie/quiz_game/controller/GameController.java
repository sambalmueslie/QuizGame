package de.sambalmueslie.quiz_game.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.sambalmueslie.quiz_game.data.*;

public class GameController {
	private static final int DEFAULT_REMAINING_TIME = 60;
	private static Logger logger = LogManager.getLogger(GameController.class);

	public Answer getAnswer(final int index) {
		if (currentQuestion == null) return null;
		final List<Answer> answers = currentQuestion.getAnswers();
		if (index >= answers.size()) return null;
		return answers.get(index);
	}

	public Index getCurrentIndex() {
		return model.getIndexByLevel(currentQuestionLevel);
	}

	public String getQuestionText() {
		return currentQuestion == null ? null : currentQuestion.getText();
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void handleAnswer(final int index) {
		if (getState() != GameState.QUESTION_ONGOING && getState() != GameState.ANSWER_GIVEN && getState() != GameState.LIFELINE_AUDIENCE) return;
		logger.info("Handle answer " + index);

		final List<Answer> answers = currentQuestion.getAnswers();

		if (index <= 0) {
			selectedAnswer = answers.get(0);
		} else if (index >= answers.size()) {
			selectedAnswer = answers.get(answers.size() - 1);
		} else {
			selectedAnswer = answers.get(index);
		}

		answers.forEach(a -> a.setState(AnswerState.IDLE));
		selectedAnswer.setState(AnswerState.SELECTED);
		currentQuestion.setAnswered(true);
		setState(GameState.ANSWER_GIVEN);
	}

	public void handleExitGame() {
		gameFinished(false, false, true);
		setState(GameState.FINISHED);
	}

	public void handleGameLoop() {
		updateClock();
	}

	public void handleUserInteraction() {
		switch (getState()) {
		case DETERMINE_NEXT_QUESTION:
			getNewQuestion();
			resetClock();

			final List<Index> indexs = model.getIndexs();
			final boolean finished = indexs.get(0).getNumber() <= currentQuestionLevel - 1;
			if (currentQuestion == null || finished) {
				gameFinished(true, false, false);
				setState(GameState.FINISHED);
				handleUserInteraction();
			} else {
				setState(GameState.PREPARE_QUESTION);
			}
			break;
		case PREPARE_QUESTION:
			makeAnswersVisible();
			setState(GameState.QUESTION_ONGOING);
			break;
		case ANSWER_GIVEN:
			showAnswerResult();
			setState(GameState.SHOW_ANSWER_RESULT);
			break;
		case SHOW_ANSWER_RESULT:
			if (selectedAnswer != null && selectedAnswer.getState() == AnswerState.WRONG) {
				setState(GameState.FINISHED);
			} else {
				currentQuestion = null;
				selectedAnswer = null;
				setState(GameState.DETERMINE_NEXT_QUESTION);
			}
			handleUserInteraction();
			break;
		case FINISHED:
			if (listener != null) {
				listener.gameFinished(gameFinishedReason, prize);
			}

			selectedAnswer = null;
			currentQuestion = null;
			currentQuestionLevel = 1;
			remainingTime = DEFAULT_REMAINING_TIME;
			gameFinishedReason = null;
			prize = 0;
			setState(GameState.DETERMINE_NEXT_QUESTION);
			break;
		default:
			break;
		}
	}

	public void requestLifeLineUsage(final LifeLine lifeLine) {
		if (!lifeLine.isAvailable()) return;
		if (getState() != GameState.QUESTION_ONGOING && getState() != GameState.ANSWER_GIVEN && getState() != GameState.LIFELINE_AUDIENCE) return;

		switch (lifeLine.getType()) {
		case AUDIENCE:
			setState(GameState.LIFELINE_AUDIENCE);
			break;
		case FIFITY_FIFTY:
			final List<Answer> answers = new LinkedList<>(currentQuestion.getAnswers());
			Collections.shuffle(answers);
			answers.remove(currentQuestion.getCorrectAnswer());

			for (int i = 0; i < 2; i++) {
				final Answer answer = answers.get(i);
				if (answer == selectedAnswer) {
					selectedAnswer = null;
					answer.setState(AnswerState.IDLE);
				}
				answer.setVisible(false);
			}

			break;
		default:
			break;
		}
		lifeLine.setAvailable(false);
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(final GameControllerListener listener) {
		this.listener = listener;
	}

	/**
	 * Set the {@link Model}.
	 *
	 * @param model
	 *            {@link #model}
	 */
	public void setModel(final Model model) {
		this.model = model;

		model.getLifeLines().forEach(l -> l.setAvailable(true));
	}

	/**
	 * @return the {@link #currentQuestion}
	 */
	Question getCurrentQuestion() {
		return currentQuestion;
	}

	/**
	 * @return the {@link #currentQuestionLevel}
	 */
	int getCurrentQuestionLevel() {
		return currentQuestionLevel;
	}

	/**
	 * @return the {@link #selectedAnswer}
	 */
	Answer getSelectedAnswer() {
		return selectedAnswer;
	}

	/**
	 * @return the {@link #state}
	 */
	GameState getState() {
		return state;
	}

	private void gameFinished(final boolean won, final boolean timeout, final boolean exit) {
		prize = 0;
		if (won) {
			final List<Index> indexs = model.getIndexs();
			prize = indexs.get(0).getMoney();
			gameFinishedReason = GameFinishedReason.WON;
		} else if (exit) {
			final Index index = model.getIndexByLevel(currentQuestionLevel);
			prize = index.getMoney();
			gameFinishedReason = GameFinishedReason.EXIT;
		} else {
			for (int i = 0; i < currentQuestionLevel; i++) {
				final Index index = model.getIndexByLevel(i);
				if (index != null && index.isSafe()) {
					prize = index.getMoney();
				}
			}
			if (timeout) {
				gameFinishedReason = GameFinishedReason.TIMEOUT;
			} else {
				gameFinishedReason = GameFinishedReason.WRONG_ANSWER;
			}
		}

	}

	private void getNewQuestion() {
		selectedAnswer = null;
		currentQuestion = model.getQuestionByLevel(currentQuestionLevel);
		if (currentQuestion == null) return;
		currentQuestion.getAnswers().forEach(a -> resetAnswer(a));
	}

	private boolean isAnswerCorrect() {
		final Answer correctAnswer = currentQuestion.getCorrectAnswer();
		return selectedAnswer != null && selectedAnswer.equals(correctAnswer);
	}

	private void makeAnswersVisible() {
		if (currentQuestion == null) return;
		final List<Answer> answers = currentQuestion.getAnswers();
		answers.forEach(a -> a.setVisible(true));
	}

	private void resetAnswer(final Answer a) {
		a.setState(AnswerState.IDLE);
		a.setVisible(false);
	}

	/**
	 * Reset the clock.
	 */
	private void resetClock() {
		remainingTime = DEFAULT_REMAINING_TIME;
	}

	private void setState(final GameState state) {
		logger.info("Switch game state from " + this.state + " to " + state);
		this.state = state;
	}

	private void showAnswerResult() {
		final Answer correctAnswer = currentQuestion.getCorrectAnswer();
		final boolean correct = isAnswerCorrect();
		if (correct) {
			selectedAnswer.setState(AnswerState.RIGHT);
			currentQuestionLevel++;
		} else if (selectedAnswer != null) {
			selectedAnswer.setState(AnswerState.WRONG);
			correctAnswer.setState(AnswerState.RIGHT);
			gameFinished(false, false, false);
		}
	}

	/**
	 * Update the clock.
	 */
	private void updateClock() {
		switch (getState()) {
		case QUESTION_ONGOING:
			remainingTime--;
			if (remainingTime <= 0) {
				gameFinished(false, true, false);
				setState(GameState.FINISHED);
				handleUserInteraction();
			}
			break;
		default:
			break;
		}
	}

	/** the current {@link Question}. */
	private Question currentQuestion;

	/** the current question level. */
	private int currentQuestionLevel = 1;

	/** the {@link GameFinishedReason}. */
	private GameFinishedReason gameFinishedReason;
	/** the {@link GameControllerListener}. */
	private GameControllerListener listener;
	/** the {@link Model}. */
	private Model model;
	private int prize;
	/** the remaining time. */
	private int remainingTime = DEFAULT_REMAINING_TIME;
	/** the selected {@link Answer}. */
	private Answer selectedAnswer;

	/** the {@link GameState}. */
	private GameState state = GameState.DETERMINE_NEXT_QUESTION;
}
