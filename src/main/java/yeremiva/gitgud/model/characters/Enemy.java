package yeremiva.gitgud.model.characters;

import com.sun.xml.internal.bind.v2.model.core.ID;
import yeremiva.gitgud.controller.GameController;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static yeremiva.gitgud.core.settings.Constants.ANI_SPEED;
import static yeremiva.gitgud.core.settings.Constants.Directions.*;
import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.*;
import static yeremiva.gitgud.core.settings.Constants.GRAVITY;
import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.BARREL;
import static yeremiva.gitgud.core.settings.Constants.ObjectConstants.BOX;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

public abstract class Enemy extends Character{
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = GameController.TILES_SIZE;
    protected boolean alive = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = GameController.SCALE * 0.35f;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            inAir = false;
            hitbox.y = GetCharacterYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int) (hitbox.y / GameController.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
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
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitbox().y / GameController.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int fixOfssetLeft = 10;
        int fixOffsetRight = 15;
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x - fixOfssetLeft);
        if (walkDir == RIGHT) {
            absValue = (int) Math.abs(player.hitbox.x - hitbox.x + fixOffsetRight);
        }
        return absValue <= attackDistance;
    }

    protected void newState(int enemyState) {
        this.state = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    public void hurt(int amount) {
        currentHealth -= amount;

        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(HIT);
        }
    }

    protected void checkIfEnemyHitsPlayer(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.changeHealth(-GetEnemyDmg(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED){
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                aniIndex = 0;
                switch(state) {
                    case ATTACK:
                    case HIT:
                        state = IDLE;
                        break;
                    case DEAD:
                        alive = false;
                }
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        alive = true;
        airSpeed = 0;
    }
}

