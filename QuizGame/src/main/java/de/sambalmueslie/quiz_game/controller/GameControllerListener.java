package de.sambalmueslie.quiz_game.controller;

public interface GameControllerListener {

	void gameFinished(boolean won, boolean timeout, boolean exit, int prize);

}
