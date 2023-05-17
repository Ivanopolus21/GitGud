package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Pause Controller.
 * <p>
 *     Class that represents controller that control the pause overlay.
 * </p>
 */
public class PauseController {
    private final static Logger log = Logger.getLogger(PauseController.class.getName());

    private final GameProcessController gameProcessController;
    private final PauseView pauseView;

    private final SoundButtonView musicButton, sfxButton;
    private final UrmButtonView menuB, replayB, unpauseB;
    private final VolumeButton volumeButton;

    public PauseController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        pauseView = new PauseView();

        musicButton = pauseView.getMusicButton();
        sfxButton = pauseView.getSfxButton();
        menuB = pauseView.getMenuB();
        replayB = pauseView.getReplayB();
        unpauseB = pauseView.getUnpauseB();
        volumeButton = pauseView.getVolumeButton();
    }

    /**
     * Update.
     * <p>
     *     Update of the pause menu buttons.
     * </p>
     */
    public void update() {
        musicButton.update();
        sfxButton.update();

        menuB.update();
        replayB.update();
        unpauseB.update();

        volumeButton.update();
    }

    /**
     * Draw.
     * <p>
     *     Draw method that calls Pause View draw.
     * </p>
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        pauseView.draw(g);
    }

    /**
     * Is in a button.
     * <p>
     * Check if players' cursor is "in" a button bounds.
     *
     * @param b the button
     * @param e the e
     * @return the boolean
     */
    public boolean isIn(MouseEvent e, PauseButton b) {
        return (b.getBounds().contains(e.getX(), e.getY()));
    }

    /**
     * Mouse Dragged.
     * <p>
     *     Checks if mouse has been dragged to change the state of the volume button.
     * </p>
     *
     * @param e the e
     */
    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            volumeButton.changeX(e.getX());
        }
    }

    /**
     * Mouse Pressed.
     * <p>
     *     Checks if mouse has been pressed.
     * </p>
     *
     * @param e the e
     */
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

    /**
     * Mouse Released.
     * <p>
     *     Checks if mouse has been released to apply states for the buttons or to the game.
     * </p>
     *
     * @param e the e
     */
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

    /**
     * Mouse Moved.
     * <p>
     *     Checks if mouse has been moved.
     * </p>
     *
     * @param e the e
     */
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
