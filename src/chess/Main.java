package chess;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import chess.game.Game;
import chess.window.Window;

public class Main {

	public static Game game;
	public static Window window;

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (final UnsupportedLookAndFeelException e) {}

		game = new Game();

		window = new Window();
		window.setVisible(true);
	}

}
