package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.LevelCompletedView;
import yeremiva.gitgud.view.UrmButtonView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

/**
 * Level Completed Controller.
 * <p>
 *     Class that represents controller that control the level completion.
 * </p>
 */
public class LevelCompletedController {
    private final static Logger log = Logger.getLogger(LevelCompletedController.class.getName());

    private final GameProcessController gameProcessController;
    private final LevelCompletedView levelCompletedView;

    private final UrmButtonView menu, nextLvl;

    public LevelCompletedController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        levelCompletedView = new LevelCompletedView();
        menu = levelCompletedView.getMenu();
        nextLvl = levelCompletedView.getNextLvl();
    }

    /**
     * Update.
     * <p>
     *     Update of the menu and next level buttons.
     * </p>
     */
    public void update() {
        nextLvl.update();
        menu.update();
    }

    /**
     * Draw.
     * <p>
     *     Draw of the level completed screen.
     * </p>
     *
     * @param g
     */
    public void draw(Graphics g) {
        levelCompletedView.draw(g);
    }

    /**
     * Is in a button.
     * <p>
     *     Check if players' cursor is "in" a button bounds.
     * </p>
     *
     * @param b the button
     * @param e the e
     * @return the boolean
     */
    private boolean isIn(UrmButtonView b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Mouse Moved.
     * <p>
     * Method that checks for hovering on buttons.
     *
     * @param e the e
     */
    public void mouseMoved(MouseEvent e) {
        nextLvl.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(nextLvl, e)) {
            nextLvl.setMouseOver(true);
        }
    }

    /**
     * Mouse Released.
     * <p>
     * Method that call the other methods depending on which button mouse released the press.
     *
     * @param e the e
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                gameProcessController.resetAll();
                Gamestate.state = Gamestate.MENU;

                log.info(Gamestate.MENU + " state was set");
            }
        } else if (isIn(nextLvl, e)) {
            if (nextLvl.isMousePressed()) {
                gameProcessController.loadNextLevel();

                if (gameProcessController.getLevelController().getLvlIndex() >= 1) {
                    log.info("Next level was set");
                }
            }
        }
        menu.resetBools();
        nextLvl.resetBools();
    }

    /**
     * Mouse Pressed.
     * <p>
     * Method that checks for pressing the buttons.
     *
     * @param e the e
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(nextLvl, e)) {
            nextLvl.setMousePressed(true);
        }
    }
}
