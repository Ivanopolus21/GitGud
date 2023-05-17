package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.view.LevelView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import static yeremiva.gitgud.controller.GameController.*;

/**
 * Level Controller.
 * <p>
 *     Class that represents controller that control the levels.
 * </p>
 */
public class LevelController {
    private final static Logger log = Logger.getLogger(LevelController.class.getName());

    private final GameController gameController;

    private BufferedImage[] levelSprite;
    private final ArrayList<LevelView> levels;
    private int lvlIndex = 0;

    public LevelController(GameController gameController) {
        this.gameController = gameController;

        levels = new ArrayList<>();

        importOutsideSprites();
        buildAllLevels();
    }

    /**
     * Draw.
     * <p>
     *     Draws the level image depending on the sprite index of the image of tiles.
     * </p>
     *
     * @param g draw system
     * @param lvlOffset level offset
     */
    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - lvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    /**
     * Loads next level data.
     */
    public void loadNextLevel() {

        lvlIndex++;

        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            gameController.getGameProcessController().setWin(true);

            log.info("Game was completed! Congrats!");
        }

        LevelView newLevel = levels.get(lvlIndex);
        gameController.getGameProcessController().getPlayer().loadLvlData(newLevel.getLvlData());
        gameController.getGameProcessController().setMaxLvlOffsetX(newLevel.getMaxLvlOffsetX());
        gameController.getGameProcessController().getEnemyController().loadEnemies(newLevel);
        gameController.getGameProcessController().getObjectController().loadObjects(newLevel);

        if (lvlIndex >= 1) {
            log.info( "Level " + (lvlIndex + 1) + " was created");
        }
    }

    /**
     * Build levels.
     * <p>
     *     The method builds all available levels.
     * </p>
     */
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new LevelView(img));
        }
    }

    /**
     * Sprite import.
     * <p>
     *     Method that imports the level tiles.
     * </p>
     */
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void update() {
    }

    /**
     * Gets Current Level.
     *
     * @return the LevelView
     */
    public LevelView getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    /**
     * Gets Level Index.
     *
     * @return the lvlIndex
     */
    public int getLvlIndex() {
        return lvlIndex;
    }
}
