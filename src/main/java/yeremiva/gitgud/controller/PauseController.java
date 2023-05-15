package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class PauseController {
    private final static Logger log = Logger.getLogger(PauseController.class.getName());

    private GameProcessController gameProcessController;
    private PauseView pauseView;

    private SoundButtonView musicButton, sfxButton;
    private UrmButtonView menuB, replayB, unpauseB;
    private VolumeButton volumeButton;

    public PauseController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        this.pauseView = new PauseView(this);
        this.musicButton = pauseView.getMusicButton();
        this.sfxButton = pauseView.getSfxButton();
        this.menuB = pauseView.getMenuB();
        this.replayB = pauseView.getReplayB();
        this.unpauseB = pauseView.getUnpauseB();
        this.volumeButton = pauseView.getVolumeButton();
    }

    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();
    }

    public void draw(Graphics g) {
        pauseView.draw(g);
    }

    public boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }

    public void mouseDragged(MouseEvent e) {
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
                gameProcessController.resetAll();
                Gamestate.state = Gamestate.MENU;
                gameProcessController.unpauseGame();

                log.info(Gamestate.MENU + " state was setted");
            }
        } else if (isIn(e, replayB)) {
            if (replayB.isMousePressed()) {
                gameProcessController.resetAll();
                gameProcessController.unpauseGame();

                log.info("Level replay");
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
}
