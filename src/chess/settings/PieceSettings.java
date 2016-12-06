package chess.settings;

import java.awt.Point;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PieceSettings {

	public Map<Byte, List<Character>> symbols;
	public Set<List<MoveSettings>> moves;
	public byte respawn;
	public byte royal;
	public boolean forcedCapture;
	public Set<Point> hill;
	public boolean mustExitHill;

}
