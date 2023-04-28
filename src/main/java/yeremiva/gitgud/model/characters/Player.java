package yeremiva.gitgud.model.characters;

import com.sun.imageio.spi.RAFImageInputStreamSpi;
import yeremiva.gitgud.Game;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.core.settings.HelpMethods;
import yeremiva.gitgud.core.settings.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static yeremiva.gitgud.core.settings.Constants.PlayerConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

public class Player extends Character{
    //30 height, 18 width 00 -- 6, 3
    //43 height, 25 width 00 -- 10, 5

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private float playerSpeed = 1.0f * GameController.SCALE;
    private int[][] lvlData;
    private float xDrawOffset = 6 * GameController.SCALE;
    private float yDrawOffset = 4 * GameController.SCALE;

    // Jumping/Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * GameController.SCALE;
    private float jumpSpeed = -2.25f * GameController.SCALE;
    private float fallSpeedAfterCollision = 0.5f * GameController.SCALE;
    private boolean inAir = false;

    //StatusBar View
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int) (192 * GameController.SCALE);
    private int statusBarHeight = (int) (58 * GameController.SCALE);
    private int statusBarX = (int) (10 * GameController.SCALE);
    private int statusBarY = (int) (10 * GameController.SCALE);

    private int healthBarWidth = (int) (150 * GameController.SCALE);
    private int healthBarHeight = (int) (4 * GameController.SCALE);
    private int healthBarXStart = (int) (34 * GameController.SCALE);
    private int healthBarYStart = (int) (14 * GameController.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //Attackbox
    private Rectangle2D.Float attackBox;
    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked = false;
    private GameProcessController gameProcessController;

    public Player(float x, float y, int width, int height, GameProcessController gameProcessController) {
        super(x, y, width, height);
        this.gameProcessController = gameProcessController;
        loadAnimations();
        initHitbox(x, y, (int) (17 * GameController.SCALE), (int) (27 * GameController.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (25 * GameController.SCALE), (int) (35 * GameController.SCALE));

    }

    public void update(){
        updateHealthBar();

        if (currentHealth <= 0) {
            gameProcessController.setGameOver(true);
            return;
        }


        updateAttackBox();

        updatePosition();
        if (attacking) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();


    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 4) {
            return;
        }
        attackChecked = true;
        gameProcessController.checkIfPlayerHitsEnemy(attackBox);
    }

    private void updateAttackBox() {
        if (right) {
            attackBox.x = hitbox.x + hitbox.width + (int) (1 * GameController.SCALE);
        } else if (left) {
            attackBox.x = hitbox.x - hitbox.width - (int) (10 * GameController.SCALE);
        }
        attackBox.y = hitbox.y - (4 * GameController.SCALE);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float)(maxHealth)) * healthBarWidth);
    }

    public void render(Graphics g, int lvlOffset){
        g.drawImage(animations[playerAction][aniIndex],
                (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX,
                   (int) (hitbox.y - yDrawOffset),
                    width * flipW, height, null);
        drawHitbox(g, lvlOffset);
        drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - lvlOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    public void setAnimation(){
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = JUMPING;
            } else {
                playerAction = FALLING;
            }
        }

        if (attacking){
            playerAction = ATTACKING;
            if (startAni != ATTACKING) {
                aniIndex = 4;
                aniTick = 0;
                return;
            }
        }

        if (startAni != playerAction){
            resetAniTick();
        }
    }

    private void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    public void updatePosition() {
        moving = false;

        if (jump) {
            jump();
        }

//        if (!left && !right && !inAir) {
//            return;
//        }

        if (!inAir) {
            if ((!left && !right) || (right && left)) {
                return;
            }
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
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
                airSpeed += gravity;
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

    private void jump(){
        if (inAir){
            return;
        }
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir(){
        inAir = false;
        airSpeed = 0;
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

    public void loadLvlData(int[][] lvlData){
        this.lvlData = lvlData;
        if (!IsCharacterOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    public void resetDirBooleans(){
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }
}