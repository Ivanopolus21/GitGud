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
    private static Logger log = Logger.getLogger(EnemyController.class.getName());

    private EnemyView enemyView;
    private GameProcessController gameProcessController;

    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private Skeleton skeleton;

    public EnemyController(GameProcessController gameProcessController){
        this.gameProcessController = gameProcessController;
        this.enemyView = new EnemyView();
    }

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

    public void draw(Graphics g, int xLvlOffset) {
        enemyView.draw(g, skeletons, xLvlOffset);
    }

    public void loadEnemies(LevelView levelView) {
        skeletons = levelView.getSkeletons();
    }

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

    public Skeleton getSkeleton() {
        return skeleton;
    }

    public void resetAllEnemies() {
        for (Skeleton s : skeletons) {
            s.resetEnemy();
        }
    }
}
