package yeremiva.gitgud.controller;

import yeremiva.gitgud.view.GamePanel;
import yeremiva.gitgud.view.GameWindowView;
//GAME
public class GameController {
    private GameWindowView gameWindowView;
    private GamePanel gamePanel;
    public GameController() {
        gamePanel = new GamePanel();
        gameWindowView = new GameWindowView(gamePanel);
        gamePanel.requestFocus();
    }
}