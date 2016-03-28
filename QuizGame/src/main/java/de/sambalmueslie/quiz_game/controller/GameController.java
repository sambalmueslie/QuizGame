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
		if (state != GameState.QUESTION_ONGOING) return;

		final List<Answer> answers = currentQuestion.getAnswers();

		if (index <= 0) {
			selectedAnswer = answers.get(0);
		} else if (index >= answers.size()) {
			selectedAnswer = answers.get(answers.size() - 1);
		} else {
			selectedAnswer = answers.get(index);
		}
		selectedAnswer.setState(AnswerState.SELECTED);
		currentQuestion.setAnswered(true);
		state = GameState.ANSWER_GIVEN;
	}

	public void handleGameLoop() {
		updateClock();
	}

	public void handleUserInteraction() {
		if (state == GameState.PREPARE_QUESTION) {
			makeAnswersVisible();
			state = GameState.QUESTION_ONGOING;
		} else if (state == GameState.ANSWER_GIVEN) {
			showAnswerResult();
			state = GameState.DETERMINE_NEXT_QUESTION;
		} else if (state == GameState.DETERMINE_NEXT_QUESTION) {
			getNewQuestion();
			state = GameState.PREPARE_QUESTION;
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

	private void getNewQuestion() {
		selectedAnswer = null;
		currentQuestion = model.getQuestionByLevel(currentQuestionLevel);
		if (currentQuestion == null) return;
		currentQuestion.getAnswers().forEach(a -> resetAnswer(a));
	}

	private void makeAnswersVisible() {
		final List<Answer> answers = currentQuestion.getAnswers();
		answers.forEach(a -> a.setVisible(true));
	}

	private void resetAnswer(final Answer a) {
		a.setState(AnswerState.IDLE);
		a.setVisible(false);
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
		case DETERMINE_NEXT_QUESTION:
			remainingTime = DEFAULT_REMAINING_TIME;
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
