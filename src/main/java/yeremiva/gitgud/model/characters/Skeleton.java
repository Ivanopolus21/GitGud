package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;

import java.awt.geom.Rectangle2D;

import static yeremiva.gitgud.core.settings.Constants.Directions.LEFT;
import static yeremiva.gitgud.core.settings.Constants.Directions.RIGHT;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;

/**
 * Class that represents Skeleton enemy.
 */
public class Skeleton extends Enemy {
    private int attackBoxOffsetX;

    public Skeleton(float x, float y, int maxHealth, int currentHealth, float walkSpeed, int enemyDamage) {
        super(x, y, SKELETON_WIDTH, SKELETON_HEIGHT, maxHealth, currentHealth, walkSpeed, enemyDamage, SKELETON);

        initHitbox(16, 25);
        initAttackBox();
    }

    /**
     * Update.
     * <p>
     *     Update of the Skeleton behaviour, attack box and animations.
     * </p>
     *
     * @param lvlData the data of the level
     * @param player the player
     */
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    /**
     * Update of the attack box.
     */
    private void updateAttackBox() {
        if (walkDir == LEFT) {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        } else if (walkDir == RIGHT) {
            attackBox.x = hitbox.x + attackBoxOffsetX;
        }
        attackBox.y = hitbox.y;
    }

    /**
     * Update.
     * <p>
     *     Update of the Skeleton behaviour based on the conditions and enemy states.
     * </p>
     *
     * @param lvlData the data of the level
     * @param player the player
     */
    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch(state) {
                case IDLE:
                    newState(RUNNING);
                    break;

                case RUNNING:
                    if (canSeePlayer(lvlData, player)) {
                        turnTowardsPlayer(player);
                        if (isPlayerCloseForAttack(player)) {
                            newState(ATTACK);
                        }
                    }
                    move(lvlData);
                    break;

                case ATTACK:
                    if (aniIndex == 0) {
                        attackChecked = false;
                    }

                    if (aniIndex == 2 && !attackChecked) {
                        checkIfEnemyHitsPlayer(attackBox, player);
                    }
                    break;

                case HIT:
                    break;
            }
        }
    }

    /**
     * Initializing of the Skeleton attack box.
     */
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (15 * GameController.SCALE), (int) (25 * GameController.SCALE));
        attackBoxOffsetX = (int) (GameController.SCALE * 15);
    }

    /**
     * Flip of the skeleton image by x.
     *
     * @return the value of the flipX
     */
    public int flipX() {
        if (walkDir == LEFT) {
            return width;
        } else {
            return 0;
        }
    }

    /**
     * Flip of the skeleton image by width.
     *
     * @return the value of the flipW
     */
    public int flipW() {
        if (walkDir == LEFT) {
            return -1;
        } else {
            return 1;
        }
    }
}
