package chess.settings;

import java.awt.Point;
import java.util.List;

public class PieceSettings {

	public byte controller;
	public byte color;
	public List<Character> symbols;
	public List<List<MoveSettings>> moves;
	public byte respawn;
	public byte royal;
	public boolean forcedCapture;
	public List<Point> hill;
	public boolean mustExitHill;

}
