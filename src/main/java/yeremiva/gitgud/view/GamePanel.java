package yeremiva.gitgud.view;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.inputs.KeyboardInputs;
import yeremiva.gitgud.core.inputs.MouseInputs;

import javax.swing.*;
import java.awt.*;

import static yeremiva.gitgud.controller.GameController.GAME_HEIGHT;
import static yeremiva.gitgud.controller.GameController.GAME_WIDTH;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private GameController gameController;

    public GamePanel(GameController gameController){
        mouseInputs = new MouseInputs(this);
        this.gameController = gameController;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size: " + GAME_WIDTH + " " + GAME_HEIGHT);
    }

    public void updateGame(){

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        gameController.render(g);
    }

    public GameController getGameController(){
        return gameController;
    }
}
