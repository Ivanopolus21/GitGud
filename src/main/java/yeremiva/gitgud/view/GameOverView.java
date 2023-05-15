package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameOverController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

public class GameOverView {
    private final GameOverController gameOverController;

    private UrmButtonView menu, play;
    private BufferedImage img;

    private int imgX, imgY, imgWidth, imgHeight;

    public GameOverView(GameOverController gameOverController) {
        this.gameOverController = gameOverController;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (330 * GameController.SCALE);
        int playX = (int) (435 * GameController.SCALE);
        int y = (int) (195 * GameController.SCALE);
        play = new UrmButtonView(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtonView(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgWidth = (int) (img.getWidth() * GameController.SCALE);
        imgHeight = (int) (img.getHeight() * GameController.SCALE);
        imgX = GameController.GAME_WIDTH / 2 - imgWidth / 2;
        imgY = (int) (100 * GameController.SCALE);
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);

        menu.draw(g);
        play.draw(g);
    }

    public UrmButtonView getMenu() {
        return menu;
    }

    public UrmButtonView getPlay() {
        return play;
    }
}
