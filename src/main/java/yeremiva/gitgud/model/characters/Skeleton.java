package yeremiva.gitgud.model.characters;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;

public class Skeleton extends Enemy{
    public Skeleton(float x, float y) {
        super(x, y, SKELETON_WIDTH, SKELETON_HEIGHT, SKELETON);
    }
}
