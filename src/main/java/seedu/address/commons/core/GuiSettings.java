package seedu.address.commons.core;

import java.awt.Point;
import java.awt.Toolkit;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final double MAX_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final double MAX_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final int MIN_Y = 0;
    private static final int MAX_Y = (int) MAX_HEIGHT / 2;
    private static final int MAX_ABSOLUTE_X = (int) MAX_WIDTH / 2;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;

    /**
     * Constructs a {@code GuiSettings} with the default height, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width and position.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition) {
        this.windowWidth = Math.min(windowWidth, MAX_WIDTH);
        this.windowHeight = Math.min(windowHeight, MAX_HEIGHT);
        windowCoordinates = getTruncatedWindowCoordinates(xPosition, yPosition);
    }

    public double getWindowWidth() {
        return Math.min(windowWidth, MAX_WIDTH);
    }

    public double getWindowHeight() {
        return Math.min(windowHeight, MAX_HEIGHT);
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null
                ? getTruncatedWindowCoordinates((int) windowCoordinates.getX(), (int) windowCoordinates.getY())
                : null;
    }

    private Point getTruncatedWindowCoordinates(int x, int y) {
        int truncatedX = x;
        int truncatedY = y;
        if (x < -MAX_ABSOLUTE_X) {
            truncatedX = -MAX_ABSOLUTE_X;
        } else if (x > MAX_ABSOLUTE_X) {
            truncatedX = MAX_ABSOLUTE_X;
        }
        if (y < MIN_Y) {
            truncatedY = MIN_Y;
        } else if (y > MAX_Y) {
            truncatedY = MAX_Y;
        }
        return new Point(truncatedX, truncatedY);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return windowWidth == o.windowWidth
                && windowHeight == o.windowHeight
                && Objects.equals(windowCoordinates, o.windowCoordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        return sb.toString();
    }
}
