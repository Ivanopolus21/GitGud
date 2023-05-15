package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.GamePanel;
import yeremiva.gitgud.view.GameWindowView;

import java.awt.*;
import java.util.logging.Logger;

public class GameController implements Runnable{
    private final static Logger log = Logger.getLogger(GameController.class.getName());

    private GameProcessController gameProcessController;
    private MainMenuController mainMenuController;

    private final GameWindowView gameWindowView;
    private final GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public final static float SCALE = 1.5f;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public GameController() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindowView = new GameWindowView(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();

        log.info("Game loop was started");
    }

    private void initClasses() {
        mainMenuController = new MainMenuController(this);
        gameProcessController = new GameProcessController(this);
    }

    public void update() {
        switch (Gamestate.state) {
            case MENU:
                mainMenuController.update();
                break;
            case PLAYING:
                gameProcessController.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                mainMenuController.draw(g);
                break;
            case PLAYING:
                gameProcessController.draw(g);
                break;
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS:" + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            gameProcessController.getPlayer().resetDirBooleans();
        }
    }

    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    public GameProcessController getGameProcessController() {
        return gameProcessController;
    }
}