package de.sambalmueslie.quiz_game.controller;

import static de.sambalmueslie.quiz_game.data.QuestionHelper.createQuestion;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.sambalmueslie.quiz_game.data.*;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

	@Before
	public void setUp() throws Exception {
		final Model model = new Model();

		model.add(new Index(1, 50, true));
		model.add(new Index(2, 100, false));
		model.add(new Index(3, 200, false));
		model.add(new Index(4, 300, false));
		model.add(new Index(5, 500, true));
		model.add(new Index(6, 1000, false));
		model.add(new Index(7, 2000, false));
		model.add(new Index(8, 4000, false));
		model.add(new Index(9, 8000, false));
		model.add(new Index(10, 16000, true));
		model.add(new Index(11, 32000, false));
		model.add(new Index(12, 64000, false));
		model.add(new Index(13, 125000, false));
		model.add(new Index(14, 500000, false));
		model.add(new Index(15, 1000000, true));

		model.add(createQuestion("Question Level 1", "a", "b", "c", "d", 1, 'a'));
		model.add(createQuestion("Question Level 2", "a", "b", "c", "d", 2, 'a'));
		model.add(createQuestion("Question Level 3", "a", "b", "c", "d", 3, 'a'));
		model.add(createQuestion("Question Level 4", "a", "b", "c", "d", 4, 'a'));
		model.add(createQuestion("Question Level 5", "a", "b", "c", "d", 5, 'a'));
		model.add(createQuestion("Question Level 6", "a", "b", "c", "d", 6, 'a'));
		model.add(createQuestion("Question Level 7", "a", "b", "c", "d", 7, 'a'));
		model.add(createQuestion("Question Level 8", "a", "b", "c", "d", 8, 'a'));
		model.add(createQuestion("Question Level 9", "a", "b", "c", "d", 9, 'a'));
		model.add(createQuestion("Question Level 10", "a", "b", "c", "d", 10, 'a'));
		model.add(createQuestion("Question Level 11", "a", "b", "c", "d", 11, 'a'));
		model.add(createQuestion("Question Level 12", "a", "b", "c", "d", 12, 'a'));
		model.add(createQuestion("Question Level 13", "a", "b", "c", "d", 13, 'a'));
		model.add(createQuestion("Question Level 14", "a", "b", "c", "d", 14, 'a'));
		model.add(createQuestion("Question Level 15", "a", "b", "c", "d", 15, 'a'));

		controller = new GameController();
		controller.setModel(model);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClock() {
		int time = controller.getRemainingTime();
		// DETERMINE_NEXT_QUESTION
		assertEquals(30, time);
		controller.handleUserInteraction();
		// GameState.PREPARE_QUESTION;
		time = controller.getRemainingTime();
		assertEquals(30, time);
		controller.handleUserInteraction();
		// GameState.QUESTION_ONGOING;
		time = controller.getRemainingTime();
		assertEquals(30, time);
		controller.handleGameLoop();
		time = controller.getRemainingTime();
		assertEquals(29, time);

		for (int i = 30; i > 0; i--) {
			controller.handleGameLoop();
		}

		// GameState.ANSWER_GIVEN;
		time = controller.getRemainingTime();
		assertEquals(0, time);
		assertEquals(GameState.FINISHED, controller.getState());
	}

	@Test
	public void testQuestion() {
		// DETERMINE_NEXT_QUESTION
		assertEquals(GameState.DETERMINE_NEXT_QUESTION, controller.getState());
		assertNull(controller.getAnswer(0));
		assertNull(null, controller.getCurrentIndex());
		assertNull(controller.getQuestionText());
		controller.handleUserInteraction();

		// GameState.PREPARE_QUESTION;
		Question q = controller.getCurrentQuestion();
		assertNotNull(q);
		assertEquals(false, q.isAnswered());
		for (final Answer a : q.getAnswers()) {
			assertFalse(a.isVisible());
			assertEquals(AnswerState.IDLE, a.getState());
		}
		for (int i = 0; i < 4; i++) {
			assertNotNull(controller.getAnswer(i));
		}
		assertEquals(q.getText(), controller.getQuestionText());
		assertEquals(1, controller.getCurrentIndex().getNumber());
		controller.handleUserInteraction();

		// GameState.QUESTION_ONGOING;
		for (final Answer a : q.getAnswers()) {
			assertTrue(a.isVisible());
		}
		controller.handleAnswer(0);
		// GameState.ANSWER_GIVEN;
		assertEquals(true, q.isAnswered());
		Answer selectedAnswer = controller.getSelectedAnswer();
		assertEquals(q.getAnswers().get(0), selectedAnswer);
		assertEquals(AnswerState.SELECTED, selectedAnswer.getState());
		controller.handleUserInteraction();

		assertEquals(2, controller.getCurrentIndex().getNumber());
		assertEquals(AnswerState.RIGHT, selectedAnswer.getState());
		controller.handleUserInteraction();

		// second round
		controller.handleGameLoop();
		// DETERMINE_NEXT_QUESTION
		assertEquals(GameState.DETERMINE_NEXT_QUESTION, controller.getState());
		assertNull(controller.getAnswer(0));
		assertNull(null, controller.getCurrentIndex());
		assertNull(controller.getQuestionText());
		controller.handleUserInteraction();

		// GameState.PREPARE_QUESTION;
		q = controller.getCurrentQuestion();
		assertNotNull(q);
		assertEquals(false, q.isAnswered());
		for (final Answer a : q.getAnswers()) {
			assertFalse(a.isVisible());
			assertEquals(AnswerState.IDLE, a.getState());
		}
		for (int i = 0; i < 4; i++) {
			final Answer a = controller.getAnswer(i);
			assertNotNull(a);
			assertFalse(a.isVisible());
			assertEquals(AnswerState.IDLE, a.getState());
		}
		assertEquals(q.getText(), controller.getQuestionText());
		assertEquals(2, controller.getCurrentIndex().getNumber());
		controller.handleUserInteraction();

		// GameState.QUESTION_ONGOING;
		for (final Answer a : q.getAnswers()) {
			assertTrue(a.isVisible());
		}
		controller.handleAnswer(1);
		// GameState.ANSWER_GIVEN;
		assertEquals(true, q.isAnswered());
		selectedAnswer = controller.getSelectedAnswer();
		assertEquals(q.getAnswers().get(1), selectedAnswer);
		assertEquals(AnswerState.SELECTED, selectedAnswer.getState());
		controller.handleUserInteraction();

		assertEquals(AnswerState.WRONG, selectedAnswer.getState());
		assertEquals(AnswerState.RIGHT, q.getAnswers().get(0).getState());
		controller.handleUserInteraction();

	}

	private GameController controller;

}
