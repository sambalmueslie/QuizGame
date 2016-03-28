package de.sambalmueslie.quiz_game;

import de.sambalmueslie.quiz_game.config.ConfigFileReader;
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
		// ConfigFileReader.createDefaultConfigFile("config.txt");
		final Model model = ConfigFileReader.readConfigFile("config.txt");

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

}
