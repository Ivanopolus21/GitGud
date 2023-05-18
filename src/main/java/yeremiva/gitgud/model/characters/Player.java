package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.ANI_SPEED;
import static yeremiva.gitgud.core.settings.Constants.GRAVITY;
import static yeremiva.gitgud.core.settings.Constants.PlayerConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

/**
 * Player class.
 * <p>
 *     The class that represents the player of the game.
 * </p>
 */
public class Player extends Character {
    //30 height, 18 width 00 -- 6, 3
    //43 height, 25 width 00 -- 10, 5

    private final static Logger log = Logger.getLogger(Player.class.getName());

    private final GameProcessController gameProcessController;

    private BufferedImage[][] animations;
    private BufferedImage statusBarImg;

    private boolean moving = false, attacking = false;
    private boolean isDead;
    private boolean left, right, jump;
    private int[][] lvlData;

    private final int healthBarWidth = (int) (150 * GameController.SCALE);
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

    /**
     * Update.
     * <p>
     *     Update of the player, his health bar, state, attack box and animations.
     * </p>
     */
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
            checkGemTouched();
            checkSpikesTouched();
        }

        if (attacking) {
            checkAttack();
        }

        updateAnimationTick();
        setAnimation();
    }

    /**
     * Update of the player health bar.
     */
    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float)(maxHealth)) * healthBarWidth);
    }

    /**
     * Update of the player attack box.
     */
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

    /**
     * Update of the player position.
     */
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
            if (IsCharacterOnFloor(hitbox, lvlData)) {
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
                    float fallSpeedAfterCollision = 0.5f * GameController.SCALE;
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    /**
     * Update of the player animation tick.
     */
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

    /**
     * Render.
     * <p>
     *     Render of the player sprites and health bar.
     * </p>
     *
     * @param g draw system
     * @param lvlOffset the level offset
     */
    public void render(Graphics g, int lvlOffset) {
        float xDrawOffset = 6 * GameController.SCALE;
        float yDrawOffset = 4 * GameController.SCALE;
        g.drawImage(animations[state][aniIndex],
                (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
                (int) (hitbox.y - yDrawOffset),
                width * flipW, height, null);
//        drawHitbox(g, lvlOffset);
//        drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    /**
     * Draw of the health bar.
     *
     * @param g draw system
     */
    private void drawUI(Graphics g) {
        //StatusBar View
        int statusBarWidth = (int) (192 * GameController.SCALE);
        int statusBarHeight = (int) (58 * GameController.SCALE);
        int statusBarX = (int) (10 * GameController.SCALE);
        int statusBarY = (int) (10 * GameController.SCALE);

        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);

        int healthBarHeight = (int) (4 * GameController.SCALE);
        int healthBarXStart = (int) (34 * GameController.SCALE);
        int healthBarYStart = (int) (14 * GameController.SCALE);

        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    /**
     * Load Player Animations.
     */
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

    /**
     * Load level data.
     *
     * @param lvlData the data of the level
     */
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    /**
     * Initialize of the player attack box.
     */
    public void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (25 * GameController.SCALE), (int) (35 * GameController.SCALE));
//        resetAttackBox();
    }

    /**
     * Check if spike was touched by the player.
     */
    private void checkSpikesTouched() {
        gameProcessController.checkSpikesTouched(this);
    }

    /**
     * Check if gem was touched by the player.
     */
    private void checkGemTouched() {
        gameProcessController.checkGemTouched(hitbox);
    }

    /**
     * Check attack functions.
     */
    private void checkAttack() {
        if (attackChecked || aniIndex != 4) {
            return;
        }
        attackChecked = true;
        gameProcessController.checkIfPlayerHitsEnemy(attackBox, playerDamage);
        gameProcessController.checkObjectHit(attackBox);
    }

    /**
     * Set player spawn point.
     *
     * @param spawn the spawn point
     */
    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;

        hitbox.x = x;
        hitbox.y = y;
    }

    /**
     * Set Animations.
     * <p>
     *     Set an animations if the player based on his state.
     * </p>
     */
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

    /**
     * Player jump.
     */
    private void jump() {
        if (inAir){
            return;
        }
        inAir = true;

        // Jumping/Gravity
        float jumpSpeed = -2.25f * GameController.SCALE;
        airSpeed = jumpSpeed;
    }

    /**
     * Uodate of the X player position.
     *
     * @param xSpeed the x speed
     */
    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y , hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetCharacterXPosNextToWall(hitbox, xSpeed);
        }
    }

    /**
     * Change of the player health.
     *
     * @param value the value
     */
    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0) {
            kill();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
        if (currentHealth > 0) {
            log.info("Health of the player was changed by " + value + " points!");
        }
    }

    /**
     * Kills the player.
     */
    public void kill() {
        currentHealth = 0;
        isDead = true;
        log.info("Player has died!");
    }

    /**
     * Sets an attacking boolean.
     *
     * @param attacking the attacking
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * Sets left direction of the player view.
     *
     * @param left the left direction
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Sets right direction of the player view.
     *
     * @param right the right direction
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Sets jumping state to the player.
     *
     * @param jump the jump state
     */
    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * Gets current player health.
     *
     * @return the currentHealth
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public boolean isDead() {
        return isDead;
    }

    /**
     * Reset.
     * <p>
     *     Resets all the player variables.
     * </p>
     */
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

        if (IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }

        log.info("Player was reseted");
    }

    /**
     * Resets player attack box.
     */
    private void resetAttackBox() {
        if (flipW == 1) {
            attackBox.x = hitbox.x + hitbox.width + (int) (1 * GameController.SCALE);
        } else {
            attackBox.x = hitbox.x - hitbox.width - (int) (10 * GameController.SCALE);
        }
    }

    /**
     * Resets player animation tick.
     */
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    /**
     * Resets direction booleans.
     */
    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    /**
     * Resets player in air state and airSpeed.
     */
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }
}