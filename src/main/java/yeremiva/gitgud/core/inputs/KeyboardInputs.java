package yeremiva.gitgud.core.inputs;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        switch (Gamestate.state) {

            case PLAYING:
                gamePanel.getGameController().getGameProcessController().keyReleased(e);
                break;
            case MENU:
                gamePanel.getGameController().getMainMenuController().keyReleased(e);
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {

            case PLAYING:
                gamePanel.getGameController().getGameProcessController().keyPressed(e);
                break;
            case MENU:
                gamePanel.getGameController().getMainMenuController().keyPressed(e);
                break;
        }
    }
}
