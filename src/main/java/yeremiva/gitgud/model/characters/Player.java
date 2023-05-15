package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.ANI_SPEED;
import static yeremiva.gitgud.core.settings.Constants.GRAVITY;
import static yeremiva.gitgud.core.settings.Constants.PlayerConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

public class Player extends Character {
    //30 height, 18 width 00 -- 6, 3
    //43 height, 25 width 00 -- 10, 5

    private final GameProcessController gameProcessController;

    private BufferedImage[][] animations;
    private BufferedImage statusBarImg;

    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] lvlData;
    private final float xDrawOffset = 6 * GameController.SCALE;
    private final float yDrawOffset = 4 * GameController.SCALE;

    // Jumping/Gravity
    private final float jumpSpeed = -2.25f * GameController.SCALE;
    private final float fallSpeedAfterCollision = 0.5f * GameController.SCALE;

    //StatusBar View
    private final int statusBarWidth = (int) (192 * GameController.SCALE);
    private final int statusBarHeight = (int) (58 * GameController.SCALE);
    private final int statusBarX = (int) (10 * GameController.SCALE);
    private final int statusBarY = (int) (10 * GameController.SCALE);

    private final int healthBarWidth = (int) (150 * GameController.SCALE);
    private final int healthBarHeight = (int) (4 * GameController.SCALE);
    private final int healthBarXStart = (int) (34 * GameController.SCALE);
    private final int healthBarYStart = (int) (14 * GameController.SCALE);
    private int healthWidth = healthBarWidth;

    //constants
    private final int playerDamage;
    private final int maxHealth;
    private int currentHealth;
    private final float walkSpeed;

    //Attackbox
    private int flipX = 0;
    private int flipW = 1;
    private boolean attackChecked = false;

    public Player(float x, float y, int width, int height, int maxHealth, int currentHealth, float walkSpeed, int playerDamage, GameProcessController gameProcessController) {
        super(x, y, width, height);
        this.gameProcessController = gameProcessController;
        this.state = IDLE;
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.walkSpeed = walkSpeed * GameController.SCALE;
        this.playerDamage = playerDamage;

        loadAnimations();
        initHitbox(17, 27);
        initAttackBox();
    }

    public void update() {
        updateHealthBar();
        if (currentHealth <= 0) {
            if (state != DEAD) {
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                gameProcessController.setPlayerDying(true);
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) {
                gameProcessController.setGameOver(true);
            } else {
                updateAnimationTick();
            }
            return;
        }
        updateAttackBox();
        updatePosition();

        if (moving) {
            checkPotionTouched();
            checkSpikesTouched();
        }

        if (attacking) {
            checkAttack();
        }

        updateAnimationTick();
        setAnimation();
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float)(maxHealth)) * healthBarWidth);
    }

    private void updateAttackBox() {
        if (right && left) {
            if (flipW == 1) {
                attackBox.x = hitbox.x + hitbox.width + (int) (1 * GameController.SCALE);
            } else {
                attackBox.x = hitbox.x - hitbox.width - (int) (10 * GameController.SCALE);
            }
        } else if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (1 * GameController.SCALE);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (10 * GameController.SCALE);
        }
        attackBox.y = hitbox.y - (4 * GameController.SCALE);
    }

    public void updatePosition() {
        moving = false;

        if (jump) {
            jump();
        }

        if (!inAir) {
            if ((!left && !right) || (right && left)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }

        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir) {
            if (!IsCharacterOnFloor(hitbox, lvlData)) {
                inAir = true;
            }
        }

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetCharacterYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    private void updateAnimationTick() {
        aniTick++;

        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[state][aniIndex],
                (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                width * flipW, height, null);
//        drawHitbox(g, lvlOffset);
//        drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[7][8];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 32, j*32, 32, 32);
            }
        }

        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (25 * GameController.SCALE), (int) (35 * GameController.SCALE));
//        resetAttackBox();
    }

    private void checkSpikesTouched() {
        gameProcessController.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        gameProcessController.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 4) {
            return;
        }
        attackChecked = true;
        gameProcessController.checkIfPlayerHitsEnemy(attackBox, playerDamage);
        gameProcessController.checkObjectHit(attackBox);
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;

        hitbox.x = x;
        hitbox.y = y;
    }

    public void setAnimation(){
        int startAni = state;

        if (moving) {
            state = RUNNING;
        } else {
            state = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                state = JUMPING;
            } else {
                state = FALLING;
            }
        }

        if (attacking){
            state = ATTACKING;
            if (startAni != ATTACKING) {
                aniIndex = 4;
                aniTick = 0;
                return;
            }
        }

        if (startAni != state){
            resetAniTick();
        }
    }

    private void jump() {
        if (inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetCharacterXPosNextToWall(hitbox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0) {
            currentHealth = 0;
            //gameOver();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void kill() {
        currentHealth = 0;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        airSpeed = 0f;
        state = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        resetAttackBox();

        if (!IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    private void resetAttackBox() {
        if (flipW == 1) {
            attackBox.x = hitbox.x + hitbox.width + (int) (1 * GameController.SCALE);
        } else {
            attackBox.x = hitbox.x - hitbox.width - (int) (10 * GameController.SCALE);
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }
}