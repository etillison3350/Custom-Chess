package chess.window;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import chess.Main;
import chess.settings.PieceSettings;

public class Board extends JComponent {

	public static final double SQRT_2 = 1.41421356237309514547462185874;
	public static final double SQRT_3 = 1.73205080756887719317660412344;

	public Board() {
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(final ComponentEvent e) {
				recalculateSize();
				repaint();
			}

		});

		update();
		recalculateSize();
	}

	private static final long serialVersionUID = 4781658951669106909L;

	private List<Set<Cell>> polygons;
	private double xmin, xmax, ymin, ymax;

	private double scale, dx, dy;
	private int numRows;
	private double rowWidth, rowHeight;

	public void update() {
		polygons = new ArrayList<>(Main.game.settings.boards);
		xmin = ymin = Integer.MAX_VALUE;
		xmax = ymax = Integer.MIN_VALUE;

		final double sin = Math.sin(Main.game.settings.boardRotation), cos = Math.cos(Main.game.settings.boardRotation);

		for (int n = 0; n < Main.game.settings.boards; n++) {
			System.out.println(n);
			final Set<Point> missing = Main.game.settings.pieceArrangement.stream().filter(gp -> gp.piece == PieceSettings.BLANK_SQUARE).map(gp -> new Point(gp.x, gp.y)).collect(HashSet::new, HashSet::add, HashSet::addAll);

			final Set<Cell> boardPolygons = new HashSet<>();

			if (Main.game.settings.isHex) {
				for (int y = 0; y < Main.game.settings.y; y++) {
					for (int x = 0; x < Main.game.settings.x; x++) {
						if (missing.contains(new Point(x, y))) continue;

						final Point2D p00 = new Point2D.Double(x * cos - y * sin, x * sin + y * cos);
						final Point2D p10 = new Point2D.Double((x + 1) * cos - y * sin, (x + 1) * sin + y * cos);
						final Point2D p01 = new Point2D.Double(x * cos - (y + 1) * sin, x * sin + (y + 1) * cos);
						final Point2D p11 = new Point2D.Double((x + 1) * cos - (y + 1) * sin, (x + 1) * sin + (y + 1) * cos);

						final Cell c = new Cell(new double[] {p00.getX(), p10.getX(), p11.getX(), p01.getX()}, new double[] {p00.getY(), p10.getY(), p11.getY(), p01.getY()}, 4);
						c.color = (x + y) % 2 == 0 ? new Color(128, 64, 32) : new Color(255, 192, 128);
						c.centerX = (x + 0.5) * cos - (y + 0.5) * sin;
						c.centerY = (x + 0.5) * sin + (y + 0.5) * cos;
						boardPolygons.add(c);

						for (final double px : c.xpoints) {
							if (px < xmin) xmin = px;
							if (px > xmax) xmax = px;
						}
						for (final double py : c.ypoints) {
							if (py < ymin) ymin = py;
							if (py > ymax) ymax = py;
						}
					}
				}
			} else {
				for (int a = 0; a < Main.game.settings.y + Main.game.settings.z - 1; a++) {
					for (int b = 0; b < Main.game.settings.x + Main.game.settings.z - 1; b++) {
						if (a - b >= Main.game.settings.y || b - a >= Main.game.settings.x || missing.contains(new Point(a, b))) continue;

						final double x = (b - a) * 0.5 * SQRT_3;
						final double y = (a + b) * 0.5;

						final Point2D p1 = new Point2D.Double((x + 0.5 / SQRT_3) * cos - (y - 0.5) * sin, (x + 0.5 / SQRT_3) * sin + (y - 0.5) * cos);
						final Point2D p2 = new Point2D.Double((x + 1 / SQRT_3) * cos - y * sin, (x + 1 / SQRT_3) * sin + y * cos);
						final Point2D p3 = new Point2D.Double((x + 0.5 / SQRT_3) * cos - (y + 0.5) * sin, (x + 0.5 / SQRT_3) * sin + (y + 0.5) * cos);
						final Point2D p4 = new Point2D.Double((x - 0.5 / SQRT_3) * cos - (y + 0.5) * sin, (x - 0.5 / SQRT_3) * sin + (y + 0.5) * cos);
						final Point2D p5 = new Point2D.Double((x - 1 / SQRT_3) * cos - y * sin, (x - 1 / SQRT_3) * sin + y * cos);
						final Point2D p6 = new Point2D.Double((x - 0.5 / SQRT_3) * cos - (y - 0.5) * sin, (x - 0.5 / SQRT_3) * sin + (y - 0.5) * cos);

						final Cell c = new Cell(new double[] {p1.getX(), p2.getX(), p3.getX(), p4.getX(), p5.getX(), p6.getX()}, new double[] {p1.getY(), p2.getY(), p3.getY(), p4.getY(), p5.getY(), p6.getY()}, 6);
						c.color = new Color[] {new Color(128, 64, 32), new Color(255, 128, 64), new Color(255, 192, 128)}[(a + b) % 3];
						c.centerX = x * cos - y * sin;
						c.centerY = x * sin + y * cos;
						boardPolygons.add(c);

						for (final double px : c.xpoints) {
							if (px < xmin) xmin = px;
							if (px > xmax) xmax = px;
						}
						for (final double py : c.ypoints) {
							if (py < ymin) ymin = py;
							if (py > ymax) ymax = py;
						}
					}
				}
			}

			polygons.add(boardPolygons);
		}
	}

	public void recalculateSize() {
		final double width = xmax - xmin;
		final double height = ymax - ymin;

		final double screenHeight = getHeight() * width / height;

		// the number of squares with which we can fill the x axis, given the maximum side of a square must be
		// (width * height / num). w/sqrt(wh/n)=sqrt(wn/h)
		final int xAxisCells = (int) Math.ceil(getWidth() / Math.sqrt((double) getWidth() * Main.game.settings.boards / screenHeight));
		final int yAxisCells = (int) Math.ceil(getWidth() / Math.sqrt(screenHeight * Main.game.settings.boards / getWidth()));

		rowWidth = Math.max(getWidth() / xAxisCells, screenHeight / yAxisCells);
		rowHeight = rowWidth * height / width;

// final int heightBasedRows = (int) Math.ceil(Math.sqrt(Main.game.settings.boards * getHeight() * width / (getWidth() *
// height)));
// final int widthBasedRows = (int) Math.ceil(Math.sqrt(Main.game.settings.boards * getWidth() * height / (getHeight() *
// width)));
//
// int heightBasedSize = heightBasedRows == 0 ? 0 : getHeight() / heightBasedRows;
// int widthBasedSize = widthBasedRows == 0 ? 0 : getWidth() / widthBasedRows;
//
// if (heightBasedSize * (int) Math.ceil((double) Main.game.settings.boards / heightBasedRows) > getWidth()) {
// heightBasedSize = 0;
// } else if (widthBasedSize * (int) Math.ceil((double) Main.game.settings.boards / widthBasedRows) > getHeight()) {
// widthBasedSize = 0;
// }
//
// if (heightBasedSize > widthBasedSize) {
// numRows = heightBasedRows;
// rowWidth = heightBasedSize;
// } else {
// numRows = (int) Math.ceil((double) Main.game.settings.boards / widthBasedRows);
// rowWidth = widthBasedSize;
// }
//
// rowHeight = rowWidth * height / width;
//
// scale = (rowWidth - 2) / width;// Math.min(rowWidth / width, rowWidth * height / width / height);
// dx = (getWidth() - rowWidth * numRows) * 0.5 - scale * xmin;
// dy = (getHeight() - rowHeight * (int) Math.ceil((double) Main.game.settings.boards / numRows)) * 0.5 - scale * ymin;
	}

	private static final BufferedImage img;
	static {
		BufferedImage i;
		try {
			i = ImageIO.read(new File("/Users/etillison/Desktop/Ethan/Computer Club/BlockFace.png"));
		} catch (final Exception e) {
			i = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE);
		}
		img = i;
	}

	@Override
	public void paintComponent(final Graphics gg) {
		if (!(gg instanceof Graphics2D) || polygons == null || polygons.isEmpty()) return;

		final Graphics2D g = (Graphics2D) gg;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		final int width = (int) Math.round(scale / SQRT_2);

		int i = 0;
		for (final Set<Cell> board : polygons) {
			final double x = i++ / numRows * rowWidth;
			final double y = i % numRows * rowHeight;
			for (final Cell c : board) {
				g.setColor(c.color);
				g.fill(c.convertToPolygon(dx + x, dy + y, scale));
				if (Math.random() > 0.9) g.drawImage(img, (int) (c.centerX * scale + dx + x - 0.5 * width), (int) (c.centerY * scale + dy + y - 0.5 * width), width, width, null);
			}
		}
	}

	private static class Cell {

		public double[] xpoints, ypoints;
		public double centerX, centerY;
		public int npoints;
		public Color color;

		public Cell(final double[] xpoints, final double[] ypoints, final int npoints) {
			this.xpoints = xpoints;
			this.ypoints = ypoints;
			this.npoints = npoints;
		}

		public Polygon convertToPolygon(final double xshift, final double yshift, final double scale) {
			return new Polygon(Arrays.stream(xpoints).mapToInt(d -> (int) Math.round(d * scale + xshift)).toArray(), Arrays.stream(ypoints).mapToInt(d -> (int) Math.round(d * scale + yshift)).toArray(), npoints);
		}
	}

}
