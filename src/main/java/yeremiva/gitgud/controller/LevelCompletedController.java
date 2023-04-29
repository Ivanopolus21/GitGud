package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
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

    }

    public void draw(Graphics g) {
        g.drawImage(img, bgX, bgY, bgWidth, bgHeight, null);
        nextLvl.draw(g);
        menu.draw(g);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }
}
