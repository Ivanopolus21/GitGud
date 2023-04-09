package yeremiva.gitgud.view;

import javax.swing.*;
//GAME WINDOW
public class GameWindowView {
    private JFrame jframe;
    private int width = 400;
    private int height = 400;

    public GameWindowView(GamePanel gamePanel){
        jframe = new JFrame();
        jframe.setSize(width, height);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
