package yeremiva.gitgud.view;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.model.characters.Skeleton;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.SKELETON_HEIGHT;

public class EnemyView {

    private BufferedImage[][] skeletonArr;

    public EnemyView() {
        loadEnemyImgs();
    }

    public void draw(Graphics g, ArrayList<Skeleton> skeletons, int xLvlOffset) {
        drawSkeletons(g, skeletons, xLvlOffset);
    }

    private void drawSkeletons(Graphics g, ArrayList<Skeleton> skeletons, int xLvlOffset) {
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

    private void loadEnemyImgs() {
        skeletonArr = new BufferedImage[5][6];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SKELETON_SPRITE);
        for (int j = 0; j < skeletonArr.length; j++){
            for (int i = 0; i < skeletonArr[j].length; i++) {
                skeletonArr[j][i] = temp.getSubimage(i * SKELETON_WIDTH_DEFAULT, j * SKELETON_HEIGHT_DEFAULT, SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
            }
        }
    }
}
