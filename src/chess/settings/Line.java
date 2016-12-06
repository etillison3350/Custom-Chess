package chess.settings;

public class Line {

	private double x1, x2, y1, y2;
	private final int board;

	public Line(final double x1, final double y1, final double x2, final double y2, final int board) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.board = board;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(final double x1) {
		this.x1 = x1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(final double x2) {
		this.x2 = x2;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(final double y1) {
		this.y1 = y1;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(final double y2) {
		this.y2 = y2;
	}

	public int getBoard() {
		return board;
	}

	public void reverseDirection() {
		final double tempX = x1, tempY = y1;
		x1 = x2;
		y1 = y2;
		x2 = tempX;
		y2 = tempY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + board;
		result = prime * result + (int) Math.round(x1 * 100);
		result = prime * result + (int) Math.round(y1 * 100);
		result = prime * result + (int) Math.round(x2 * 100);
		result = prime * result + (int) Math.round(y2 * 100);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Line other = (Line) obj;
		if (board != other.board) return false;
		if (Math.round(x1 * 100) != Math.round(other.x1 * 100)) return false;
		if (Math.round(y1 * 100) != Math.round(other.y1 * 100)) return false;
		if (Math.round(x2 * 100) != Math.round(other.x2 * 100)) return false;
		if (Math.round(y2 * 100) != Math.round(other.y2 * 100)) return false;
		return true;
	}

}
