package chess.settings;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoveSettings {

	public byte captureMove;
	public List<List<PieceSettings>> captureablePieces = new ArrayList<>();
	public short directions;
	public byte repeatType;
	public List<Byte> blockCombinations;
	public MovementRange explodeRange;
	public Map<Color, List<PieceSettings>> explodablePieces;
	public MovementRange custodialMove;
	public List<PieceSettings> custodialPartners;
	public List<Byte> bounces;
	public boolean relativeToLast;
	public byte inwards;
	public MovementRange chargeRange;
	public List<PieceSettings> chargePieces;
	public byte chargeNumPieces;
	public boolean benedict;
	public boolean rifle;
	public boolean initial;
	public MovementRange immobilization;
	public byte absorbtion;
	public List<PieceSettings> promotesTo;
	public List<Point> promotesOn;
	public List<Point> allowedOn;
	public boolean swapCapture;
	public boolean grasshopper;
	public List<PieceSettings> hurdlePieces;
	public List<Byte> hurdleColors;
	public byte movesToHurdle;
	public byte movesAfterHurdle;
	public MovementRange enPassant;
	public MovementRange move;
	public byte moveAs;
	public List<PieceSettings> moveToRange;
	public byte repeatCount;
	public List<PieceSettings> castlePieces;
	public MoveSettings oppositeMove;
	public boolean canCheck;
	public boolean protectToCapture;
	public List<PieceSettings> transformTo;
	public List<Byte> transformLines;
	public List<Byte> linesToCross;
	public List<Byte> barrierLines;
	public byte multimoveCheck;

	public MoveSettings(final byte captureMove) {
		if (captureMove < 0 || captureMove >= 3) throw new IllegalArgumentException();

		this.captureMove = captureMove;
	}

}
