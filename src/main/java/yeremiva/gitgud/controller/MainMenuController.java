package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.core.states.State;
import yeremiva.gitgud.core.states.Statemethods;
import yeremiva.gitgud.view.GameMenuButton;
import yeremiva.gitgud.view.MainMenuView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

//MENU
public class MainMenuController extends State implements Statemethods {
    private GameMenuButton[] buttons;
    private MainMenuView mainMenuView;

    public MainMenuController(GameController gameController) {
        super(gameController);
        this.mainMenuView = new MainMenuView(this);
        this.buttons = mainMenuView.getButtons();
    }

    @Override
    public void update() {
        for (GameMenuButton gmb : buttons) {
            gmb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        mainMenuView.draw(g);
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
