package yeremiva.gitgud.view;

import javax.swing.*;
//GAME WINDOW
public class GameWindowView {
    private JFrame jframe;

    public GameWindowView(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setSize(400, 400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}
