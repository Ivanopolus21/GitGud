package yeremiva.gitgud.core.inputs;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        switch(Gamestate.state) {
            case PLAYING:
                gamePanel.getGameController().getGameProcessController().mouseClicked(e);
                break;
        }
    }

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
