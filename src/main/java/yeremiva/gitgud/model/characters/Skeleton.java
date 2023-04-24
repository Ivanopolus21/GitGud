package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;

import java.awt.*;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;

public class Skeleton extends Enemy{
    public Skeleton(float x, float y) {
        super(x, y, SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON);
        initHitbox(x, y, (int) (21 * GameController.SCALE), (int) (31 * GameController.SCALE));
    }

}
