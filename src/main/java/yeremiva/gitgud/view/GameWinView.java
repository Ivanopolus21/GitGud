package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameWinController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

/**
 * Game Win View class.
 * <p>
 *     Class that represents the Game Win View.
 * </p>
 */
public class GameWinView {
    private BufferedImage img;
    private UrmButtonView menu;

    private int imgX, imgY, imgWidth, imgHeight;

    public GameWinView() {
        createImg();
        createButton();
    }

    /**
     * Draw.
     * <p>
     *     The method draws game win view, menu button and black background.
     * </p>
     *
     * @param g draw system
     */
    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);

        menu.draw(g);
    }

    /**
     * Creation of the game win image.
     */
    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.WIN_SCREEN);
        imgWidth = (int) (img.getWidth() * GameController.SCALE);
        imgHeight = (int) (img.getHeight() * GameController.SCALE);
        imgX = GameController.GAME_WIDTH / 2 - imgWidth / 2;
        imgY = (int) (100 * GameController.SCALE);
    }

    /**
     * Creation of the game win buttons.
     */
    private void createButton() {
        int x = (int) (385 * GameController.SCALE);
        int y = (int) (220 * GameController.SCALE);

        menu = new UrmButtonView(x, y, URM_SIZE, URM_SIZE, 2);
    }

    /**
     * Gets Menu Button.
     *
     * @return the menu button
     */
    public UrmButtonView getMenu() {
        return menu;
    }
}
