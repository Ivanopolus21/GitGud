package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.LevelCompletedController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

public class LevelCompletedView {

    private LevelCompletedController levelCompletedController;
    private UrmButton menu, nextLvl;
    private BufferedImage img;
    private int bgX, bgY, bgWidth, bgHeight;

    public LevelCompletedView(LevelCompletedController levelCompletedController) {
        this.levelCompletedController = levelCompletedController;
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

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0,0,GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, bgX, bgY, bgWidth, bgHeight, null);
        nextLvl.draw(g);
        menu.draw(g);
    }

    public UrmButton getMenu() {
        return menu;
    }

    public UrmButton getNextLvl() {
        return nextLvl;
    }
}
