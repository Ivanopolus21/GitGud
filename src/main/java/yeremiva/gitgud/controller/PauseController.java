package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.PauseButton;
import yeremiva.gitgud.view.SoundButton;
import yeremiva.gitgud.view.UrmButton;
import yeremiva.gitgud.view.VolumeButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.PauseButtons.*;
import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.*;
import static yeremiva.gitgud.core.settings.Constants.View.VolumeButtons.*;

//PAUSE OVERLAY
public class PauseController {
    private GameProcessController gameProcessController;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgWidth, bgHeight;
    private SoundButton musicButton, sfxButton;
    private UrmButton menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseController(GameProcessController gameProcessController){
        this.gameProcessController = gameProcessController;
        loadBackground();
        createSoundButtons();
        createUrmButtons();
        createVolumeButton();
    }

    private void createVolumeButton() {
        int volumeX = (int) (309 * GameController.SCALE);
        int volumeY = (int) (278 * GameController.SCALE);
        volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    //The method creates URM buttons (replay, menu, unpause)
    private void createUrmButtons() {
        int menuX = (int) (313 * GameController.SCALE);
        int replayX = (int) (387 * GameController.SCALE);
        int unpauseX = (int) (462 * GameController.SCALE);
        int buttonY = (int) (325 * GameController.SCALE);

        menuB = new UrmButton(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
        replayB = new UrmButton(replayX, buttonY, URM_SIZE, URM_SIZE, 1);
        unpauseB = new UrmButton(unpauseX, buttonY, URM_SIZE, URM_SIZE, 0);
    }

    private void createSoundButtons() {
        int soundX = (int) (450 * GameController.SCALE);
        int musicY = (int) (140 * GameController.SCALE);
        int sfxY = (int) (186 * GameController.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    //Function that loads background of the pause menu
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImg.getWidth() * GameController.SCALE);
        bgHeight = (int) (backgroundImg.getHeight() * GameController.SCALE);
        bgX = GameController.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (25 * GameController.SCALE);
    }

    public void update(){
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();
    }

    public void draw(Graphics g){
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

    public void mouseDragged(MouseEvent e){
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        } else if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e, replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMousePressed(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        } else if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
                gameProcessController.unpauseGame();
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                System.out.println("Replay lvl!");
            }
        } else if (isIn(e, unpauseB)) {
            if (unpauseB.isMousePressed()) {
                gameProcessController.unpauseGame();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        menuB.resetBools();
        replayB.resetBools();
        unpauseB.resetBools();
        volumeButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        } else if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else if (isIn(e, replayB)) {
            replayB.setMouseOver(true);
        } else if (isIn(e, unpauseB)) {
            unpauseB.setMouseOver(true);
        } else if (isIn(e, volumeButton)) {
            volumeButton.setMouseOver(true);
        }
    }

    public boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }
}
