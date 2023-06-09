package yeremiva.gitgud.view;

import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.VolumeButtons.*;

/**
 * Volume Button View class.
 * <p>
 *     Class that represents the Volume Button View.
 * </p>
 */
public class VolumeButton extends PauseButton{
    private BufferedImage[] imgs;
    private BufferedImage slider;

    private int index = 0;
    private boolean mouseOver, mousePressed;
    private final int minX, maxX;
    private int buttonX;

    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, VOLUME_WIDTH, height);
        this.x = x;
        this.width = width;

        bounds.x -= VOLUME_WIDTH / 2;
        buttonX = x + width / 2;
        minX = x + VOLUME_WIDTH / 2;
        maxX = width + x - VOLUME_WIDTH / 2;

        loadImgs();
    }

    /**
     * Update of the volume buttons.
     */
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    /**
     * Draw of the volume buttons.
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - VOLUME_WIDTH / 2, y, VOLUME_WIDTH, height, null);
    }

    /**
     * Load of Volume Buttons Images.
     */
    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
        }
        slider = temp.getSubimage(3 * VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
    }

    /**
     * Change the X.
     * <p>
     *     Change of the X position according to the player set.
     * </p>
     *
     * @param x the x
     */
    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }

        bounds.x = buttonX - VOLUME_WIDTH / 2;
    }

    /**
     * Sets Mouse Over.
     *
     * @param mouseOver the mouse over
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Sets Mouse Pressed.
     *
     * @param mousePressed the mouse pressed
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Gets Mouse Pressed.
     *
     * @return the mousePressed
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Resets booleans.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
