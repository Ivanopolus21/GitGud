package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.PauseButtons.SOUND_SIZE;
import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;
import static yeremiva.gitgud.core.settings.Constants.View.VolumeButtons.SLIDER_WIDTH;
import static yeremiva.gitgud.core.settings.Constants.View.VolumeButtons.VOLUME_HEIGHT;

/**
 * Pause View class.
 * <p>
 *     Class that represents the Pause View.
 * </p>
 */
public class PauseView {
    private SoundButtonView musicButton, sfxButton;
    private UrmButtonView menuB, replayB, unpauseB;
    private VolumeButton volumeButton;
    private BufferedImage backgroundImg;

    private int bgX, bgY, bgWidth, bgHeight;

    public PauseView() {
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    /**
     * Creation of the volume button.
     */
    private void createVolumeButton() {
        int volumeX = (int) (309 * GameController.SCALE);
        int volumeY = (int) (258 * GameController.SCALE);

        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    /**
     * Creation of the URM buttons (replay, menu, unpause).
     */
    private void createUrmButtons() {
        int menuX = (int) (313 * GameController.SCALE);
        int replayX = (int) (387 * GameController.SCALE);
        int unpauseX = (int) (462 * GameController.SCALE);
        int buttonY = (int) (305 * GameController.SCALE);

        menuB = new UrmButtonView(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButtonView(replayX, buttonY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButtonView(unpauseX, buttonY, URM_SIZE, URM_SIZE, 0);
    }

    /**
     * Creation of the sound buttons.
     */
    private void createSoundButtons() {
        int soundX = (int) (450 * GameController.SCALE);
        int musicY = (int) (115 * GameController.SCALE);
        int sfxY = (int) (161 * GameController.SCALE);
        musicButton = new SoundButtonView(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButtonView(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    /**
     * Load of the pause background.
     */
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImg.getWidth() * GameController.SCALE);
        bgHeight = (int) (backgroundImg.getHeight() * GameController.SCALE);
        bgX = GameController.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (80 * GameController.SCALE);
    }

    /**
     * Draw.
     * <p>
     *     Method that draws pause background and pause buttons.
     * </p>
     *
     * @param g draw system
     */
    public void draw(Graphics g){
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        //Background
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);

        //Sound buttons
        musicButton.draw(g);
        sfxButton.draw(g);

        //Urm buttons
        menuB.draw(g);
        replayB.draw(g);
        unpauseB.draw(g);

        //Volume slider
        volumeButton.draw(g);
    }

    /**
     * Gets Music Button.
     *
     * @return the music button
     */
    public SoundButtonView getMusicButton() {
        return musicButton;
    }

    /**
     * Gets Sound Button.
     *
     * @return the sound button.
     */
    public SoundButtonView getSfxButton() {
        return sfxButton;
    }

    /**
     * Gets Menu Button.
     *
     * @return the menu button
     */
    public UrmButtonView getMenuB() {
        return menuB;
    }

    /**
     * Gets Replay Button.
     *
     * @return the replau button
     */
    public UrmButtonView getReplayB() {
        return replayB;
    }

    /**
     * Gets Unpause Button.
     *
     * @return the unpause button
     */
    public UrmButtonView getUnpauseB() {
        return unpauseB;
    }

    /**
     * Gets Volume Button.
     *
     * @return the volume button
     */
    public VolumeButton getVolumeButton() {
        return volumeButton;
    }

}
