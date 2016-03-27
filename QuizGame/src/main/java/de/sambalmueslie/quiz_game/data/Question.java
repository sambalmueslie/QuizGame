package de.sambalmueslie.quiz_game.data;

import java.util.LinkedList;
import java.util.List;

/**
 * A question.
 */
public class Question {

	/**
	 * @return the {@link #answers}
	 */
	public List<Answer> getAnswers() {
		return answers;
	}

	/**
	 * @return the {@link #correctAnswer}
	 */
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * @return the {@link #level}
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the {@link #text}
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the {@link #answered}
	 */
	public boolean isAnswered() {
		return answered;
	}

	/**
	 * @param answered
	 *            the answered to set
	 */
	public void setAnswered(final boolean answered) {
		this.answered = answered;
	}

	/**
	 * @param correctAnswer
	 *            the correctAnswer to set
	 */
	public void setCorrectAnswer(final Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(final int level) {
		this.level = level;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/** is the question answered. */
	private boolean answered;

	/** the {@link Answer}s. */
	private final List<Answer> answers = new LinkedList<>();
	/** the correct {@link Answer}. */
	private Answer correctAnswer;
	/** the question level. */
	private int level;
	/** the text. */
	private String text;

}
