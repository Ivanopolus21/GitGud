package yeremiva.gitgud.view;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;

import static yeremiva.gitgud.core.settings.Constants.View.Buttons.*;

public class MainMenuButtonView {
    private final static Logger log = Logger.getLogger(MainMenuButtonView.class.getName());

    private final Gamestate state;

    private Rectangle bounds;
    private BufferedImage[] imgs;

    private final int xPos, yPos, rowIndex;
    private int index;
    private final int xOffsetCenter = B_WIDTH / 2;
    private boolean mouseOver, mousePressed;

    //Function to get menu buttons position
    public MainMenuButtonView(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;

        initBounds();
        loadImgs();
    }

    //Function to load the menu button images
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i =  0; i < imgs.length; i++){
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    //Function that draws the buttons on the screen
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    public void applyGamestate() {
        Gamestate.state = state;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Gamestate getState() {
        return state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
