package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static yeremiva.gitgud.core.settings.Constants.ANI_SPEED;
import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;

/**
 * Class of the game object.
 */
public class GameObject {
    protected Rectangle2D.Float hitbox;

    protected int x,y, objType;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    /**
     * Update.
     * <p>
     *     Update of the animaton tick of the game objects.
     * </p>
     */
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                if (objType == BLUE_GEMSTONE || objType == RED_GAMESTONE) {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    /**
     * Initializes game object hitbox.
     *
     * @param width the width of the game object hitbox
     * @param height the height of the game object hitbox
     */
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float( x, y, (int) (width * GameController.SCALE), (int)(height * GameController.SCALE));
    }

    /**
     * Draws object hitbox.
     *
     * @param g draw system
     * @param xLvlOffset the level offset
     */
    public void drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * Sets the object active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Sets the boolean of doing the animation.
     *
     * @param doAnimation the boolean
     */
    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    /**
     * Gets object hitbox.
     *
     * @return hitbox
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * Gets object type.
     *
     * @return the object type
     */
    public int getObjType() {
        return objType;
    }

    /**
     * Gets the active boolean.
     *
     * @return the active boolean.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the boolean of doing the animations.
     *
     * @return
     */
    public boolean isDoAnimation() {
        return doAnimation;
    }

    /**
     * Gets x draw offset.
     *
     * @return the draw offset.
     */
    public int getxDrawOffset() {
        return xDrawOffset;
    }

    /**
     * Gets y draw offset.
     * @return the draw offset
     */
    public int getyDrawOffset() {
        return yDrawOffset;
    }

    /**
     * Gets the animation index.
     *
     * @return the animation index
     */
    public int getAniIndex() {
        return aniIndex;
    }

    /**
     * Reset.
     * <p>
     *     Resets the game object variables.
     * </p>
     */
    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if (objType == BLUE_GEMSTONE || objType == RED_GAMESTONE) {
            doAnimation = false;
        } else {
            doAnimation = true;
        }
    }
}
