package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameWinController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

public class GameWinView {
    private final GameWinController gameWinController;

    private BufferedImage img;
    private UrmButtonView menu;

    private int imgX, imgY, imgWidth, imgHeight;

    public GameWinView(GameWinController gameWinController) {
        this.gameWinController = gameWinController;
        createImg();
        createButton();
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);

        menu.draw(g);
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.WIN_SCREEN);
        imgWidth = (int) (img.getWidth() * GameController.SCALE);
        imgHeight = (int) (img.getHeight() * GameController.SCALE);
        imgX = GameController.GAME_WIDTH / 2 - imgWidth / 2;
        imgY = (int) (100 * GameController.SCALE);
    }

    private void createButton() {
        int x = (int) (385 * GameController.SCALE);
        int y = (int) (220 * GameController.SCALE);

        menu = new UrmButtonView(x, y, URM_SIZE, URM_SIZE, 2);
    }

    public UrmButtonView getMenu() {
        return menu;
    }
}
