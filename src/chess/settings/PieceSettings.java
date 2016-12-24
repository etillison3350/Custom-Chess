package chess.settings;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PieceSettings {

	public static final PieceSettings BLANK_SQUARE = new PieceSettings();

	public final Map<Byte, List<Character>> symbols = new HashMap<>();
	public final Set<List<MoveSettings>> moves = new HashSet<>();
	public byte respawn = 0;
	public byte royal = 0;
	public boolean forcedCapture = false;
	public final Set<Point> hill = new HashSet<>();
	public boolean mustExitHill = false;

}
