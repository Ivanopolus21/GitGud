package yeremiva.gitgud.model.objects;

import yeremiva.gitgud.controller.GameController;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static yeremiva.gitgud.core.settings.Constants.ANI_SPEED;
import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.*;

public class GameObject {
    protected Rectangle2D.Float hitbox;

    protected int x,y, objType;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objType)) {
                aniIndex = 0;
                if (objType == BLUE_GEMSTONE || objType == RED_GAMESTONE) {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    protected void initHitbox(int width, int height) {
        hitbox = new Rectangle2D.Float( x, y, (int) (width * GameController.SCALE), (int)(height * GameController.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlOffset) {
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAnimation(boolean doAnimation) {
        this.doAnimation = doAnimation;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public int getObjType() {
        return objType;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isDoAnimation() {
        return doAnimation;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    public int getyDrawOffset() {
        return yDrawOffset;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if (objType == BLUE_GEMSTONE || objType == RED_GAMESTONE) {
            doAnimation = false;
        } else {
            doAnimation = true;
        }
    }
}
