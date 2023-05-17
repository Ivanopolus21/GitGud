package yeremiva.gitgud.view;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.Buttons.*;

/**
 * Main Menu Button View class.
 * <p>
 *     Class that represents the Main Menu Button View.
 * </p>
 */
public class MainMenuButtonView {
    private final Gamestate state;

    private Rectangle bounds;
    private BufferedImage[] imgs;

    private final int xPos, yPos, rowIndex;
    private int index;
    private final int xOffsetCenter = B_WIDTH / 2;
    private boolean mouseOver, mousePressed;

    //Function to get menu buttons position
    public MainMenuButtonView(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;

        initBounds();
        loadImgs();
    }

    /**
     * Loads the images of the buttons.
     */
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i =  0; i < imgs.length; i++){
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    /**
     * Updates the buttons.
     */
    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    /**
     * Draw.
     * <p>
     *     Draws the main menu buttons.
     * </p>
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    /**
     * Initializes buttons bounds.
     */
    public void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    /**
     * Applies game state.
     */
    public void applyGamestate() {
        Gamestate.state = state;
    }

    /**
     * Sets Mouse Over.
     *
     * @param mouseOver the mouseOver
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Sets Mouse Pressed.
     *
     * @param mousePressed the mousePressed
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
     * Gets Button Bounds.
     *
     * @return the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Gets state.
     *
     * @return state
     */
    public Gamestate getState() {
        return state;
    }

    /**
     * Resets the mouse booleans.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
