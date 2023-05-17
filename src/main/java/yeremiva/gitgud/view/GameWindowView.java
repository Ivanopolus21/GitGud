package yeremiva.gitgud.view;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * Game Window View class.
 * <p>
 *     Class that represents the Game Window View.
 * </p>
 */
public class GameWindowView {

    public GameWindowView(GamePanel gamePanel) {
        JFrame jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            /**
             * Window Focus Lost.
             *
             * @param e the e
             */
            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGameController().windowFocusLost();
            }
        });
    }
}
