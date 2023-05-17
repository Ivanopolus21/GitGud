package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

/**
 * Gem class.
 * <p>
 *     Class that represents the gems objects in the game.
 * </p>
 */
public class Gem extends GameObject{
    private float hoverOffset;
    private final int maxHoverOffset;
    private int hoverDir = 1;

    public Gem(int x, int y, int objType) {
        super(x, y, objType);

        initHitbox(7, 14);

        doAnimation = true;
        xDrawOffset = (int) (3 * GameController.SCALE);
        yDrawOffset = (int) (2 * GameController.SCALE);
        maxHoverOffset = (int) (10 * GameController.SCALE);
    }

    /**
     * Update.
     * <p>
     *     Update of the gem animations.
     * </p>
     */
    public void update() {
        updateAnimationTick();
        updateHover();
    }

    /**
     * Update of the gem hover animation.
     */
    private void updateHover() {
        hoverOffset += (0.05f * GameController.SCALE * hoverDir);
        if (hoverOffset >= maxHoverOffset) {
            hoverDir = -1;
        } else if (hoverOffset < 0) {
            hoverDir = 1;
        }
        hitbox.y = y + hoverOffset;
    }
}
