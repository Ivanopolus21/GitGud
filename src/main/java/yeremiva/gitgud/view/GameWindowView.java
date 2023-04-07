package yeremiva.gitgud.view;

import javax.swing.*;

public class GameWindowView {
    private JFrame jframe;

    public GameWindowView(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setSize(400, 400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setVisible(true);
    }
}
