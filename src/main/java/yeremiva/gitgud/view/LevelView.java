package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.model.characters.Skeleton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.HelpMethods.*;

//LEVEL
public class LevelView {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Skeleton> skeletons;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public LevelView(BufferedImage img){
        this.img = img;
        createLevelData();
        createEnemies();
        calculateLevelOffsets();
        calculatePlayerSpawn();
    }

    private void calculatePlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calculateLevelOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - GameController.TILES_IN_WIDTH;
        maxLvlOffsetX = GameController.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        skeletons = GetSkeletons(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }
}
