package yeremiva.gitgud.core.settings;

import sun.security.provider.ConfigFile;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.model.characters.Skeleton;
import yeremiva.gitgud.model.objects.GameContainer;
import yeremiva.gitgud.model.objects.Potion;
import yeremiva.gitgud.model.objects.Spike;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.SKELETON;
import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;

public class HelpMethods {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)){
                if (!IsSolid(x + width, y, lvlData)){
                    if (!IsSolid(x, y + height, lvlData)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][]lvlData) {
        int maxWidth = lvlData[0].length * GameController.TILES_SIZE;
        if ( x < 0 || x >= maxWidth){
            return true;
        }
        if (y < 0 || y >= GameController.GAME_HEIGHT){
            return true;
        }

        float xIndex = x / GameController.TILES_SIZE;
        float yIndex = y / GameController.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[ yTile][ xTile];
        if (value >= 48 || value < 0 || value != 11){
            return true;
        }
        return false;
    }

    public static float GetCharacterXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / GameController.TILES_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPosition = currentTile * GameController.TILES_SIZE;
            int xOffset = (int) (GameController.TILES_SIZE - hitbox.width);
            return tileXPosition + xOffset - 1;
        } else {
            //Left
            return currentTile * GameController.TILES_SIZE;
        }
    }

    public static float GetCharacterYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
        int currentTile = (int) (hitbox.y / GameController.TILES_SIZE);
        if (airSpeed > 0) {
            //Falling - touching floor
            int tileYPos = currentTile * GameController.TILES_SIZE;
            int yOffset = (int) (GameController.TILES_SIZE - hitbox.height);
//            int yOffset = (int) ((21 * GameController.SCALE) + 1);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * GameController.TILES_SIZE;
        }
    }

    public static boolean IsCharacterOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        //Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) {
            return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
        } else {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        }
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData)) {
                return false;
            }
            //check one under
            if (!IsTileSolid(xStart + i, y + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsSightClear( int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int)(firstHitbox.x / GameController.TILES_SIZE);
        int secondXTile = (int)(secondHitbox.x / GameController.TILES_SIZE);

        if(firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }

    public static int[][] GetLevelData(BufferedImage img){
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

    public static ArrayList<Skeleton> GetSkeletons(BufferedImage img) {
        ArrayList<Skeleton> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == SKELETON) {
                    list.add(new Skeleton(i * GameController.TILES_SIZE, j * GameController.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage img) {
        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100) {
                    return new Point(i * GameController.TILES_SIZE, j * GameController.TILES_SIZE);
                }
            }
        }
        return new Point(1 * GameController.TILES_SIZE, 1 * GameController.TILES_SIZE) ;
    }

    public static ArrayList<Potion> GetPotions(BufferedImage img) {
        ArrayList<Potion> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == RED_POTION || value == BLUE_POTION) {
                    list.add(new Potion(i * GameController.TILES_SIZE, j * GameController.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static ArrayList<GameContainer> GetContainers(BufferedImage img) {
        ArrayList<GameContainer> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == BARREL || value == BOX) {
                    list.add(new GameContainer(i * GameController.TILES_SIZE, j * GameController.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage img) {
        ArrayList<Spike> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getBlue();
                if (value == SPIKE) {
                    list.add(new Spike(i * GameController.TILES_SIZE, j * GameController.TILES_SIZE, SPIKE));
                }
            }
        }
        return list;
    }
}
