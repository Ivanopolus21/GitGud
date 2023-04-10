package yeremiva.gitgud.controller;

import yeremiva.gitgud.view.GamePanel;
import yeremiva.gitgud.view.GameWindowView;
//GAME
public class GameController implements Runnable{
    private GameWindowView gameWindowView;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;

    public GameController() {
        gamePanel = new GamePanel();
        gameWindowView = new GameWindowView(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1_000_000_000.0/ FPS_SET;
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while(true){

            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS:" + frames);
                frames = 0;
            }
        }
    }
}