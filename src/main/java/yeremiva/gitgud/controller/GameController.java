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

    private final GamePanel gamePanel;

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
        GameWindowView gameWindowView = new GameWindowView(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();

        log.info("Game loop was started");
    }

    /**
     * Init.
     * <p>
     * Initializing of the mainMenuController and gameProcessController classes.
     */
    private void initClasses() {
        mainMenuController = new MainMenuController(this);
        gameProcessController = new GameProcessController(this);
    }

    /**
     * Update.
     * <p>
     * Update of game states.
     */
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

    /**
     * Draw.
     * <p>
     * Render (draw) method of the game states
     * @param g draw system
     */
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

    /**
     * Game loop.
     * <p>
     * This method starts the game loop, which runs continuously and updates the game state and renders the graphics
     * at a desired frame rate and update rate.
     * </p>
     * The game loop uses two variables, FPS_SET and UPS_SET, to determine the time interval between frames and updates.
     * The game loop measures the elapsed time between iterations to ensure that the frame rate and update rate are consistent.
     * The loop also keeps track of the number of frames and updates that occur within a specific time interval (1 second),
     * which can be used for monitoring performance.
     * </p>
     * The game loop consists of the following steps:
     * Calculate the time interval per frame (timePerFrame) and per update (timePerUpdate) based on the desired frame rate (FPS_SET) and update rate (UPS_SET).
     * Initialize variables for tracking elapsed time (previousTime), frame and update counts (frames, updates), and the last check time (lastCheck).
     * Initialize variables for measuring time differences (deltaU, deltaF).
     * Enter an infinite loop that runs continuously until the game is exited.
     * Get the current time (currentTime) using the System.nanoTime() method.
     * Calculate the elapsed time since the previous iteration and add it to the deltaU and deltaF variables.
     * If deltaU reaches or exceeds 1, call the update() method to update the game state, increment the update count (updates),
     * and subtract 1 from deltaU.
     * If deltaF reaches or exceeds 1, call the gamePanel.repaint() method to render the graphics, increment the frame count (frames),
     * and subtract 1 from deltaF.
     * Check if the specified time interval (1 second) has passed since the last performance check.
     * If it has, update the lastCheck time, reset the frame and update counts, and optionally print the frame rate (FPS) and update rate (UPS) to the console.
     * </p>
     * Note: The FPS and UPS calculations assume that the timePerFrame and timePerUpdate values are accurate and represent the desired frame rate and update rate respectively.
     */
    private void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * The run method for the game loop thread.
     * It contains the main logic for the game loop, including updating the game state and rendering the graphics.
     * The loop runs continuously until the game is exited.
     */
    @Override
    public void run() {
        int FPS_SET = 120;
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        int UPS_SET = 200;
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

    /**
     * Window focus lost.
     * <p>
     * Method stops a player from moving if focus on the game window was lost.
     */
    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            gameProcessController.getPlayer().resetDirBooleans();
        }
    }

    /**
     * Gets main menu controller.
     *
     * @return the main menu controller
     */
    public MainMenuController getMainMenuController() {
        return mainMenuController;
    }

    /**
     * Gets game process controller.
     *
     * @return the game process controller
     */
    public GameProcessController getGameProcessController() {
        return gameProcessController;
    }
}