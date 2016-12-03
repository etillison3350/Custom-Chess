package chess.settings;

import java.util.Map;
import java.util.Set;

public class PlayerSettings {

	public byte checksToLose;
	public byte checkmatesToLose;
	public Map<Byte, Byte> movesPerTurn;
	public Set<Set<PieceSettings>> extinctPieces;

}
