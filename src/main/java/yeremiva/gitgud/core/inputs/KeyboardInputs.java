package yeremiva.gitgud.core.inputs;

import yeremiva.gitgud.view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static yeremiva.gitgud.core.inputs.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGameController().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGameController().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGameController().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGameController().getPlayer().setRight(false);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                gamePanel.getGameController().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_A:
                gamePanel.getGameController().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_S:
                gamePanel.getGameController().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_D:
                gamePanel.getGameController().getPlayer().setRight(true);
                break;
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            gamePanel.getGameController().getPlayer().setAttacking(true);
        }
    }
}
