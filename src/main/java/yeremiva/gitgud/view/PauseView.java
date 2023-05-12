package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.controller.PauseController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.PauseButtons.SOUND_SIZE;
import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;
import static yeremiva.gitgud.core.settings.Constants.View.VolumeButtons.SLIDER_WIDTH;
import static yeremiva.gitgud.core.settings.Constants.View.VolumeButtons.VOLUME_HEIGHT;

public class PauseView {
    private PauseController pauseController;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgWidth, bgHeight;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseView(PauseController pauseController) {
        this.pauseController = pauseController;

        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeX = (int) (309 * GameController.SCALE);
        int volumeY = (int) (258 * GameController.SCALE);
        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    //The method creates URM buttons (replay, menu, unpause)
    private void createUrmButtons() {
        int menuX = (int) (313 * GameController.SCALE);
        int replayX = (int) (387 * GameController.SCALE);
        int unpauseX = (int) (462 * GameController.SCALE);
        int buttonY = (int) (305 * GameController.SCALE);

        menuB = new UrmButton(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, buttonY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, buttonY, URM_SIZE, URM_SIZE, 0);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * GameController.SCALE);
        int musicY = (int) (115 * GameController.SCALE);
        int sfxY = (int) (161 * GameController.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    //Function that loads background of the pause menu
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImg.getWidth() * GameController.SCALE);
        bgHeight = (int) (backgroundImg.getHeight() * GameController.SCALE);
        bgX = GameController.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (80 * GameController.SCALE);
    }

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

    public SoundButton getMusicButton() {
        return musicButton;
    }

    public SoundButton getSfxButton() {
        return sfxButton;
    }

    public UrmButton getMenuB() {
        return menuB;
    }

    public UrmButton getReplayB() {
        return replayB;
    }

    public UrmButton getUnpauseB() {
        return unpauseB;
    }

    public VolumeButton getVolumeButton() {
        return volumeButton;
    }

}
