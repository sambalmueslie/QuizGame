package de.sambalmueslie.quiz_game.data;

import static de.sambalmueslie.quiz_game.data.QuestionHelper.createQuestion;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Model {

	@Deprecated
	public Model() {
		questions.add(createQuestion("Welcher israelitische Stamm hat sich, um einen Schwur zu umgehen, Frauen auf einem Fest in Absprache erbeutet?",
				"Benjamin", "Manasse", "Ruben", "Josef", 1, 'a'));
		questions.add(createQuestion("Welche Person war kein Richter in Israel?", "Deborah", "Gideon", "Jael", "Somson", 1, 'c'));
		questions.add(createQuestion("Welcher König musste sich von seiner Frau für seinen ausgefallenen Lobpreisstil ,Kritik anhören?", "David", "Salomo",
				"Saul", "Rehabeam", 1, 'a'));
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

	/** the {@link Question}s. */
	private final List<Question> questions = new LinkedList<>();
}
