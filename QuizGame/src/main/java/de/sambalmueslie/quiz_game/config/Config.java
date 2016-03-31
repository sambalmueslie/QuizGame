package de.sambalmueslie.quiz_game.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.sambalmueslie.quiz_game.data.Index;
import de.sambalmueslie.quiz_game.data.LifeLineType;
import de.sambalmueslie.quiz_game.data.Question;

public class Config {

	/**
	 * @return the {@link #indexs}
	 */
	public List<Index> getIndexs() {
		return indexs;
	}

	/**
	 * @return the {@link #lifeLineTypes}
	 */
	public List<LifeLineType> getLifeLineTypes() {
		return lifeLineTypes;
	}

	/**
	 * @return the {@link #questions}
	 */
	public List<ConfigQuestion> getQuestions() {
		return questions;
	}

	/**
	 * @param indexs
	 *            the indexs to set
	 */
	public void setIndexs(final List<Index> indexs) {
		this.indexs = indexs;
	}

	/**
	 * @param lifeLineTypes
	 *            the lifeLineTypes to set
	 */
	public void setLifeLineTypes(final List<LifeLineType> lifeLineTypes) {
		this.lifeLineTypes = lifeLineTypes;
	}

	/**
	 * @param questions
	 *            the questions to set
	 */
	public void setQuestions(final List<ConfigQuestion> questions) {
		this.questions = questions;
	}

	/** the {@link Index} {@link List}. */
	private List<Index> indexs = new ArrayList<>();

	/** the {@link Index} {@link List}. */
	private List<LifeLineType> lifeLineTypes = new ArrayList<>();

	/** the {@link Question}s. */
	private List<ConfigQuestion> questions = new LinkedList<>();
}
