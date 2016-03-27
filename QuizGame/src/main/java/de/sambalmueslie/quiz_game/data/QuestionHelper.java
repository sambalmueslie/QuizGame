package de.sambalmueslie.quiz_game.data;

public class QuestionHelper {
	public static Question createQuestion(final String text, final String answerA, final String answerB, final String answerC, final String answerD,
			final int level, final int correct) {
		final Question q = new Question();
		q.setText(text);

		final Answer a = new Answer();
		a.setPrefix("A");
		a.setText(answerA);
		a.setState(AnswerState.IDLE);
		a.setVisible(false);
		q.getAnswers().add(a);

		final Answer b = new Answer();
		b.setPrefix("B");
		b.setText(answerB);
		b.setState(AnswerState.IDLE);
		b.setVisible(false);
		q.getAnswers().add(b);

		final Answer c = new Answer();
		c.setPrefix("C");
		c.setText(answerC);
		c.setState(AnswerState.IDLE);
		c.setVisible(false);
		q.getAnswers().add(c);

		final Answer d = new Answer();
		d.setPrefix("D");
		d.setText(answerD);
		d.setState(AnswerState.IDLE);
		d.setVisible(false);
		q.getAnswers().add(d);

		if (correct == 0 || correct == 'a') {
			q.setCorrectAnswer(a);
		} else if (correct == 1 || correct == 'b') {
			q.setCorrectAnswer(b);
		} else if (correct == 2 || correct == 'c') {
			q.setCorrectAnswer(c);
		} else if (correct >= 3 || correct == 'd') {
			q.setCorrectAnswer(d);
		}
		q.setLevel(level);

		return q;
	}
}
