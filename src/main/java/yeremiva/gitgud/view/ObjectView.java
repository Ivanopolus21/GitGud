package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.ObjectController;
import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.model.objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;
import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.GEM_HEIGHT;

public class ObjectView {
    private final ObjectController objectController;

    private BufferedImage[][] gemImgs, containerImgs;
    private BufferedImage spikeImg;
    private ArrayList<Gem> gems;
    private ArrayList<GameContainer> containers;
    private ArrayList<Spike> spikes;

    public ObjectView(ObjectController objectController) {
        this.objectController = objectController;

        loadImgs();
    }

    public void init(ArrayList<Gem> givenGems, ArrayList<GameContainer> givenContainers, ArrayList<Spike> givenSpikes) {
        gems = givenGems;
        containers = givenContainers;
        spikes = givenSpikes;
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawGems(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : spikes){
            g.drawImage(spikeImg, (int)(s.getHitbox().x - xLvlOffset),
                    (int) (s.getHitbox().y - s.getyDrawOffset()),
                    SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers) {
            if (gc.isActive()) {
                int type = 0;
                if(gc.getObjType() == BLUE_GEMSTONE) {
                    type = 1;
                }
                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                        (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
        }
    }

    private void drawGems(Graphics g, int xLvlOffset) {
        for (Gem gem: gems) {
            if (gem.isActive()) {
                int type = 0;
                if (gem.getObjType() == RED_GEM) {
                    type = 1;
                }
                g.drawImage(gemImgs[type][gem.getAniIndex()],
                        (int) (gem.getHitbox().x - gem.getxDrawOffset() - xLvlOffset),
                        (int) (gem.getHitbox().y - gem.getyDrawOffset()),
                        GEM_WIDTH,
                        GEM_HEIGHT,
                        null);
            }
        }
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.GEM_ATLAS);
        gemImgs = new BufferedImage[2][7];

        for (int j = 0; j < gemImgs.length; j++) {
            for (int i = 0; i < gemImgs[j].length; i++) {
                gemImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++) {
            for (int i = 0; i < containerImgs[j].length; i++) {
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
            }
        }
        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
    }
}
