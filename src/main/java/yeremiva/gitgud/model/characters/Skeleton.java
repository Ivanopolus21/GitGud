package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;

import java.awt.*;

import static yeremiva.gitgud.core.settings.Constants.Directions.LEFT;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

public class Skeleton extends Enemy{
    public Skeleton(float x, float y) {
        super(x, y, SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON);
        initHitbox(x, y, (int) (21 * GameController.SCALE), (int) (31 * GameController.SCALE));
    }

    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
    }


    private void updateMove(int[][] lvlData) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            updateInAir(lvlData);
        } else {
            switch(enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    move(lvlData);
                    break;
            }
        }
    }

}
