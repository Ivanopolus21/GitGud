package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.HelpMethods;
import yeremiva.gitgud.core.settings.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static yeremiva.gitgud.core.settings.Constants.PlayerConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.*;

public class Player extends Character{
    //30 height, 18 width 00 -- 6, 3
    //43 height, 25 width 00 -- 10, 5

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.5f;
    private int[][] lvlData;
    private float xDrawOffset = 7 * GameController.SCALE;
    private float yDrawOffset = 4 * GameController.SCALE;

    // Jumping/Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * GameController.SCALE;
    private float jumpSpeed = -2.25f * GameController.SCALE;
    private float fallSpeedAfterCollision = 0.5f * GameController.SCALE;
    private boolean inAir = false;


    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (16 * GameController.SCALE), (int) (27 * GameController.SCALE));

    }

    public void update(){
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
//        drawHitbox(g);
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    public void setAnimation(){
        int startAni = playerAction;

        if(moving){
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (inAir){
            if (airSpeed < 0) {
                playerAction = JUMPING;
            } else {
                playerAction = KNEELING;
            }
        }

        if (attacking){
            playerAction = ATTACKING;

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

        if (!left && !right && !inAir) {
            return;
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
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


    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][8];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 32, j*32, 32, 32);
            }
        }
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
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking){
        this.attacking = attacking;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump){
        this.jump = jump;
    }
}