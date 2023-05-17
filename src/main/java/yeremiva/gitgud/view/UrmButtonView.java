package yeremiva.gitgud.view;

import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.*;

/**
 * View class.
 * <p>
 *     Class that represents the URM Button View.
 * </p>
 */
public class UrmButtonView extends PauseButton{
    private BufferedImage[] imgs;

    private final int rowIndex;
    private int index;
    private boolean mouseOver, mousePressed;

    public UrmButtonView(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;

        loadImgs();
    }

    /**
     * Update of the URM buttons.
     */
    public void update() {
        index = 0;
        if (mouseOver){
            index = 1;
        }
        if (mousePressed){
            index = 2;
        }
    }

    /**
     * Draw of the URM buttons.
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
    }

    /**
     * Load of URM Buttons Images.
     */
    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++){
            imgs[i] = temp.getSubimage(i * URM_DEFAULT_SIZE, rowIndex * URM_DEFAULT_SIZE, URM_DEFAULT_SIZE, URM_DEFAULT_SIZE);
        }
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
