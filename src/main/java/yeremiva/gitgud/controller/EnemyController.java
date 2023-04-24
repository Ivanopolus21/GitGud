package yeremiva.gitgud.controller;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.model.characters.Enemy;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.characters.Skeleton;

import java.awt.*;
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
        addEnemies();
    }

    private void addEnemies() {
        skeletons = LoadSave.GetSkeletons();
        System.out.println("size of skeletons: " + skeletons.size());
    }

    public void update(int[][] lvlData){
        for (Skeleton s: skeletons) {
            s.update(lvlData);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawSkeletons(g, xLvlOffset);
    }

    private void drawSkeletons(Graphics g, int xLvlOffset) {
        for(Skeleton s: skeletons) {
            g.drawImage(skeletonArr[s.getEnemyState()][s.getAniIndex()], (int) s.getHitbox().x - xLvlOffset, (int) s.getHitbox().y - 48, SKELETON_WIDTH, SKELETON_HEIGHT, null);
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
