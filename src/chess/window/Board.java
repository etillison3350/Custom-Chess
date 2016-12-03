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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

public class Board extends JComponent {

	public static final double SQRT_3 = 1.7320508076;

	private double scale, dx, dy;

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

	private Set<Cell> polygons;
	private double xmin, xmax, ymin, ymax;

	private final double rotation = Math.PI;
	private final int board = 1;
	private final Set<Point> missing = new HashSet<>();

	public void update() {
		polygons = new HashSet<>();
		xmin = ymin = Integer.MAX_VALUE;
		xmax = ymax = Integer.MIN_VALUE;

		final int width = 20, length = 20, height = 0;
		final double sin = Math.sin(rotation), cos = Math.cos(rotation);

		switch (board) {
			case 0:
				for (int y = 0; y < length; y++) {
					for (int x = 0; x < width; x++) {
						if (missing.contains(new Point(x, y))) continue;

						final Point2D p00 = new Point2D.Double(x * cos - y * sin, x * sin + y * cos);
						final Point2D p10 = new Point2D.Double((x + 1) * cos - y * sin, (x + 1) * sin + y * cos);
						final Point2D p01 = new Point2D.Double(x * cos - (y + 1) * sin, x * sin + (y + 1) * cos);
						final Point2D p11 = new Point2D.Double((x + 1) * cos - (y + 1) * sin, (x + 1) * sin + (y + 1) * cos);

						final Cell c = new Cell(new double[] {p00.getX(), p10.getX(), p11.getX(), p01.getX()}, new double[] {p00.getY(), p10.getY(), p11.getY(), p01.getY()}, 4);
						c.color = (x + y) % 2 == 0 ? new Color(128, 64, 32) : new Color(255, 192, 128);
						polygons.add(c);
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
				break;
			case 1:
				for (int a = 0; a < length + height - 1; a++) {
					for (int b = 0; b < width + height - 1; b++) {
						if (a - b >= length || b - a >= width || missing.contains(new Point(a, b))) continue;

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
						polygons.add(c);
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
				break;
		}
	}

	public void recalculateSize() {
		final double width = xmax - xmin;
		final double height = ymax - ymin;
		scale = Math.min(getWidth() / width, getHeight() / height);
		dx = (getWidth() - scale * width) / 2.0 - scale * xmin;
		dy = (getHeight() - scale * height) / 2.0 - scale * ymin;
	}

	@Override
	public void paintComponent(final Graphics gg) {
		if (!(gg instanceof Graphics2D) || polygons == null || polygons.isEmpty()) return;

		final Graphics2D g = (Graphics2D) gg;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (final Cell c : polygons) {
			g.setColor(c.color);
			g.fill(c.convertToPolygon(dx, dy, scale));
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
