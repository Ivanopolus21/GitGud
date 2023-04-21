package yeremiva.gitgud.controller;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.core.settings.LoadSave;
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
    private PauseController pauseController;
    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * GameController.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GameController.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - GameController.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * GameController.TILES_SIZE;

    public GameProcessController(GameController gameController) {
        super(gameController);
        initClasses();
    }

    private void initClasses() {
        levelController = new LevelController(gameController);
        player = new Player(200, 200, (int) (32 * GameController.SCALE), (int) (32 * GameController.SCALE));
        player.loadLvlData(levelController.getCurrentLevel().getLvlData());
        pauseController = new PauseController(this);
    }

    @Override
    public void update() {
        if (!paused){
            levelController.update();
            player.update();
            checkCloseToBorder();
        } else {
            pauseController.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) (player.getHitbox().x);
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        levelController.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0,0,GameController.GAME_WIDTH, GameController.GAME_HEIGHT);
            pauseController.draw(g);
        }

    }

    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseController.mouseDragged(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseController.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseController.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseController.mouseMoved(e);
        }
    }

//    @Override
//    public void mouseDragged(MouseEvent e) {
//        if (paused) {
//            pauseController.mouseDragged(e);
//        }
//    }

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
                paused = !paused;
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

    public void unpauseGame() {
        paused = false;
    }

    public void  windowFocusLost(){
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }

}
