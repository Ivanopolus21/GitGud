package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.LevelCompletedView;
import yeremiva.gitgud.view.UrmButtonView;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class LevelCompletedController {
    private final static Logger log = Logger.getLogger(LevelCompletedController.class.getName());

    private final GameProcessController gameProcessController;
    private final LevelCompletedView levelCompletedView;

    private final UrmButtonView menu, nextLvl;

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

    private boolean isIn(UrmButtonView b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
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

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(nextLvl, e)) {
            nextLvl.setMousePressed(true);
        }
    }
}
