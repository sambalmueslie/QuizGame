package de.sambalmueslie.quiz_game.data;

/**
 * A life line.
 */
public class LifeLine {

	/**
	 * Constructor.
	 *
	 * @param type
	 */
	public LifeLine(final LifeLineType type) {
		this.type = type;
	}

	/**
	 * @return the {@link #type}
	 */
	public LifeLineType getType() {
		return type;
	}

	/**
	 * @return the {@link #available}
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public void setAvailable(final boolean available) {
		this.available = available;
	}

	/** is the lifeline available. */
	private boolean available;

	/** the {@link LifeLineType}. */
	private final LifeLineType type;

}
