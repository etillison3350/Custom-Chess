package chess;

import javax.swing.JFrame;

import chess.window.Board;

public class Main {

	public static void main(final String[] args) {
		final JFrame frame = new JFrame();
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(3);
		frame.add(new Board());
		frame.setVisible(true);
	}

}
