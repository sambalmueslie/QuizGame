package de.sambalmueslie.quiz_game.config;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import de.sambalmueslie.quiz_game.data.*;

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

		final List<ConfigQuestion> q = config.getQuestions();
		q.add(new ConfigQuestion("Wer hätte statt seines Erbauers an einem Galgen hängen sollen?", 12, "Der König", "Haman", "Mordechai", "Esther", 'c'));
		q.add(new ConfigQuestion("Was gab es als Beilage zum Manna?", 13, "Wein", "Wachteln", "Stockfisch", "Nutella", 'b'));
		q.add(new ConfigQuestion("Welcher Diener eignete sich ein Geschenk, dass ein Herr ablehnte an?", 14, "Gemausi", "Gemaliel", "Gedion", "Gehasi", 'd'));

		q.add(new ConfigQuestion("Welcher israelitische Stamm erbeutete Frauen bei einem Fest um einen Schwur zu umgehen?", 14, "Benjamin", "Manasse", "Ruben",
				"Josef", 'a'));
		q.add(new ConfigQuestion("Über welche Zeitspanne wurde die Bibel geschrieben?", 14, "500 Jahre", "100 Jahre", "1550 Jahre", "750 Jahre", 'c'));
		q.add(new ConfigQuestion("Wann fand die Schlacht von Karkemisch statt?", 15, "605 v. Chr", "606 v. Chr", "6010 v. Chr", "7001 v. Chr", 'a'));
		q.add(new ConfigQuestion("Wer flüchtete vor Gott?", 2, "Noah", "Lukas", "Jona", "Abraham", 'c'));
		q.add(new ConfigQuestion("Welcher Jünger war zur Zeit des Passsamahls von Satan besessen?", 2, "Simon", "Petrus", "Thomas", "Judas", 'd'));
		q.add(new ConfigQuestion("Wie viele Bücher hat das alte Testament?", 3, "33", "39", "45", "40", 'b'));
		q.add(new ConfigQuestion("Was ist die Bibel in erster Linie?", 3, "Das meist zitierte Buch", "Ein philosophisches Buch", "ein Rezeptbuch",
				"ein Geschichtsbuch", 'a'));
		q.add(new ConfigQuestion("Auf welchem Berg bekam Mose die 10 Gebote?", 4, "Horeb", "Karmel", "Zugspitze", "Ölberg", 'a'));
		q.add(new ConfigQuestion("Wie lange war Saulus blind?", 4, "4 Tage", "3 Tage", "2  Wochen", "1 Tag", 'b'));

		q.add(new ConfigQuestion("Welche Stadt gehört nicht zu dein in der Offenbarung erwähnten Gemeinden?", 6, "Philadelphia", "Laodizea", "Korinth",
				"Smyrna", 'c'));
		q.add(new ConfigQuestion("Was war die erste Reaktion von Saul auf die Königskrone?", 6, "Er gab ein Festmahl", "Er ging zu den Schafen",
				"Er schrie vor Freude", "Er versteckte sich", 'd'));
		q.add(new ConfigQuestion("Wie hießen die Söhne von Noah?", 6, "Adam, Set, Enosch", "Samuel, David, Tubal", "Sem, Ham, Jafet", "Put, Kanaan, Togarma",
				'c'));
		q.add(new ConfigQuestion("Welcher König erhielt von seiner Frau eine Rüge für seinen ausgefallenen Lobpreisstil?", 7, "David", "Saul", "Salomo",
				"Rehabeam", 'a'));
		q.add(new ConfigQuestion("Welche symbolische Bedeutung hat die Zahl 7 in der Bibel?", 8, "Keine", "Primzahl", "Göttliche Zahl", "Menschliche Zahl",
				'c'));
		q.add(new ConfigQuestion("Wer war kein Richter in Israel?", 8, "Deborah", "Gideon", "Jael", "Simson", 'c'));
		q.add(new ConfigQuestion("Wie viele Bücher hat das NT?", 9, "14", "32", "27", "18", 'c'));
		q.add(new ConfigQuestion("In der Offenbarung werden die Stämme Israels aufgezählt, welcher wird nicht genannt?", 9, "Asser", "Dan", "Juda", "Naftali",
				'b'));
		q.add(new ConfigQuestion("Warum starb Usa?", 9, "Wurde überfahren", "Seine Frau warf einen Mühlstein", "Hat die Bundeslade angefasst", "Selbstmord",
				'c'));
		q.add(new ConfigQuestion("Welchen Tipp gab Jitro Mose um ihm die Arbeit zu erleichtern?", 11, "Schnellverfahren einführen",
				"Unternehmensberater einstellen", "40h Woche", "Arbeit verteilen", 'd'));
		q.add(new ConfigQuestion("Wer sorgte in Ephesus für Unruhen?", 11, "Jakobus", "Paulus", "Johannes", "Maleachi", 'b'));
		q.add(new ConfigQuestion("Wie machte Jesus 5000 Menschen satt?", 1, "5 Brote und 2 Fische", "Liferando.de", "Manna", "5 Brote und 3 Fische", 'a'));
		q.add(new ConfigQuestion("Wie viele Verse hat die Bibel?", 10, "31176", "45289", "25001", "100", 'a'));

		Arrays.asList(LifeLineType.values()).forEach(config.getLifeLineTypes()::add);

		final String data = gson.toJson(config);

		final Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"))) {
			writer.write(data);
		}

	}

	public static Model readConfigFile(final String fileName) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		final Gson gson = new Gson();
		final Config config = gson.fromJson(new InputStreamReader(new FileInputStream(fileName), Charset.forName("UTF-8")), Config.class);

		final Model model = new Model();
		config.getIndexs().forEach(model::add);

		config.getQuestions().stream().map(ConfigFileReader::convert).forEach(model::add);

		config.getLifeLineTypes().stream().map(lt -> new LifeLine(lt)).forEach(model::add);
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
