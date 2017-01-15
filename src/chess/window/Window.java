package chess.window;

import java.awt.Frame;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = -6536189597354579525L;

	private final Board board;

	public Window() {
		super("Chess");
		this.setSize(1024, 768);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new Board();
		add(board);
	}

}
