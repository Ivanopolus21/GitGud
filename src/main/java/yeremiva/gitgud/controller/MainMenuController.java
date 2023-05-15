package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.*;
import yeremiva.gitgud.view.GameMenuButtonView;
import yeremiva.gitgud.view.MainMenuView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainMenuController extends State implements Statemethods {
    private GameMenuButtonView[] buttons;
    private MainMenuView mainMenuView;

    public MainMenuController(GameController gameController) {
        super(gameController);
        this.mainMenuView = new MainMenuView(this);
        this.buttons = mainMenuView.getButtons();
    }

    @Override
    public void update() {
        for (GameMenuButtonView gmb : buttons) {
            gmb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        mainMenuView.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (GameMenuButtonView gmb : buttons) {
            if(isIn(e, gmb)){
                gmb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (GameMenuButtonView gmb : buttons) {
            if (isIn(e, gmb)){
                if (gmb.isMousePressed()){
                    gmb.applyGamestate();
                }
                break;
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //reset after clickong on button
        for (GameMenuButtonView gmb: buttons){
            gmb.setMouseOver(false);
        }
        //check if we are hovering the button
        for (GameMenuButtonView gmb: buttons){
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    private void resetButtons() {
        for (GameMenuButtonView gmb : buttons) {
            gmb.resetBools();
        }
    }
}
