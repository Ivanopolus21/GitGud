package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverController {
    private GameProcessController gameProcessController;

    public GameOverController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Game Over", GameController.GAME_WIDTH / 2, 150);
        g.drawString("Press ESCAPE button to enther Main Menu!", GameController.GAME_WIDTH / 2, 300);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameProcessController.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
