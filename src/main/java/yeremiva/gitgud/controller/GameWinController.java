package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GameWinView;
import yeremiva.gitgud.view.UrmButtonView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class GameWinController {
    private final static Logger log = Logger.getLogger(GameWinController.class.getName());

    private final GameProcessController gameProcessController;
    private final GameWinView gameWinView;
    private final UrmButtonView menu;

    public GameWinController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        gameWinView = new GameWinView();
        menu = gameWinView.getMenu();
    }

    /**
     * Update.
     * <p>
     *     Update of the menu button.
     * </p>
     */
    public void update() {
        menu.update();
    }

    /**
     * Draw.
     * <p>
     *     Draw of the game win screen.
     * </p>
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        gameWinView.draw(g);
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
     * Key Press.
     * <p>
     * Method that pass the player to the main menu in case he clicked "Escape" button after game win.
     *
     * @param e the e
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameProcessController.setWin(false);
            gameProcessController.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    /**
     * Mouse Moved.
     * <p>
     * Method that checks for hovering on button.
     *
     * @param e the e
     */
    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        }
    }

    /**
     * Mouse Released.
     * <p>
     *     Method that call the other methods depending on which button mouse released the press.
     * </p>
     *
     * @param e the e
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                gameProcessController.setWin(false);
                gameProcessController.resetAll();
                Gamestate.state = Gamestate.MENU;

                log.info(Gamestate.MENU + " state was set");
            }
        }
        menu.resetBools();
    }

    /**
     * Mouse Pressed.
     * <p>
     * Method that checks for pressing the button.
     *
     * @param e the e
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        }
    }
}
