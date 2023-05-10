package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.UrmButton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.*;

public class LevelCompletedController {
    private GameProcessController gameProcessController;
    private UrmButton menu, nextLvl;
    private BufferedImage img;
    private int bgX, bgY, bgWidth, bgHeight;

    public LevelCompletedController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
        initImg();
        initButtons();
    }

    private void initButtons() {
        int menuX = (int) (330 * GameController.SCALE);
        int nextLvlX = (int) (445 * GameController.SCALE);
        int y = (int) (195 * GameController.SCALE);
        nextLvl = new UrmButton(nextLvlX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
     }

    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.COMPLETED_IMAGE);
        bgWidth = (int) (img.getWidth() * GameController.SCALE);
        bgHeight = (int) (img.getHeight() * GameController.SCALE);
        bgX = GameController.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (75 * GameController.SCALE);
    }

    public void update() {
        nextLvl.update();
        menu.update();
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0,0,GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, bgX, bgY, bgWidth, bgHeight, null);
        nextLvl.draw(g);
        menu.draw(g);
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
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
}
