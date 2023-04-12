package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.view.LevelView;

import java.awt.*;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.controller.GameController.*;

//LEVEL MANAGER
public class LevelController {
    private GameController gameController;
    private BufferedImage[] levelSprite;
    private LevelView levelOne;

    public LevelController(GameController gameController){
        this.gameController = gameController;
        importOutsideSprites();
        levelOne = new LevelView(LoadSave.GetLevelData());
    }

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

    public void draw(Graphics g){
        for (int j = 0; j < TILES_IN_HEIGHT; j++){
            for (int i = 0; i < TILES_IN_WIDTH; i++){
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public LevelView getCurrentLevel() {
        return levelOne;
    }
}
