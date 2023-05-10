package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.characters.Skeleton;
import yeremiva.gitgud.view.EnemyView;
import yeremiva.gitgud.view.LevelView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;

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

    public void loadEnemies(LevelView levelView) {
        skeletons = levelView.getSkeletons();
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
        }
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

    public void draw(Graphics g, int xLvlOffset) {
        enemyView.draw(g, skeletons, xLvlOffset);
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
