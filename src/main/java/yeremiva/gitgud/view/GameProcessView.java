package yeremiva.gitgud.view;


import yeremiva.gitgud.Game;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static yeremiva.gitgud.core.settings.Constants.Enviroment.*;

public class GameProcessView {
    private GameProcessController gameProcessController;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    public GameProcessView(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++)
            smallCloudsPos[i] = (int) (90 * GameController.SCALE) + rnd.nextInt((int) (100 * GameController.SCALE));
    }

    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(backgroundImg, 0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT, null);

        drawClouds(g, xLvlOffset);

        gameProcessController.getLevelController().draw(g, xLvlOffset);
        gameProcessController.getPlayer().render(g, xLvlOffset);
        gameProcessController.getEnemyController().draw(g, xLvlOffset);
        gameProcessController.getObjectController().draw(g, xLvlOffset);

        if (gameProcessController.isPaused()) {
            gameProcessController.getPauseController().draw(g);
        } else if (gameProcessController.isGameOver()) {
            gameProcessController.getGameOverController().getGameOverView().draw(g);
        } else if (gameProcessController.isWin()) {
            gameProcessController.getGameWinController().draw(g);
        } else if (gameProcessController.isLvlCompleted()) {
            gameProcessController.getLevelCompletedController().draw(g);
        }
    }

    private void drawClouds(Graphics g, int xLvlOffset) {
        for (int i = 0; i < 3; i++)
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * GameController.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);

        for (int i = 0; i < smallCloudsPos.length; i++)
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALl_CLOUD_HEIGHT, null);
    }
}
