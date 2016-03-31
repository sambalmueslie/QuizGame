package de.sambalmueslie.quiz_game;

import java.io.IOException;

import de.sambalmueslie.quiz_game.config.ConfigFileReader;

public class DefaultConfigGenerator {

	public static void main(final String[] args) throws IOException {
		ConfigFileReader.createDefaultConfigFile("sample.txt");
	}

}
