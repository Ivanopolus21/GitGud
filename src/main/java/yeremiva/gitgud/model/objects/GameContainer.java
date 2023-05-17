package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;

/**
 * Game Container Class.
 * <p>
 *     Class that represents the game containers, such as gemstones.
 * </p>
 */
public class GameContainer extends GameObject{

    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);

        createHitbox();
    }

    /**
     * Update.
     * <p>
     *     Update of the animations of the game containers.
     * </p>
     */
    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }

    /**
     * Creation of the hitbox of the game containers.
     */
    private void createHitbox() {
        if (objType == RED_GAMESTONE) {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * GameController.SCALE);
            yDrawOffset = (int) (12 * GameController.SCALE);
        } else {
            initHitbox(23, 25);

            xDrawOffset = (int) (8 * GameController.SCALE);
            yDrawOffset = (int) (5 * GameController.SCALE);
        }

        hitbox.y += yDrawOffset + (int) (GameController.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }
}
