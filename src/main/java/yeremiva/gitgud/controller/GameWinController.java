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

        this.gameWinView = new GameWinView(this);
        this.menu = gameWinView.getMenu();
    }

    public void update() {
        menu.update();
    }

    public void draw(Graphics g) {
        gameWinView.draw(g);
    }

    private boolean isIn(UrmButtonView b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameProcessController.setWin(false);
            gameProcessController.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        }
    }

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

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        }
    }

    public GameWinView getGameWinView() {
        return gameWinView;
    }
}
