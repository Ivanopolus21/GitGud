package yeremiva.gitgud.controller;

import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.objects.*;
import yeremiva.gitgud.view.LevelView;
import yeremiva.gitgud.view.ObjectView;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;

public class ObjectController {
    private GameProcessController gameProcessController;
    private ObjectView objectView;

    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;

    public ObjectController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
        this.objectView = new ObjectView(this);
    }

    public void update() {
        for (Potion p: potions) {
            if (p.isActive()) {
                p.update();
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
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        spikes = newLevel.getSpikes();

        objectView.init(potions, containers, spikes);
    }

    public void checkSpikesTouched(Player player) {
        for (Spike s : spikes) {
            if (s.getHitbox().intersects(player.getHitbox())) {
                player.kill();
            }
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p: potions) {
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
        }
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjType() == RED_POTION) {
            gameProcessController.getPlayer().changeHealth(RED_POTION_VALUE);
        }
    }

    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (GameContainer gc : containers) {
            if (gc.isActive() && !gc.isDoAnimation()) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setAnimation(true);
                    int type = 0;
                    if (gc.getObjType() == BARREL) {
                        type = 1;
                    }
                    potions.add(new Potion((int) (gc.getHitbox().x + gc.getHitbox().width / 2),
                            (int)(gc.getHitbox().y - gc.getHitbox().height / 4),
                            type));
                    return;
                }
            }
        }
    }

    public void resetAllObjects() {
        loadObjects(gameProcessController.getLevelController().getCurrentLevel());

        for (Potion p: potions) {
            p.reset();
        }
        for (GameContainer gc : containers) {
            gc.reset();
        }
    }
}
