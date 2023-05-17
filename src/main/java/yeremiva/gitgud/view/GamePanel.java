package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.inputs.*;

import javax.swing.*;
import java.awt.*;

import static yeremiva.gitgud.controller.GameController.GAME_HEIGHT;
import static yeremiva.gitgud.controller.GameController.GAME_WIDTH;

/**
 * Game Panel class.
 * <p>
 *     The class is responsible for the Game Panel.
 * </p>
 */
public class GamePanel extends JPanel {
    private final GameController gameController;

    public GamePanel(GameController gameController) {
        this.gameController = gameController;

        setPanelSize();

        MouseInputs mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Paint Component.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g ){
        super.paintComponent(g);
        gameController.render(g);
    }

    /**
     * Sets the panel size.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
//        System.out.println("size: " + GAME_WIDTH + " " + GAME_HEIGHT);
    }

    /**
     * Gets game controller.
     *
     * @return the game controller
     */
    public GameController getGameController() {
        return gameController;
    }
}
