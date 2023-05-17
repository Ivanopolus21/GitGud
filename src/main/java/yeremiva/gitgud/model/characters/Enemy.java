package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.EnemyType;

import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.ANI_SPEED;
import static yeremiva.gitgud.core.settings.Constants.Directions.*;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;
import static yeremiva.gitgud.core.settings.Constants.GRAVITY;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

/**
 * Enemy class.
 *<p>
 *     Class that represents an enemy.
 *</p>
 */
public abstract class Enemy extends Character{
    private final static Logger log = Logger.getLogger(Enemy.class.getName());

    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = GameController.TILES_SIZE;
    protected boolean alive = true;
    protected boolean attackChecked;
    protected int enemyDamage;

    public Enemy(float x, float y, int width, int height, int maxHealth, int currentHealth, float walkSpeed, int enemyDamage, int enemyType) {
        super(x, y, width, height);
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.walkSpeed = GameController.SCALE * walkSpeed;
        this.enemyDamage = enemyDamage;
        this.enemyType = enemyType;
    }

    /**
     * First update check.
     *
     * @param lvlData the level of the data
     */
    protected void firstUpdateCheck(int[][] lvlData) {
        if (IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    /**
     * Update animation tick.
     * <p>
     *     The method updates an animation ticks base on the provided sprite amount of the enemy state.
     * </p>
     */
    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;
                switch(state) {
                    case ATTACK:
                    case HIT:
                        state = IDLE;
                        break;
                    case DEAD:
                        alive = false;
                        log.info("Enemy " + EnemyType.SKELETON + " was killed");
                }
            }
        }
    }

    /**
     * Updates the enemy in air.
     *
     * @param lvlData the data of the level
     */
    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetCharacterYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / GameController.TILES_SIZE);
        }
    }

    /**
     * Move.
     * <p>
     *     Method that is responsible for enemy movement speed, ability and direction.
     * </p>
     *
     * @param lvlData the data of the level
     */
    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }
        changeWalkDir();
    }

    /**
     * Turn Towards Player.
     * <p>
     *     Method that is responsible for turning the enemy to the direction where a player is.
     * </p>
     *
     * @param player the player
     */
    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    /**
     * Can See Player.
     * <p>
     *     Checks if an enemy can see player.
     * </p>
     *
     * @param lvlData the data of the level
     * @param player the player
     * @return true if enemy can see player and false otherwise
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / GameController.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if player is in range of the enemy.
     *
     * @param player the player
     * @return true if in range, false otherwise
     */
    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    /**
     * Checks if player is in range for an attack.
     *
     * @param player the player
     * @return true if in range, false otherwise
     */
    protected boolean isPlayerCloseForAttack(Player player) {
        int fixOfssetLeft = 10;
        int fixOffsetRight = 15;
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x - fixOfssetLeft);
        if (walkDir == RIGHT) {
            absValue = (int) Math.abs(player.hitbox.x - hitbox.x + fixOffsetRight);
        }
        return absValue <= attackDistance;
    }

    /**
     * Sets state.
     * <p>
     *     Sets new enemy state and resets animation tick and animation index.
     * </p>
     *
     * @param enemyState the enemy state
     */
    protected void newState(int enemyState) {
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    /**
     * Hurt.
     * <p>
     *     Method is responsible for hurting the enemy by player.
     * </p>
     *
     * @param amount the amount of the hurt
     */
    public void hurt(int amount) {
        currentHealth -= amount;

        if (currentHealth <= 0) {
            newState(DEAD);

            log.info("Enemy " + EnemyType.SKELETON + " died!");
        } else {
            newState(HIT);
        }

        log.info("Enemy " + EnemyType.SKELETON + " was hurt by " + amount + "!");
    }

    /**
     * Checks if enemy hits a player.
     *
     * @param attackBox the enemy attack box
     * @param player the player
     */
    protected void checkIfEnemyHitsPlayer(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.changeHealth(-enemyDamage);

            if (player.getCurrentHealth() > 0) {
                log.info(EnemyType.SKELETON + " damaged player by " + enemyDamage + "!");
            }
        }
        attackChecked = true;
    }

    /**
     * Changes walk direction.
     */
    protected void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    /**
     * Gets alive.
     *
     * @return alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Gets current health.
     *
     * @return currentHealth
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Reset.
     * <p>
     *     Completely resets an enemies on the level.
     * </p>
     */
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        alive = true;
        airSpeed = 0;

        log.info("Enemy was reseted");
    }
}

