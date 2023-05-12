package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.LevelCompletedView;
import yeremiva.gitgud.view.PauseView;
import yeremiva.gitgud.view.UrmButton;

import java.awt.*;
import java.awt.event.MouseEvent;

public class LevelCompletedController {
    private GameProcessController gameProcessController;
    private LevelCompletedView levelCompletedView;
    private UrmButton menu, nextLvl;

    public LevelCompletedController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
        this.levelCompletedView = new LevelCompletedView(this);
        this.menu = levelCompletedView.getMenu();
        this.nextLvl = levelCompletedView.getNextLvl();
    }

    public void update() {
        nextLvl.update();
        menu.update();
    }

    public void draw(Graphics g) {
        levelCompletedView.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
        nextLvl.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(nextLvl, e)) {
            nextLvl.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                gameProcessController.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(nextLvl, e)) {
            if (nextLvl.isMousePressed()) {
                gameProcessController.loadNextLevel();
            }
        }
        menu.resetBools();
        nextLvl.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(nextLvl, e)) {
            nextLvl.setMousePressed(true);
        }
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
