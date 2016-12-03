package chess.settings;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.List;

import chess.game.GamePiece;

public class GameSettings {

	public List<GamePiece> pieceArrangement;
	public boolean isHex;
	public byte x;
	public byte y;
	public byte z;
	// TODO implement infinite boards
	public byte players;
	public byte boards;
	public boolean validationOn;
	public List<Line> lines;
	public HashMap<Point, Byte> specialColors;
	public HashMap<Byte, Color> tileColors;

}
