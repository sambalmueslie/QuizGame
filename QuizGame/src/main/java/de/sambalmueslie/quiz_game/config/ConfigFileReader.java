package de.sambalmueslie.quiz_game.config;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.sambalmueslie.quiz_game.data.Index;
import de.sambalmueslie.quiz_game.data.Model;
import de.sambalmueslie.quiz_game.data.Question;
import de.sambalmueslie.quiz_game.data.QuestionHelper;

public class ConfigFileReader {

	public static void createDefaultConfigFile(final String fileName) throws IOException {
		final GsonBuilder b = new GsonBuilder();
		final Gson gson = b.setPrettyPrinting().create();

		final Config config = new Config();
		config.getIndexs().add(new Index(1, 50, true));
		config.getIndexs().add(new Index(2, 100, false));
		config.getIndexs().add(new Index(3, 200, false));
		config.getIndexs().add(new Index(4, 300, false));
		config.getIndexs().add(new Index(5, 500, true));
		config.getIndexs().add(new Index(6, 1000, false));
		config.getIndexs().add(new Index(7, 2000, false));
		config.getIndexs().add(new Index(8, 4000, false));
		config.getIndexs().add(new Index(9, 8000, false));
		config.getIndexs().add(new Index(10, 16000, true));
		config.getIndexs().add(new Index(11, 32000, false));
		config.getIndexs().add(new Index(12, 64000, false));
		config.getIndexs().add(new Index(13, 125000, false));
		config.getIndexs().add(new Index(14, 500000, false));
		config.getIndexs().add(new Index(15, 1000000, true));

		config.getQuestions().add(new ConfigQuestion("text", 1, "a", "b", "c", "d", 'a'));
		config.getQuestions().add(new ConfigQuestion("text", 2, "a", "b", "c", "d", 'a'));
		config.getQuestions().add(new ConfigQuestion("text", 3, "a", "b", "c", "d", 'a'));

		final String data = gson.toJson(config);

		final Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			writer.write(data);
		}

	}

	public static Model readConfigFile(final String fileName) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		final Gson gson = new Gson();
		final Config config = gson.fromJson(new FileReader(fileName), Config.class);

		final Model model = new Model();
		config.getIndexs().forEach(model::addIndex);

		config.getQuestions().stream().map(ConfigFileReader::convert).forEach(model::addQuestion);
		return model;
	}

	private static Question convert(final ConfigQuestion config) {
		final String text = config.getText();
		final String answerA = config.getAnswerA();
		final String answerB = config.getAnswerB();
		final String answerC = config.getAnswerC();
		final String answerD = config.getAnswerD();
		final int level = config.getLevel();
		final int correct = config.getCorrect();
		return QuestionHelper.createQuestion(text, answerA, answerB, answerC, answerD, level, correct);
	}

}
