package yeremiva.gitgud.controller;

import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.objects.*;
import yeremiva.gitgud.view.LevelView;
import yeremiva.gitgud.view.ObjectView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;

public class ObjectController {
    private final static Logger log = Logger.getLogger(ObjectController.class.getName());

    private final GameProcessController gameProcessController;
    private final ObjectView objectView;

    private ArrayList<Gem> gems;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;

    public ObjectController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;

        objectView = new ObjectView();
    }

    /**
     * Update.
     * <p>
     *     Update method of every object if they are active.
     * </p>
     */
    public void update() {
        for (Gem g: gems) {
            if (g.isActive()) {
                g.update();
            }
        }

        for (GameContainer gc: containers) {
            if (gc.isActive()) {
                gc.update();
            }
        }
    }

    /**
     * Draw.
     * <p>
     *     Draw method that call the Object View draw.
     * </p>
     *
     * @param g draw system
     * @param xLvlOffset the level offset
     */
    public void draw(Graphics g, int xLvlOffset) {
        objectView.draw(g, xLvlOffset);
    }

    /**
     * Load Objects.
     * <p>
     *     Method that loads all the objects on a level.
     * </p>
     *
     * @param newLevel the level
     */
    public void loadObjects(LevelView newLevel) {
        gems = new ArrayList<>(newLevel.getGems());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();

        objectView.init(gems, containers, spikes);
    }

    /**
     * Check for spike touching.
     * <p>
     *     Check if player touched the spikes and kills him if he did.
     * </p>
     *
     * @param player the player
     */
    public void checkSpikesTouched(Player player) {
        for (Spike s : spikes) {
            if (s.getHitbox().intersects(player.getHitbox())) {
                player.kill();

                log.info("Player touched the spikes");
            }
        }
    }

    /**
     * Check for touching the objects.
     * <p>
     *     Method that checks if a player touched the objects and applies any effect if he did.
     * </p>
     *
     * @param hitbox te player hitbox
     */
    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Gem g: gems) {
            if (g.isActive()) {
                if (hitbox.intersects(g.getHitbox())) {
                    g.setActive(false);
                    applyEffectToPlayer(g);

                    log.info("Player has touched the gem");
                }
            }
        }
    }

    /**
     * Apply effect to player.
     * <p>
     *     The method applies the corresponding effect of a gem on a player.
     * </p>
     *
     * @param g the gem
     */
    public void applyEffectToPlayer(Gem g) {
        if (g.getObjType() == RED_GEM) {
            gameProcessController.getPlayer().changeHealth(RED_GEM_VALUE);

            log.info("Player was healed by " + RED_GEM_VALUE + " points");
        }
    }

    /**
     * Check for object hit.
     * <p>
     *     Method that checks if any container was destroyed by player.
     * </p>
     *
     * @param attackbox the player attack box
     */
    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (GameContainer gc : containers) {
            if (gc.isActive() && !gc.isDoAnimation()) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setAnimation(true);

                    log.info("Player broke a container");

                    int type = 0;
                    if (gc.getObjType() == BLUE_GEMSTONE) {
                        type = 1;
                    }
                    gems.add(new Gem((int) (gc.getHitbox().x + gc.getHitbox().width / 2),
                            (int)(gc.getHitbox().y - gc.getHitbox().height / 4),
                            type));
                    return;
                }
            }
        }
    }

    /**
     * Reset.
     * <p>
     *     Method that resets all objects on the level.
     * </p>
     */
    public void resetAllObjects() {
        loadObjects(gameProcessController.getLevelController().getCurrentLevel());

        for (Gem g: gems) {
            g.reset();
        }
        for (GameContainer gc : containers) {
            gc.reset();
        }
    }
}
