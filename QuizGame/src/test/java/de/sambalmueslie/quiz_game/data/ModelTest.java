package de.sambalmueslie.quiz_game.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ModelTest {

	@Before
	public void setUp() throws Exception {
		model = new Model();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIndex() {
		for (int i = 1; i < 16; i++) {
			model.addIndex(new Index(i, i * 10, false));
		}

		assertEquals(15, model.getIndexs().size());
		Index i = model.getIndexByLevel(-1);
		assertEquals(1, i.getNumber());

		i = model.getIndexByLevel(99);
		assertEquals(15, i.getNumber());

		i = model.getIndexByLevel(5);
		assertEquals(5, i.getNumber());
	}

	@Test
	public void testInvalidParameter() {
		model.addQuestion(null);
		assertNull(model.getQuestionByLevel(0));

		model.addIndex(null);
		assertNull(model.getIndexByLevel(0));
		assertTrue(model.getIndexs().isEmpty());
	}

	@Test
	public void testQuestion() {
		for (int i = 1; i < 16; i++) {
			model.addQuestion(QuestionHelper.createQuestion("asdf", "a", "b", "c", "d", i, 'a'));
		}

		for (int i = 1; i < 16; i++) {
			final Question q = model.getQuestionByLevel(i);
			assertNotNull(q);
			assertTrue(i >= q.getLevel());
		}
	}

	@Test
	public void testQuestionIgnoreAnswered() {
		final Question q1 = QuestionHelper.createQuestion("asdf", "a", "b", "c", "d", 1, 'a');
		model.addQuestion(q1);
		final Question q2 = QuestionHelper.createQuestion("asdf", "a", "b", "c", "d", 1, 'a');
		model.addQuestion(q2);
		q2.setAnswered(true);

		final Question q = model.getQuestionByLevel(1);
		assertNotNull(q);
		assertEquals(q1, q);
	}

	/** the {@link Model}. */
	private Model model;

}
