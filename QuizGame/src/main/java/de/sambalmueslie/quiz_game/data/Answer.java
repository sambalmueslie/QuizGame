package de.sambalmueslie.quiz_game.data;

/**
 * The answer.
 */
public class Answer {
	/**
	 * @return the {@link #prefix}
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * @return the {@link #state}
	 */
	public AnswerState getState() {
		return state;
	}

	/**
	 * @return the {@link #text}
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the {@link #visible}
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param prefix
	 *            the prefix to set
	 */
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(final AnswerState state) {
		this.state = state;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(final boolean visible) {
		this.visible = visible;
	}

	@Override
	public String toString() {
		return "Answer [prefix=" + prefix + ", state=" + state + ", text=" + text + ", visible=" + visible + "]";
	}

	/** the prefix. */
	private String prefix;

	/** the {@link AnswerState}. */
	private AnswerState state;
	/** the text. */
	private String text;
	/** is the answer visible. */
	private boolean visible;
}
