package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static yeremiva.gitgud.core.settings.Constants.Environment.*;

/**
 * Game Process View class.
 * <p>
 *     Class that represents the Game Process View.
 * </p>
 */
public class GameProcessView {
    private final GameProcessController gameProcessController;

    private final BufferedImage backgroundImg, bigBgObject, smallBgObject;

    private final int[] smallBgObjectsPos;

    public GameProcessView(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
        bigBgObject = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallBgObject = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallBgObjectsPos = new int[8];
        for (int i = 0; i < smallBgObjectsPos.length; i++) {
            Random rnd = new Random();
            smallBgObjectsPos[i] = (int) (90 * GameController.SCALE) + rnd.nextInt((int) (100 * GameController.SCALE));
        }
    }

    /**
     * Draw.
     * <p>
     *     Draws all states in the game, all models and views.
     * </p>
     *
     * @param g draw system
     * @param xLvlOffset the level offset
     */
    public void draw(Graphics g, int xLvlOffset) {
        g.drawImage(backgroundImg, 0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT, null);

        drawBgObjects(g, xLvlOffset);

        gameProcessController.getLevelController().draw(g, xLvlOffset);
        gameProcessController.getPlayer().render(g, xLvlOffset);
        gameProcessController.getEnemyController().draw(g, xLvlOffset);
        gameProcessController.getObjectController().draw(g, xLvlOffset);

        if (gameProcessController.isPaused()) {
            gameProcessController.getPauseController().draw(g);
        } else if (gameProcessController.isGameOver()) {
            gameProcessController.getGameOverController().draw(g);
        } else if (gameProcessController.isWin()) {
            gameProcessController.getGameWinController().draw(g);
        } else if (gameProcessController.isLvlCompleted()) {
            gameProcessController.getLevelCompletedController().draw(g);
        }
    }

    /**
     * Draws background noninteractive objects.
     * @param g draw system
     * @param xLvlOffset the level offset
     */
    private void drawBgObjects(Graphics g, int xLvlOffset) {
        for (int i = 0; i < 3; i++)
            g.drawImage(bigBgObject, i * BIG_BG_OBJECT_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * GameController.SCALE), BIG_BG_OBJECT_WIDTH, BIG_BG_OBJECT_HEIGHT, null);

        for (int i = 0; i < smallBgObjectsPos.length; i++)
            g.drawImage(smallBgObject, SMALL_BG_OBJECT_WIDTH * 4 * i - (int) (xLvlOffset * 0.7), smallBgObjectsPos[i], SMALL_BG_OBJECT_WIDTH, SMALl_BG_OBJECT_HEIGHT, null);
    }
}
