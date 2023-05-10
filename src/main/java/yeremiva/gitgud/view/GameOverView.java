package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

public class GameOverView {

    private BufferedImage img;
    private int imgX, imgY, imgWidth, imgHeight;
    private UrmButton menu, play;

    public GameOverView() {
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (335 * GameController.SCALE);
        int playX = (int) (440 * GameController.SCALE);
        int y = (int) (195 * GameController.SCALE);
        play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
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
//        g.setColor(Color.white);
//        g.drawString("Game Over", GameController.GAME_WIDTH / 2, 150);
//        g.drawString("Press ESCAPE button to enther Main Menu!", GameController.GAME_WIDTH / 2, 300);
    }

    public UrmButton getMenu() {
        return menu;
    }

    public UrmButton getPlay() {
        return play;
    }
}
