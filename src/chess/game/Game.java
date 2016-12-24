package chess.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import chess.settings.GameSettings;

public class Game {

	public GameSettings settings = new GameSettings();

	public void saveSettings(final Path path) throws IOException {
		Files.write(path, "".getBytes() /* TODO */);
	}

	public void readSettings(final Path path) throws IOException {
		final byte[] bytes = Files.readAllBytes(path);
		// TODO
	}

}
