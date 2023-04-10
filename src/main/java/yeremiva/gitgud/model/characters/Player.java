package yeremiva.gitgud.model.characters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static yeremiva.gitgud.core.inputs.Constants.Directions.*;
import static yeremiva.gitgud.core.inputs.Constants.Directions.DOWN;
import static yeremiva.gitgud.core.inputs.Constants.PlayerConstants.*;

public class Player extends Character{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 30;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean moving = false;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update(){
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void render(Graphics g){
        g.drawImage(animations[playerAction][aniIndex], (int)x, (int)y, 128, 128, null);
    }

    public void setDirection(int direction){
        this.playerDirection = direction;
        moving = true;
    }

    public void setMoving(boolean moving){
        this.moving = moving;
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(playerAction)){
                aniIndex = 0;
            }
        }
    }

    public void setAnimation(){

        if(moving){
            playerAction = WALKING;
        } else {
            playerAction = IDLE;
        }
    }

    public void updatePosition(){

        if (moving){
            switch(playerDirection){
                case LEFT:
                    x -= 5;
                    break;
                case UP:
                    y -= 5;
                    break;
                case RIGHT:
                    x += 5;
                    break;
                case DOWN:
                    y += 5;
                    break;
            }
        }
    }


    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[9][8];
            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 32, j*32, 32, 32);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
