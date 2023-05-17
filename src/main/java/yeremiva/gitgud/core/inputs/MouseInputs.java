package yeremiva.gitgud.core.inputs;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Mouse Inputs.
 * <p>
 *     Class that control the user mouse inputs.
 * </p>
 */
public class MouseInputs implements MouseListener, MouseMotionListener {
    private final GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Mouse Moved.
     * <p>
     * Controls moving the mouse and send it to game states.
     *
     * @param e the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        switch(Gamestate.state) {
            case PLAYING:
                gamePanel.getGameController().getGameProcessController().mouseMoved(e);
                break;
            case MENU:
                gamePanel.getGameController().getMainMenuController().mouseMoved(e);
                break;
        }
    }

    /**
     * Mouse Clicked.
     * <p>
     * Controls clicking the mouse and send it to game states.
     *
     * @param e the e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch(Gamestate.state) {
            case PLAYING:
                gamePanel.getGameController().getGameProcessController().mouseClicked(e);
                break;
        }
    }

    /**
     * Mouse Pressed.
     * <p>
     * Controls pressing the mouse and send it to game states.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch(Gamestate.state) {
            case PLAYING:
                gamePanel.getGameController().getGameProcessController().mousePressed(e);
                break;
            case MENU:
                gamePanel.getGameController().getMainMenuController().mousePressed(e);
                break;
        }
    }

    /**
     * Mouse Released.
     * <p>
     * Controls releasing the mouse and send it to game states.
     *
     * @param e the e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch(Gamestate.state) {
            case PLAYING:
                gamePanel.getGameController().getGameProcessController().mouseReleased(e);
                break;
            case MENU:
                gamePanel.getGameController().getMainMenuController().mouseReleased(e);
                break;
        }
    }

    /**
     * Mouse Dragged.
     * <p>
     * Controls dragging the mouse and send it to PLAYING state.
     *
     * @param e the e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        switch(Gamestate.state) {
            case PLAYING:
                gamePanel.getGameController().getGameProcessController().mouseDragged(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
