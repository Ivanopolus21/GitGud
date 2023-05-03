package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

public class Potion extends GameObject{

    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        initHitbox(7, 14);
        xDrawOffset = (int) (3 * GameController.SCALE);
        yDrawOffset = (int) (2 * GameController.SCALE);
    }

    public void update() {
        updateAnimationTick();
    }
}
