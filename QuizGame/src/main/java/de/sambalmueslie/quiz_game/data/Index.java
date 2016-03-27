package de.sambalmueslie.quiz_game.data;

public class Index {

	/**
	 * Constructor.
	 */
	public Index() {

	}

	/**
	 * Constructor.
	 *
	 * @param number
	 * @param money
	 * @param safe
	 */
	public Index(final int number, final int money, final boolean safe) {
		this.money = money;
		this.number = number;
		this.safe = safe;
	}

	/**
	 * @return the {@link #money}
	 */
	public int getMoney() {
		return money;
	}

	/**
	 * @return the {@link #number}
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the {@link #safe}
	 */
	public boolean isSafe() {
		return safe;
	}

	/**
	 * @param money
	 *            the money to set
	 */
	public void setMoney(final int money) {
		this.money = money;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(final int number) {
		this.number = number;
	}

	/**
	 * @param safe
	 *            the safe to set
	 */
	public void setSafe(final boolean safe) {
		this.safe = safe;
	}

	private int money;
	private int number;

	private boolean safe;
}
