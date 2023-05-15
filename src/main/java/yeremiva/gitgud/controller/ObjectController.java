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

    private GameProcessController gameProcessController;
    private ObjectView objectView;

    private ArrayList<Gem> gems;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;

    public ObjectController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
        this.objectView = new ObjectView(this);
    }

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

    public void draw(Graphics g, int xLvlOffset) {
        objectView.draw(g, xLvlOffset);
    }

    public void loadObjects(LevelView newLevel) {
        gems = new ArrayList<>(newLevel.getGems());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();

        objectView.init(gems, containers, spikes);
    }

    public void checkSpikesTouched(Player player) {
        for (Spike s : spikes) {
            if (s.getHitbox().intersects(player.getHitbox())) {
                player.kill();

                log.info("Player touched the spikes");
            }
        }
    }

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

    public void applyEffectToPlayer(Gem p) {
        if (p.getObjType() == RED_GEM) {
            gameProcessController.getPlayer().changeHealth(RED_GEM_VALUE);

            log.info("Player was healed by " + RED_GEM_VALUE + " points");
        }
    }

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
