package yeremiva.gitgud.view;

import com.sun.xml.internal.bind.v2.model.core.ID;
import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.core.inputs.KeyboardInputs;
import yeremiva.gitgud.core.inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static yeremiva.gitgud.core.inputs.Constants.Directions.*;
import static yeremiva.gitgud.core.inputs.Constants.PlayerConstants.*;

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
        Dimension size = new Dimension(1280, 800);
        setPreferredSize(size);
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
