package de.sambalmueslie.quiz_game.data;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Model {

	public void addIndex(final Index index) {
		if (index == null) return;
		indexs.add(index);
		indexs.sort((o1, o2) -> Integer.compare(o2.getNumber(), o1.getNumber()));
	}

	public void addQuestion(final Question question) {
		if (question == null) return;
		questions.add(question);
	}

	public Index getIndexByLevel(final int currentQuestionLevel) {
		if (indexs.isEmpty()) return null;
		if (currentQuestionLevel <= 0)
			return indexs.get(indexs.size() - 1);
		else if (currentQuestionLevel >= indexs.size())
			return indexs.get(0);
		else
			return indexs.get(indexs.size() - currentQuestionLevel);
	}

	public List<Index> getIndexs() {
		return Collections.unmodifiableList(indexs);
	}

	public Question getQuestionByLevel(final int level) {
		final Stream<Question> stream = questions.stream();
		final List<Question> result = stream.filter(q -> q.getLevel() <= level).filter(q -> !q.isAnswered()).collect(Collectors.toList());
		if (result.isEmpty()) return null;
		if (result.size() == 1) return result.get(0);
		final Random r = new Random();
		final int index = r.nextInt(result.size() - 1);
		return result.get(index);
	}

	/** the {@link Index} {@link List}. */
	private final List<Index> indexs = new ArrayList<>();

	/** the {@link Question}s. */
	private final List<Question> questions = new LinkedList<>();
}
