package yeremiva.gitgud.controller;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.core.states.State;
import yeremiva.gitgud.core.states.Statemethods;
import yeremiva.gitgud.model.characters.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//PLAYING
public class GameProcessController extends State implements Statemethods {
    private Player player;
    private LevelController levelController;

    public GameProcessController(GameController gameController) {
        super(gameController);
        initClasses();
    }

    private void initClasses() {
        levelController = new LevelController(gameController);
        player = new Player(200, 200, (int) (32 * GameController.SCALE), (int) (32 * GameController.SCALE));
        player.loadLvlData(levelController.getCurrentLevel().getLvlData());
    }

    @Override
    public void update() {
        levelController.update();
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        levelController.draw(g);
        player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_DOWN:
                player.setAttacking(true);
                break;
            case KeyEvent.VK_ESCAPE:
                Gamestate.state = Gamestate.MENU;
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }

    public void  windowFocusLost(){
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }

}
