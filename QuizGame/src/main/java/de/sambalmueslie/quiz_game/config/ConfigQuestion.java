package de.sambalmueslie.quiz_game.config;

public class ConfigQuestion {
	/**
	 * Constructor.
	 */
	public ConfigQuestion() {
	}

	/**
	 * Constructor.
	 *
	 * @param answerA
	 * @param answerB
	 * @param answerC
	 * @param answerD
	 * @param correct
	 * @param level
	 * @param text
	 */
	public ConfigQuestion(final String text, final int level, final String answerA, final String answerB, final String answerC, final String answerD,
			final int correct) {
		this.answerA = answerA;
		this.answerB = answerB;
		this.answerC = answerC;
		this.answerD = answerD;
		this.correct = correct;
		this.level = level;
		this.text = text;
	}

	/**
	 * @return the {@link #answerA}
	 */
	public String getAnswerA() {
		return answerA;
	}

	/**
	 * @return the {@link #answerB}
	 */
	public String getAnswerB() {
		return answerB;
	}

	/**
	 * @return the {@link #answerC}
	 */
	public String getAnswerC() {
		return answerC;
	}

	/**
	 * @return the {@link #answerD}
	 */
	public String getAnswerD() {
		return answerD;
	}

	/**
	 * @return the {@link #correct}
	 */
	public int getCorrect() {
		return correct;
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
	 * @param answerA
	 *            the answerA to set
	 */
	public void setAnswerA(final String answerA) {
		this.answerA = answerA;
	}

	/**
	 * @param answerB
	 *            the answerB to set
	 */
	public void setAnswerB(final String answerB) {
		this.answerB = answerB;
	}

	/**
	 * @param answerC
	 *            the answerC to set
	 */
	public void setAnswerC(final String answerC) {
		this.answerC = answerC;
	}

	/**
	 * @param answerD
	 *            the answerD to set
	 */
	public void setAnswerD(final String answerD) {
		this.answerD = answerD;
	}

	/**
	 * @param correct
	 *            the correct to set
	 */
	public void setCorrect(final int correct) {
		this.correct = correct;
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

	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private int correct;
	private int level;
	private String text;
}
