package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameOverController;
import yeremiva.gitgud.controller.GameWinController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

public class GameWinView {
    private BufferedImage img;
    private int imgX, imgY, imgWidth, imgHeight;
    private UrmButtonView menu;
    private GameWinController gameWinController;

    public GameWinView(GameWinController gameWinController) {
        this.gameWinController = gameWinController;
        createImg();
        createButton();
    }

    private void createButton() {
        int menuX = (int) (335 * GameController.SCALE);
        int y = (int) (195 * GameController.SCALE);

        menu = new UrmButtonView(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.WIN_SCREEN);
        imgWidth = (int) (img.getWidth() * GameController.SCALE);
        imgHeight = (int) (img.getHeight() * GameController.SCALE);
        imgX = GameController.GAME_WIDTH / 2 - imgWidth / 2;
        imgY = (int) (100 * GameController.SCALE);
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);

        menu.draw(g);
    }

    public UrmButtonView getMenu() {
        return menu;
    }
}
