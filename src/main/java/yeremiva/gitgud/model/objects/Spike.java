package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

/**
 * Spike class.
 * <p>
 *     Class that represents the spike objects in the game.
 * </p>
 */
public class Spike extends GameObject {

    public Spike(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(32, 16);

        xDrawOffset = 0;
        yDrawOffset = (int) (GameController.SCALE * 16);
        hitbox.y += yDrawOffset;
    }
}
