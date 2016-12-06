package chess.settings;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import chess.game.GamePiece;

public class GameSettings {

	public final Set<GamePiece> pieceArrangement = new HashSet<>();
	public boolean isHex = false;
	public byte x = 8;
	public byte y = 8;
	public byte z = 6;
	// TODO implement infinite boards
	public byte players = 2;
	public byte boards = 1;
	public boolean validationOn = true;
	public final Set<Line> lines = new HashSet<>();
	public final Map<Point, Byte> specialColors = new HashMap<>();
	public final Map<Byte, Color> tileColors = new HashMap<>();

}
