package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Character class.
 * <p>
 *     Class that represents any character in the game.
 * </p>
 */
public abstract class Character {
    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float attackBox;

    protected float x, y;
    protected int width, height;
    protected int aniTick, aniIndex;
    protected int state;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected float walkSpeed = 1.0f * GameController.SCALE;

    public Character(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Initializing player hitbox.
     *
     * @param width the hitbox width
     * @param height the hitbox height
     */
    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float( x, y, (int) (width * GameController.SCALE), (int)(height * GameController.SCALE));
    }

    /**
     * Draws player hitbox.
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
     * Draws player attack box.
     *
     * @param g draw system
     * @param xLvlOffset the level offset
     */
    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) (attackBox.x - xLvlOffset), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    /**
     * Gets player hitbox.
     *
     * @return the hitbox
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * Gets player attackbox.
     *
     * @return the attackbox
     */
    public Rectangle2D.Float getAttackBox() {
        return attackBox;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Gets animation index.
     *
     * @return the animation index
     */
    public int getAniIndex() {
        return aniIndex;
    }
}
