package chess.game;

import chess.settings.PieceSettings;

public class GamePiece {

	public final PieceSettings piece;
	public int x, y;
	public final int startX, startY;
	public byte controller;
	public byte color;

	public GamePiece(final PieceSettings piece, final int x, final int y) {
		this.piece = piece;
		this.x = startX = x;
		this.y = startY = y;
	}

}
