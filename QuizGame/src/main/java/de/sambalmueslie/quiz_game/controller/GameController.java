package de.sambalmueslie.quiz_game.controller;

import java.util.List;

import de.sambalmueslie.quiz_game.data.*;

public class GameController {
	private static final int DEFAULT_REMAINING_TIME = 60;

	public Answer getAnswer(final int index) {
		if (currentQuestion == null) return null;
		final List<Answer> answers = currentQuestion.getAnswers();
		if (index >= answers.size()) return null;
		return answers.get(index);
	}

	public Index getCurrentIndex() {
		if (currentQuestion == null) return null;
		return model.getIndexByLevel(currentQuestionLevel);
	}

	public String getQuestionText() {
		return currentQuestion == null ? null : currentQuestion.getText();
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void handleAnswer(final int index) {
		if (state != GameState.QUESTION_ONGOING && state != GameState.ANSWER_GIVEN) return;

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
		state = GameState.ANSWER_GIVEN;
	}

	public void handleGameLoop() {
		updateClock();
	}

	public void handleUserInteraction() {
		switch (state) {
		case DETERMINE_NEXT_QUESTION:
			getNewQuestion();
			resetClock();
			state = GameState.PREPARE_QUESTION;
			break;
		case PREPARE_QUESTION:
			makeAnswersVisible();
			state = GameState.QUESTION_ONGOING;
			break;
		case ANSWER_GIVEN:
			showAnswerResult();
			state = GameState.SHOW_ANSWER_RESULT;
			break;
		case SHOW_ANSWER_RESULT:
			currentQuestion = null;
			selectedAnswer = null;
			state = GameState.DETERMINE_NEXT_QUESTION;
		default:
			break;
		}
	}

	/**
	 * Set the {@link Model}.
	 *
	 * @param model
	 *            {@link #model}
	 */
	public void setModel(final Model model) {
		this.model = model;
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

	private void getNewQuestion() {
		selectedAnswer = null;
		currentQuestion = model.getQuestionByLevel(currentQuestionLevel);
		if (currentQuestion == null) return;
		currentQuestion.getAnswers().forEach(a -> resetAnswer(a));
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

	private void showAnswerResult() {
		final Answer correctAnswer = currentQuestion.getCorrectAnswer();
		final boolean correct = selectedAnswer != null && selectedAnswer.equals(correctAnswer);
		if (correct) {
			selectedAnswer.setState(AnswerState.RIGHT);
			currentQuestionLevel++;
		} else if (selectedAnswer != null) {
			selectedAnswer.setState(AnswerState.WRONG);
			correctAnswer.setState(AnswerState.RIGHT);
		}
	}

	/**
	 * Update the clock.
	 */
	private void updateClock() {
		switch (state) {
		case QUESTION_ONGOING:
			remainingTime--;
			if (remainingTime <= 0) {
				state = GameState.ANSWER_GIVEN;
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
	/** the {@link Model}. */
	private Model model;
	/** the remaining time. */
	private int remainingTime;
	/** the selected {@link Answer}. */
	private Answer selectedAnswer;
	/** the {@link GameState}. */
	private GameState state = GameState.DETERMINE_NEXT_QUESTION;
}
