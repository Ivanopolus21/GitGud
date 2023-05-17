package yeremiva.gitgud.view;

import java.awt.*;

/**
 * Pause Button class.
 * <p>
 *     Represents the pause buttons class.
 * </p>
 */
public class PauseButton {
    protected Rectangle bounds;

    protected int x, y, width, height;

    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        createBounds();
    }

    /**
     * Creates bounds of the pause buttons.
     */
    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Sets width of the buttons.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets height of the buttons.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets X of the buttons.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets Y of the buttons.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets X.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets Y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Gets Width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets Height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets buttons bounds.
     *
     * @return the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }
}
