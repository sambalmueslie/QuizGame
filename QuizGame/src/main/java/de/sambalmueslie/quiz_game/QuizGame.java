package de.sambalmueslie.quiz_game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.sambalmueslie.quiz_game.config.ConfigFileReader;
import de.sambalmueslie.quiz_game.controller.GameController;
import de.sambalmueslie.quiz_game.controller.GameFinishedReason;
import de.sambalmueslie.quiz_game.data.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
		gameController = new GameController();
		gameController.setListener((reason, prize) -> handleGameStateChanged(reason, prize));

		pane = new BorderPane();
		scene = new Scene(pane);
		scene.getStylesheets().addAll(getClass().getClassLoader().getResource("style.css").toExternalForm());

		showStartWindow();

		final Screen primaryScreen = Screen.getPrimary();
		final Rectangle2D bounds = primaryScreen.getBounds();
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);

		stage.show();
	}

	private void handleGameStateChanged(final GameFinishedReason reason, final int prize) {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FinishedWindow.fxml"));
			final Parent root = fxmlLoader.load();
			final FinishedWindowController controller = (FinishedWindowController) fxmlLoader.getController();
			controller.setup(reason, prize);

			scene.setOnKeyTyped(e -> showStartWindow());

			pane.setCenter(root);
		} catch (final Exception e) {
			logger.fatal("Exception occured", e);
		}
	}

	/**
	 * Show the main window.
	 */
	private void showMainWindow() {
		try {
			final Model model = ConfigFileReader.readConfigFile("sample.txt");
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("MainWindow.fxml"));
			final Parent root = fxmlLoader.load();
			final MainWindowController controller = (MainWindowController) fxmlLoader.getController();
			controller.setGameController(gameController);
			controller.setModel(model);

			scene.setOnKeyTyped(e -> controller.handleKeyTyped(e));
			gameController.handleUserInteraction();

			pane.setCenter(root);
		} catch (final Exception e) {
			logger.fatal("Exception occured", e);
		}
	}

	/**
	 * Show the start window.
	 */
	private void showStartWindow() {
		try {
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StartWindow.fxml"));
			final Parent root = fxmlLoader.load();
			scene.setOnKeyTyped(e -> showMainWindow());
			pane.setCenter(root);
		} catch (final Exception e) {
			logger.fatal("Exception occured", e);
		}
	}

	/** the {@link GameController}. */
	private GameController gameController;
	private BorderPane pane;
	private Scene scene;
}
