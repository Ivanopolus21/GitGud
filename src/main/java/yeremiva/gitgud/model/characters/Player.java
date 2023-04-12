package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.settings.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static yeremiva.gitgud.core.settings.Constants.PlayerConstants.*;
import static yeremiva.gitgud.core.settings.HelpMethods.CanMoveHere;

public class Player extends Character{
    //30 height, 18 width 00 -- 6, 3
    //43 height, 25 width 00 -- 10, 5

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 1.0f;
    private int[][] lvlData;
    private float xDrawOffset = 10 * GameController.SCALE;
    private float yDrawOffset = 5 * GameController.SCALE;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 25 * GameController.SCALE, 43 * GameController.SCALE);
    }

    public void update(){
        updatePosition();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);
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
            playerAction = WALKING;
        } else {
            playerAction = IDLE;
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

    public void updatePosition(){
        moving = false;
        if (!left && !right && !up && !down){
            return;
        }

        float xSpeed = 0, ySpeed = 0;

        if (left && !right) {
            xSpeed = -playerSpeed;
        } else if (right && !left){
            xSpeed = playerSpeed;
        }

        if (up && !down){
            ySpeed = -playerSpeed;
        } else if (down && !up){
            ySpeed = playerSpeed;
        }

//        if (CanMoveHere(x + xSpeed, y + ySpeed, width, height, lvlData)) {
//            this.x += xSpeed;
//            this.y += ySpeed;
//            moving = true;
//        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
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
}
