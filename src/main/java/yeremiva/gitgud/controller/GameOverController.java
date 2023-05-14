package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GameOverView;
import yeremiva.gitgud.view.UrmButtonView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameOverController {
    private GameProcessController gameProcessController;
    private GameOverView gameOverView;
    private UrmButtonView menu;
    private UrmButtonView play;

    public GameOverController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
        this.gameOverView = new GameOverView(this);
        this.menu = gameOverView.getMenu();
        this.play = gameOverView.getPlay();
    }

    public void update() {
        menu.update();
        play.update();
    }

    public void draw(Graphics g) {
        gameOverView.draw(g);
    }

    private boolean isIn(UrmButtonView b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameProcessController.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(play, e)) {
            play.setMouseOver(true);
        }
    }

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

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(play, e)) {
            play.setMousePressed(true);
        }
    }

    public GameOverView getGameOverView() {
        return gameOverView;
    }
}
