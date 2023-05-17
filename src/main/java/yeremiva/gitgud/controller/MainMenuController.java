package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.*;
import yeremiva.gitgud.view.MainMenuButtonView;
import yeremiva.gitgud.view.MainMenuView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

public class MainMenuController extends State implements Statemethods {
    private final static Logger log = Logger.getLogger(MainMenuController.class.getName());

    private final MainMenuButtonView[] buttons;
    private final MainMenuView mainMenuView;

    public MainMenuController(GameController gameController) {
        super(gameController);

        mainMenuView = new MainMenuView();
        buttons = mainMenuView.getButtons();
    }

    /**
     * Update.
     * <p>
     *     Method that updates main menu buttons.
     * </p>
     */
    @Override
    public void update() {
        for (MainMenuButtonView gmb : buttons) {
            gmb.update();
        }
    }

    /**
     * Draw.
     * <p>
     *     Draw method that calls Main Menu View draw method.
     * </p>
     */
    @Override
    public void draw(Graphics g) {
        mainMenuView.draw(g);
    }

    /**
     * Mouse Pressed.
     * <p>
     * Method that checks for pressing the buttons.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (MainMenuButtonView gmb : buttons) {
            if(isIn(e, gmb)){
                gmb.setMousePressed(true);
                break;
            }
        }
    }

    /**
     * Mouse Released.
     * <p>
     * Method that call the other methods depending on which button mouse released the press.
     *
     * @param e the e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MainMenuButtonView gmb : buttons) {
            if (isIn(e, gmb)){
                if (gmb.isMousePressed()){
                    gmb.applyGamestate();

                    log.info(gmb.getState() + " state was setted");
                }
                break;
            }
        }
        resetButtons();
    }

    /**
     * Mouse Moved.
     * <p>
     * Method that checks for hovering on buttons.
     *
     * @param e the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        //reset after clicking on button
        for (MainMenuButtonView gmb: buttons){
            gmb.setMouseOver(false);
        }
        //check if we are hovering the button
        for (MainMenuButtonView gmb: buttons){
            if (isIn(e, gmb)){
                gmb.setMouseOver(true);
                break;
            }
        }
    }

    /**
     * Key Pressed.
     * <p>
     * Sets the PLAYING state when a player clicks on ENTER.
     *
     * @param e the e
     */
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

    /**
     * Reset.
     * <p>
     *     Reset of all the main menu buttons.
     * </p>
     */
    private void resetButtons() {
        for (MainMenuButtonView gmb : buttons) {
            gmb.resetBools();
        }
    }
}
