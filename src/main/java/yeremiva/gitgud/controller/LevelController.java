package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.LevelView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static yeremiva.gitgud.controller.GameController.*;

//LEVEL MANAGER
public class LevelController {
    private GameController gameController;
    private BufferedImage[] levelSprite;
    private ArrayList<LevelView> levels;
    private int lvlIndex = 0;

    public LevelController(GameController gameController){
        this.gameController = gameController;
        importOutsideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    public void loadNextLevel() {
        lvlIndex++;
        if (lvlIndex >= levels.size()) {
            lvlIndex = 0;
            System.out.println("no more levels! Game completed!");
            Gamestate.state = Gamestate.MENU;
        }

        LevelView newLevel = levels.get(lvlIndex);
        gameController.getGameProcessController().getEnemyController().loadEnemies(newLevel);
        gameController.getGameProcessController().getPlayer().loadLvlData(newLevel.getLvlData());
        gameController.getGameProcessController().setMaxLvlOffsetX(newLevel.getMaxLvlOffsetX());
    }
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels) {
            levels.add(new LevelView(img));
        }
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

    public void draw(Graphics g, int lvlOffset){
        for (int j = 0; j < TILES_IN_HEIGHT; j++){
            for (int i = 0; i < levels.get(lvlIndex).getLvlData()[0].length; i++){
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], TILES_SIZE * i - lvlOffset, TILES_SIZE * j, TILES_SIZE, TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public LevelView getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }
}
