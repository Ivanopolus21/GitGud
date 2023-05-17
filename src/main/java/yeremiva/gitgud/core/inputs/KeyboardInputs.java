package yeremiva.gitgud.core.inputs;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard Inputs.
 * <p>
 *     Class that control the user keyboard inputs during the whole process.
 * </p>
 */
public class KeyboardInputs implements KeyListener {
    private final GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Key Pressed.
     * <p>
     * Controls pressing the keys and send it to game states.
     *
     * @param e the e
     */
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

    /**
     * Key Released.
     * <p>
     * Controls releasing the keys and send it to game states.
     *
     * @param e the e
     */
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
    public void keyTyped(KeyEvent e) {
    }
}
