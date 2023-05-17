package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

/**
 * Game Over View class.
 * <p>
 *     Class that represents the Game Over View.
 * </p>
 */
public class GameOverView {
    private UrmButtonView menu, play;
    private BufferedImage img;

    private int imgX, imgY, imgWidth, imgHeight;

    public GameOverView() {

        createImg();
        createButtons();
    }

    /**
     * Creation of the Game Over buttons.
     */
    private void createButtons() {
        int menuX = (int) (330 * GameController.SCALE);
        int playX = (int) (435 * GameController.SCALE);
        int y = (int) (195 * GameController.SCALE);
        play = new UrmButtonView(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButtonView(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    /**
     * Creation of the Game Over image.
     */
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgWidth = (int) (img.getWidth() * GameController.SCALE);
        imgHeight = (int) (img.getHeight() * GameController.SCALE);
        imgX = GameController.GAME_WIDTH / 2 - imgWidth / 2;
        imgY = (int) (100 * GameController.SCALE);
    }

    /**
     * Draw.
     * <p>
     *     Draws the Game Over background and buttons.
     * </p>
     *
     * @param g draw system
     */
    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);

        menu.draw(g);
        play.draw(g);
    }

    /**
     * Gets Menu Button.
     *
     * @return menu button
     */
    public UrmButtonView getMenu() {
        return menu;
    }

    /**
     * Gets Play Button.
     *
     * @return play button
     */
    public UrmButtonView getPlay() {
        return play;
    }
}
