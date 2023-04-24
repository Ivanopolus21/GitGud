package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;

import java.awt.*;

import static yeremiva.gitgud.core.settings.Constants.Directions.*;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

public abstract class Enemy extends Character{
    private int aniIndex, enemyState, enemyType;
    private int aniTick, aniSpeed = 25;
    private boolean firstUpdate = true;
    private boolean inAir;
    private float fallSpeed;
    private float gravity = 0.04f * GameController.SCALE;
    private float walkSpeed = 0.5f * GameController.SCALE;
    private int walkDir = LEFT;
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void drawHitbox(Graphics g, int xLvlOffset){
        // For debugging the hitbox
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;
            }
        }
    }

    public void update(int[][] lvlData) {
        updateMove(lvlData);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData) {
        if (firstUpdate) {
            if (!IsCharacterOnFloor(hitbox, lvlData)) {
                inAir = true;
            }
            firstUpdate = false;
        }
        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            } else {
                inAir = false;
                hitbox.y = GetCharacterYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else {
            switch(enemyState) {
                case IDLE:
                    enemyState = RUNNING;
                    break;
                case RUNNING:
                    float xSpeed = 0;

                    if (walkDir == LEFT) {
                        xSpeed = -walkSpeed;
                    } else {
                        xSpeed = walkSpeed;
                    }
                    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
                        if (IsFloor(hitbox, xSpeed, lvlData)) {
                            hitbox.x += xSpeed;
                            return;
                        }
                    }
                    changeWalkDir();
                    break;
            }
        }
    }

    private void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }
}
