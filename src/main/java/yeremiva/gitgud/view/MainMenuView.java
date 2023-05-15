package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.MainMenuController;
import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MainMenuView {
    private final MainMenuController mainMenuController;
    private final MainMenuButtonView[] buttons = new MainMenuButtonView[3];

    private BufferedImage backgroundImgOfMenuOverlay;
    private final BufferedImage backgroundImg;

    private int menuX, menuY, menuWidth, menuHeight;

    public MainMenuView(MainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMAGE);

        loadButtons();
        loadBackground();
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT, null);

        g.drawImage(backgroundImgOfMenuOverlay, menuX, menuY, menuWidth, menuHeight, null);

        for (MainMenuButtonView gmb : buttons) {
            gmb.draw(g);
        }

    }

    private void loadBackground() {
        backgroundImgOfMenuOverlay = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImgOfMenuOverlay.getWidth() * GameController.SCALE);
        menuHeight = (int) (backgroundImgOfMenuOverlay.getHeight() * GameController.SCALE);
        menuX = GameController.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * GameController.SCALE);
    }

    //Function to load all game menu buttons
    private void loadButtons() {
        buttons[0] = new MainMenuButtonView(GameController.GAME_WIDTH / 2, (int) (150 * GameController.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MainMenuButtonView(GameController.GAME_WIDTH / 2, (int) (220 * GameController.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MainMenuButtonView(GameController.GAME_WIDTH / 2, (int) (290 * GameController.SCALE), 2, Gamestate.QUIT);
    }

    public MainMenuButtonView[] getButtons() {
        return buttons;
    }


}
