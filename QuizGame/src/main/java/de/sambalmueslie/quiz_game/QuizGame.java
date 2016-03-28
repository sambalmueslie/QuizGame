package de.sambalmueslie.quiz_game;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.sambalmueslie.quiz_game.config.ConfigFileReader;
import de.sambalmueslie.quiz_game.controller.GameController;
import de.sambalmueslie.quiz_game.data.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class QuizGame extends Application {

	/** the {@link Logger}. */
	private static Logger logger = LogManager.getLogger(QuizGame.class);

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
		this.stage = stage;
		gameController = new GameController();
		gameController.setListener((won, timeout, prize) -> handleGameStateChanged(won, timeout, prize));
		showStartWindow();
	}

	private void handleGameStateChanged(final boolean won, final boolean timeout, final int prize) {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FinishedWindow.fxml"));
			final Parent root = fxmlLoader.load();
			final FinishedWindowController controller = (FinishedWindowController) fxmlLoader.getController();
			controller.setup(won, timeout, prize);

			final Scene scene = new Scene(root, 1024, 768);
			scene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());

			stage.setTitle("Quiz Game - Finished");
			stage.setScene(scene);
			stage.show();
		} catch (final Exception e) {
			logger.fatal("Exception occured", e);
		}
	}

	/**
	 * Show the main window.
	 */
	private void showMainWindow() {
		try {
			final Model model = ConfigFileReader.readConfigFile("config.txt");
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainWindow.fxml"));
			final Parent root = fxmlLoader.load();
			final MainWindowController controller = (MainWindowController) fxmlLoader.getController();
			controller.setGameController(gameController);
			controller.setModel(model);

			final Scene scene = new Scene(root, 1024, 768);
			scene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());
			scene.setOnKeyTyped(e -> controller.handleKeyTyped(e));

			stage.setTitle("Quiz Game - Game");
			stage.setScene(scene);
			stage.show();
		} catch (final Exception e) {
			logger.fatal("Exception occured", e);
		}
	}

	/**
	 * Show the start window.
	 */
	private void showStartWindow() throws IOException {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StartWindow.fxml"));
			final Parent root = fxmlLoader.load();
			final Scene scene = new Scene(root, 1024, 768);
			scene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());
			scene.setOnKeyTyped(e -> showMainWindow());

			stage.setTitle("Quiz Game - Welcome");
			stage.setScene(scene);
			stage.show();
		} catch (final Exception e) {
			logger.fatal("Exception occured", e);
		}
	}

	/** the {@link GameController}. */
	private GameController gameController;
	/** the {@link Stage}. */
	private Stage stage;
}
