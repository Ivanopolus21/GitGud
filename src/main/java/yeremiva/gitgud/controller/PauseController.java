package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

//PAUSE OVERLAY
public class PauseController {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgWidth, bgHeight;
    public PauseController(){
        loadBackground();

    }

    //Function that loads backgroun of the pause menu
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgWidth = (int) (backgroundImg.getWidth() * GameController.SCALE);
        bgHeight = (int) (backgroundImg.getHeight() * GameController.SCALE);
        bgX = GameController.GAME_WIDTH / 2 - bgWidth / 2;
        bgY = (int) (25 * GameController.SCALE);
    }

    public void update(){

    }

    public void draw(Graphics g){
        g.drawImage(backgroundImg, bgX, bgY, bgWidth, bgHeight, null);
    }

    public void mouseDragged(){

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
