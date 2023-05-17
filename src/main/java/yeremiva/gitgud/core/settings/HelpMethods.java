package yeremiva.gitgud.core.settings;

import yeremiva.gitgud.controller.GameController;

import java.awt.geom.Rectangle2D;

/**
 * Help Methods.
 * <p>
 *     The class has different methods that have different usages among all the classes.
 * </p>
 */
public class HelpMethods {

    /**
     * Can Move Here.
     * <p>
     *     Method that checks if player or enemy can move on a tile.
     * </p>
     *
     * @param x the x
     * @param y the y
     * @param width the width of the object
     * @param height the height of the object
     * @param lvlData the data of the level
     * @return true if the character can move or false otherwise
     */
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

    /**
     * Is Solid.
     * <p>
     *     Checks if the position is solid or not.
     * </p>
     *
     * @param x the x
     * @param y the y
     * @param lvlData the data of the level
     * @return true if position is solid, false otherwise
     */
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

    /**
     * Is Tile Solid.
     * <p>
     *     Checks if the tile is solid or not.
     * </p>
     *
     * @param xTile the x
     * @param yTile the y
     * @param lvlData the data of the level
     * @return true if tile is solid, false otherwise
     */
    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[ yTile][ xTile];
        if (value >= 48 || value < 0 || value != 11){
            return true;
        }
        return false;
    }

    /**
     * Get Character Position Next To Wall.
     * <p>
     *     The method gets character position (by x) and preventing lags if the player is near the wall and make it
     *     better to view.
     * </p>
     *
     * @param hitbox the hitbox of the player
     * @param xSpeed the x speed
     * @return characters' position if he is near to wall
     */
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

    /**
     * Get Character Position Under Roof or Above Floor.
     * <p>
     *     Method gets character position if he is falling or not.
     * </p>
     *
     * @param hitbox the hitbox of the player
     * @param airSpeed the player speed in the air
     * @return characters' position if he is in the air
     */
    public static float GetCharacterYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / GameController.TILES_SIZE);
        if (airSpeed > 0) {
            //Falling - touching floor
            int tileYPos = currentTile * GameController.TILES_SIZE;
            int yOffset = (int) (GameController.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * GameController.TILES_SIZE;
        }
    }

    /**
     * Is Character on Floor.
     * <p>
     *     The method checks if character on the floor or not.
     * </p>
     *
     * @param hitbox the hitbox of the player
     * @param lvlData the data of the level
     * @return true if character is on the floor and false otherwise
     */
    public static boolean IsCharacterOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        //Check the pixel below bottomleft and bottomright
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is Floor.
     * <p>
     *     Checks if a tile is solid floor or not.
     * </p>
     *
     * @param hitbox the hitbox of the player
     * @param xSpeed the x speed
     * @param lvlData the data of the level
     * @return true if it is floor and false otherwise
     */
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) {
            return IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData);
        } else {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        }
    }

    /**
     * Is All Tiles Walkable.
     * <p>
     *     Checks if all the tiles is walkable or is there some objects on the way, from a start and to an end.
     * </p>
     *
     * @param xStart the x start
     * @param xEnd the x end
     * @param y the y
     * @param lvlData the data of the level
     * @return true if all tiles is walkable and false otherwise.
     */
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

    /**
     * Is Sight Clear.
     * <p>
     *     Checks if sight of the character is clear and there no obstacles.
     * </p>
     *
     * @param lvlData the data of the level
     * @param firstHitbox the first character hitbox
     * @param secondHitbox the second character hitbox
     * @param yTile the y
     * @return true if sight is clear, false otherwise
     */
    public static boolean IsSightClear( int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int)(firstHitbox.x / GameController.TILES_SIZE);
        int secondXTile = (int)(secondHitbox.x / GameController.TILES_SIZE);

        if(firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }
}
