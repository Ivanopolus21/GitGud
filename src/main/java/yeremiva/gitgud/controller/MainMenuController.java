package yeremiva.gitgud.controller;

import javafx.scene.control.MenuButton;
import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.core.states.State;
import yeremiva.gitgud.core.states.Statemethods;
import yeremiva.gitgud.view.GameMenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

//MENU
public class MainMenuController extends State implements Statemethods {
    private GameMenuButton[] buttons = new GameMenuButton[3];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;

    public MainMenuController(GameController gameController) {
        super(gameController);
        loadButtons();
        loadBackground();
    }

    //Function that loads menu background
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * GameController.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * GameController.SCALE);
        menuX = GameController.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * GameController.SCALE);
    }

    //Function to load all game menu buttons
    private void loadButtons() {
        buttons[0] = new GameMenuButton(GameController.GAME_WIDTH / 2, (int) (150 * GameController.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new GameMenuButton(GameController.GAME_WIDTH / 2, (int) (220 * GameController.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new GameMenuButton(GameController.GAME_WIDTH / 2, (int) (290 * GameController.SCALE), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (GameMenuButton gmb : buttons) {
            gmb.update();
        }
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        for (GameMenuButton gmb : buttons) {
            gmb.draw(g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GameMenuButton gmb : buttons) {
            if(isIn(e, gmb)){
                gmb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GameMenuButton gmb : buttons) {
            if (isIn(e, gmb)){
                if (gmb.isMousePressed()){
                    gmb.applyGamestate();
                }
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for (GameMenuButton gmb : buttons) {
            gmb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //reset after clickong on button
        for (GameMenuButton gmb: buttons){
            gmb.setMouseOver(false);
        }
        //check if we are hovering the button
        for (GameMenuButton gmb: buttons){
            if (isIn(e, gmb)){
                gmb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
