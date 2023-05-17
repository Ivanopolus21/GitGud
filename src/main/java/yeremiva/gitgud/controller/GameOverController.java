package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GameOverView;
import yeremiva.gitgud.view.UrmButtonView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Game Over Controller.
 * <p>
 *     Class that represents controller that control the game over overlay.
 * </p>
 */
public class GameOverController {
    private final GameProcessController gameProcessController;
    private final GameOverView gameOverView;

    private final UrmButtonView menu;
    private final UrmButtonView play;

    public GameOverController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        gameOverView = new GameOverView();

        menu = gameOverView.getMenu();
        play = gameOverView.getPlay();
    }

    /**
     * Update.
     * <p>
     * Update of the game over buttons.
     */
    public void update() {
        menu.update();
        play.update();
    }

    /**
     * Draw.
     * <p>
     * Draw of the view.
     *
     * @param g draw system
     */
    public void draw(Graphics g) {
        gameOverView.draw(g);
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
    private boolean isIn(UrmButtonView b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Key Press.
     * <p>
     * Method that pass the player to the main menu in case he clicked "Escape" button after game over.
     *
     * @param e the e
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameProcessController.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    /**
     * Mouse Moved.
     * <p>
     * Method that checks for hovering on buttons.
     *
     * @param e the e
     */
    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(play, e)) {
            play.setMouseOver(true);
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
            }
        } else if (isIn(play, e)) {
            if (play.isMousePressed()) {
                gameProcessController.resetAll();
            }
        }
        menu.resetBools();
        play.resetBools();
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
        } else if (isIn(play, e)) {
            play.setMousePressed(true);
        }
    }

    /**
     * Gets Game Over View.
     *
     * @return the GameOverView
     */
    public GameOverView getGameOverView() {
        return gameOverView;
    }
}
