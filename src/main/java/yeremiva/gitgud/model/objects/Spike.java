package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

public class Spike extends GameObject{
    public Spike(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int) (GameController.SCALE * 16);
        hitbox.y += yDrawOffset;
    }
}
