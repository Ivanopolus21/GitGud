package yeremiva.gitgud.model.characters;

import yeremiva.gitgud.core.settings.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static yeremiva.gitgud.core.settings.Constants.PlayerConstants.*;

public class Player extends Character{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
    }

    public void update(){
        updatePosition();
        updateHitbox();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, width, height, null);
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

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left){
            x += playerSpeed;
            moving = true;
        }

        if (up && !down){
            y -= playerSpeed;
            moving = true;
        } else if (down && !up){
            y += playerSpeed;
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

    public boolean isLeft() {
        return left;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
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
