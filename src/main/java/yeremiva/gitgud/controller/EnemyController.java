package yeremiva.gitgud.controller;

import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.characters.Skeleton;
import yeremiva.gitgud.view.EnemyView;
import yeremiva.gitgud.view.LevelView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Logger;

public class EnemyController {
    private static final Logger log = Logger.getLogger(EnemyController.class.getName());

    private final EnemyView enemyView;
    private final GameProcessController gameProcessController;

    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private Skeleton skeleton;

    public EnemyController(GameProcessController gameProcessController){
        this.gameProcessController = gameProcessController;

        enemyView = new EnemyView();
    }

    /**
     * Update.
     * <p>
     * Method that updates all enemies if they are alive.
     *
     * @param lvlData data of the specific level
     * @param player player
     */
    public void update(int[][] lvlData, Player player){
        boolean isAnyAlive = false;
        for (Skeleton s: skeletons) {
            if (s.isAlive()) {
                s.update(lvlData, player);
                isAnyAlive = true;
            }
        }
        if (!isAnyAlive) {
            gameProcessController.setLevelCompleted(true);

            log.info("Every single enemy are dead!");
        }
    }

    /**
     * Draw.
     * <p>
     * Draw method that calls Enemy View draw method.
     *
     * @param g draw system
     * @param xLvlOffset offset of the level
     */
    public void draw(Graphics g, int xLvlOffset) {
        enemyView.draw(g, skeletons, xLvlOffset);
    }

    /**
     * Method that load enemies on the level.
     *
     * @param levelView view of the level
     */
    public void loadEnemies(LevelView levelView) {
        skeletons = levelView.getSkeletons();
    }

    /**
     * Hit check.
     * <p>
     * Checks if an enemy got a hit from the player.
     *
     * @param attackBox player attack box
     * @param playerDamage player damage
     */
    public void checkEnemyHit(Rectangle2D.Float attackBox, int playerDamage) {
        for (Skeleton s : skeletons) {
            if (s.isAlive() && s.getCurrentHealth() > 0) {
                if (attackBox.intersects(s.getHitbox())) {
                    s.hurt(playerDamage);
                    return;
                }
            }
        }
    }

    /**
     * Gets Skeleton.
     *
     * @return the Skeleton
     */
    public Skeleton getSkeleton() {
        return skeleton;
    }

    /**
     * Reset.
     * <p>
     * Resets each of the enemy on the level.
     */
    public void resetAllEnemies() {
        for (Skeleton s : skeletons) {
            s.resetEnemy();
        }
    }
}
