package de.sambalmueslie.quiz_game;

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
		final Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("MainWindow.fxml"));

		final Scene scene = new Scene(root, 1024, 768);
		scene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());

		stage.setTitle("FXML Welcome");
		stage.setScene(scene);
		stage.show();
	}

}
