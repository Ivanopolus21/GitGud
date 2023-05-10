package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.characters.Skeleton;
import yeremiva.gitgud.view.LevelView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;

public class EnemyController {
    private Skeleton skeleton;
    private GameProcessController gameProcessController;
    private BufferedImage[][] skeletonArr;
    private ArrayList<Skeleton> skeletons = new ArrayList<>();

    public EnemyController(GameProcessController gameProcessController){
        this.gameProcessController = gameProcessController;
        loadEnemyImgs();
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

    public void draw(Graphics g, int xLvlOffset) {
        drawSkeletons(g, xLvlOffset);
    }

    private void drawSkeletons(Graphics g, int xLvlOffset) {
        int fixSkeletonHitboxHeight = 11;
        for(Skeleton s: skeletons) {
            if (s.isAlive()) {
                g.drawImage(
                        skeletonArr[s.getState()][s.getAniIndex()],
                        (int) s.getHitbox().x - xLvlOffset - SKELETON_DRAWOFFSET_X + s.flipX(),
                        (int) s.getHitbox().y + fixSkeletonHitboxHeight - SKELETON_DRAWOFFSET_Y,
                        SKELETON_WIDTH * s.flipW(),
                        SKELETON_HEIGHT,
                        null);
//                s.drawHitbox(g, xLvlOffset);
//                s.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Skeleton s : skeletons) {
            if (s.isAlive() && s.getCurrentHealth() > 0) {
                if (attackBox.intersects(s.getHitbox())) {
                    s.hurt(10);
                    return;
                }
            }
        }
    }

    private void loadEnemyImgs() {
        skeletonArr = new BufferedImage[5][6];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SKELETON_SPRITE);
        for (int j = 0; j < skeletonArr.length; j++){
            for (int i = 0; i < skeletonArr[j].length; i++) {
                skeletonArr[j][i] = temp.getSubimage(i * SKELETON_WIDTH_DEFAULT, j * SKELETON_HEIGHT_DEFAULT, SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetAllEnemies() {
        for (Skeleton s : skeletons) {
            s.resetEnemy();
        }
    }
}
