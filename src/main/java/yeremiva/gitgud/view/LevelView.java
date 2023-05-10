package yeremiva.gitgud.view;

import org.json.JSONObject;
import sun.rmi.runtime.Log;
import yeremiva.gitgud.controller.EnemyController;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.EnemyConfig;
import yeremiva.gitgud.core.settings.HelpMethods;
import yeremiva.gitgud.core.settings.PlayerConfig;
import yeremiva.gitgud.model.characters.Skeleton;
import yeremiva.gitgud.model.objects.GameContainer;
import yeremiva.gitgud.model.objects.Potion;
import yeremiva.gitgud.model.objects.Spike;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

//LEVEL
public class LevelView {

    private static Logger log = Logger.getLogger(LevelView.class.getName());

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<GameContainer> containers = new ArrayList<>();
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    private EnemyConfig enemyConfig;

    public LevelView(BufferedImage img){
        this.img = img;
        lvlData = new int[img.getHeight()][img.getWidth()];

        loadLevel();
        calculateLevelOffsets();
    }

    private void loadLevel() {

        // Looping through the image colors just once. Instead of one per
        // object/enemy/etc..

        for (int y = 0; y < img.getHeight(); y++)
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }
    }

    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50)
            lvlData[y][x] = 0;
        else
            lvlData[y][x] = redValue;
    }

    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION:
            case BLUE_POTION:
                potions.add(new Potion(x * GameController.TILES_SIZE, y * GameController.TILES_SIZE, blueValue));
                break;
            case BARREL:
            case BOX:
                containers.add(new GameContainer(x * GameController.TILES_SIZE, y * GameController.TILES_SIZE, blueValue));
                break;
            case SPIKE:
                spikes.add(new Spike(x * GameController.TILES_SIZE, y * GameController.TILES_SIZE, SPIKE));
                break;
        }
    }

    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case SKELETON:
                JSONObject enemyConfig = EnemyConfig.getEnemyConfig(false);
                skeletons.add(new Skeleton(x * GameController.TILES_SIZE, y * GameController.TILES_SIZE,
                        enemyConfig.getInt("maxHealth"),
                        enemyConfig.getInt("currentHealth"),
                        enemyConfig.getFloat("walkSpeed"),
                        enemyConfig.getInt("damage")
                ));

                log.info("Enemies were loaded");
                break;
            case 100:
                playerSpawn = new Point(x * GameController.TILES_SIZE, y * GameController.TILES_SIZE);
                break;
        }
    }

    private void calculateLevelOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - GameController.TILES_IN_WIDTH;
        maxLvlOffsetX = GameController.TILES_SIZE * maxTilesOffset;
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

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
