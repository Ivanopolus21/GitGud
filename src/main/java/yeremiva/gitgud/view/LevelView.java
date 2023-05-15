package yeremiva.gitgud.view;

import org.json.JSONObject;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.EnemyConfig;
import yeremiva.gitgud.model.characters.Skeleton;
import yeremiva.gitgud.model.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;

public class LevelView {
    private static final Logger log = Logger.getLogger(LevelView.class.getName());

    private final BufferedImage img;
    private final ArrayList<Skeleton> skeletons = new ArrayList<>();
    private final ArrayList<Gem> gems = new ArrayList<>();
    private final ArrayList<Spike> spikes = new ArrayList<>();
    private final ArrayList<GameContainer> containers = new ArrayList<>();
    private Point playerSpawn;

    private final int[][] lvlData;
    private int lvlTilesWide, maxTilesOffset;
    private int maxLvlOffsetX;

    public LevelView(BufferedImage img) {
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

        log.info("Level was loaded");
    }

    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50) {
            lvlData[y][x] = 0;
        } else {
            lvlData[y][x] = redValue;
        }
    }

    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_GEM:
            case BLUE_GEM:
                gems.add(new Gem(x * GameController.TILES_SIZE, y * GameController.TILES_SIZE, blueValue));
                break;
            case BLUE_GEMSTONE:
            case RED_GAMESTONE:
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

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Skeleton> getSkeletons() {
        return skeletons;
    }

    public ArrayList<Gem> getGems() {
        return gems;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLvlData() {
        return lvlData;
    }

    public int getMaxLvlOffsetX() {
        return maxLvlOffsetX;
    }
}
