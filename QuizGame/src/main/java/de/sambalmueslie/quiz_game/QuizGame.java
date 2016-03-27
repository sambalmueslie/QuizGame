package de.sambalmueslie.quiz_game;

import static de.sambalmueslie.quiz_game.data.QuestionHelper.createQuestion;

import de.sambalmueslie.quiz_game.data.Index;
import de.sambalmueslie.quiz_game.data.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuizGame extends Application {

	/**
	 * Main.
	 *
	 * @param args
	 *            the args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {

		final Model model = new Model();
		initialSetupQuestions(model);
		initialSetupIndex(model);

		final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainWindow.fxml"));
		final Parent root = fxmlLoader.load();
		final MainWindowController controller = (MainWindowController) fxmlLoader.getController();
		controller.setModel(model);

		final Scene scene = new Scene(root, 1024, 768);
		scene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());
		scene.setOnKeyTyped(e -> controller.handleKeyTyped(e));

		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}

	private void initialSetupIndex(final Model model) {
		model.addIndex(new Index(1, 50, true));
		model.addIndex(new Index(2, 100, false));
		model.addIndex(new Index(3, 200, false));
		model.addIndex(new Index(4, 300, false));
		model.addIndex(new Index(5, 500, true));
		model.addIndex(new Index(6, 1000, false));
		model.addIndex(new Index(7, 2000, false));
		model.addIndex(new Index(8, 4000, false));
		model.addIndex(new Index(9, 8000, false));
		model.addIndex(new Index(10, 16000, true));
		model.addIndex(new Index(11, 32000, false));
		model.addIndex(new Index(12, 64000, false));
		model.addIndex(new Index(13, 125000, false));
		model.addIndex(new Index(14, 500000, false));
		model.addIndex(new Index(15, 1000000, true));
	}

	private void initialSetupQuestions(final Model model) {
		model.addQuestion(createQuestion("Welcher israelitische Stamm hat sich, um einen Schwur zu umgehen, Frauen auf einem Fest in Absprache erbeutet?",
				"Benjamin", "Manasse", "Ruben", "Josef", 1, 'a'));
		model.addQuestion(createQuestion("Welche Person war kein Richter in Israel?", "Deborah", "Gideon", "Jael", "Somson", 1, 'c'));
		model.addQuestion(createQuestion("Welcher König musste sich von seiner Frau für seinen ausgefallenen Lobpreisstil ,Kritik anhören?", "David", "Salomo",
				"Saul", "Rehabeam", 1, 'a'));
	}

}
