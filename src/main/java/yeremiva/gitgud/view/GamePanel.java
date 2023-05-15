package yeremiva.gitgud.view;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.inputs.*;

import javax.swing.*;
import java.awt.*;

import static yeremiva.gitgud.controller.GameController.GAME_HEIGHT;
import static yeremiva.gitgud.controller.GameController.GAME_WIDTH;

public class GamePanel extends JPanel {
    private final MouseInputs mouseInputs;
    private final GameController gameController;

    public GamePanel(GameController gameController) {
        mouseInputs = new MouseInputs(this);
        this.gameController = gameController;

        setPanelSize();

        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    @Override
    public void paintComponent(Graphics g ){
        super.paintComponent(g);
        gameController.render(g);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size: " + GAME_WIDTH + " " + GAME_HEIGHT);
    }

    public GameController getGameController() {
        return gameController;
    }
}
