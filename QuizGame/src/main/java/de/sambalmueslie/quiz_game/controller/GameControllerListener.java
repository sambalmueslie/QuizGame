package de.sambalmueslie.quiz_game.controller;

public interface GameControllerListener {

	void gameFinished(GameFinishedReason reason, int prize);

}
