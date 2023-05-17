package yeremiva.gitgud.view;

import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.PauseButtons.*;

/**
 * Sound Button View class.
 * <p>
 *     Class that represents the Sound Button View.
 * </p>
 */
public class SoundButtonView extends PauseButton{
    private BufferedImage[][] soundImgs;

    private int rowIndex, columnIndex;
    private boolean mouseOver, mousePressed;
    private boolean muted;

    public SoundButtonView(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImgs();
    }

    /**
     * Update of the pause buttons.
     */
    public void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        columnIndex = 0;

        if (mouseOver) {
            columnIndex = 1;
        }
        if (mousePressed) {
            columnIndex = 2;
        }
    }

    /**
     * Draw of the pause buttons.
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][columnIndex], x, y, width, height, null);
    }

    /**
     * Load of Sound Buttons Images.
     */
    private void loadSoundImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int j = 0; j < soundImgs.length; j++){
            for (int i = 0; i < soundImgs[j].length; i++){
                soundImgs[j][i] = temp.getSubimage( i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    /**
     * Sets Muted.
     *
     * @param muted the muted
     */
    public void setMuted(boolean muted) {
        this.muted = muted;
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
     * Gets Muted.
     *
     * @return the muted
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Resets booleans.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
