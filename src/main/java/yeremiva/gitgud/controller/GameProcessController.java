package yeremiva.gitgud.controller;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.core.states.State;
import yeremiva.gitgud.core.states.Statemethods;
import yeremiva.gitgud.model.characters.Enemy;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.view.GameOverView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import static yeremiva.gitgud.core.settings.Constants.Enviroment.*;

//PLAYING
public class GameProcessController extends State implements Statemethods {
    private Player player;
    private LevelController levelController;
    private EnemyController enemyController;
    private PauseController pauseController;
    private GameOverController gameOverController;
    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * GameController.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GameController.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - GameController.TILES_IN_WIDTH;
    private int maxLvlOffsetX = maxTilesOffset * GameController.TILES_SIZE;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    private boolean gameOver;

    public GameProcessController(GameController gameController) {
        super(gameController);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMAGE);
        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        for (int i = 0; i < smallCloudsPos.length; i++){
            smallCloudsPos[i] = (int) (90 * GameController.SCALE) + rnd.nextInt((int) (100 * GameController.SCALE));
        }
    }

    private void initClasses() {
        levelController = new LevelController(gameController);
        enemyController = new EnemyController(this);
        player = new Player(200, 200, (int) (32 * GameController.SCALE), (int) (32 * GameController.SCALE), this);
        player.loadLvlData(levelController.getCurrentLevel().getLvlData());
        pauseController = new PauseController(this);
        gameOverController = new GameOverController(this);
    }

    @Override
    public void update() {
        if (!paused){
            levelController.update();
            player.update();
            enemyController.update(levelController.getCurrentLevel().getLvlData(), player);
            checkCloseToBorder();
        } else {
            pauseController.update();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int) (player.getHitbox().x);
        int diff = playerX - xLvlOffset;

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        } else if (xLvlOffset < 0) {
            xLvlOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT, null);

        drawClouds(g);

        levelController.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyController.draw(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0,0,GameController.GAME_WIDTH, GameController.GAME_HEIGHT);
            pauseController.draw(g);
        } else if (gameOver) {
            gameOverController.draw(g);
        }

    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 3; i++) {
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int) (xLvlOffset * 0.3), (int) (204 * GameController.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }
        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int) (xLvlOffset * 0.7 ), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALl_CLOUD_HEIGHT, null);
        }

    }

    public void resetAll() {
        //reset player, enemy etc.
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkIfPlayerHitsEnemy(Rectangle2D.Float attackBox) {
        enemyController.checkEnemyHit(attackBox);
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseDragged(e);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mousePressed(e);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseReleased(e);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseMoved(e);
            }
        }
    }

//    @Override
//    public void mouseDragged(MouseEvent e) {
//        if (paused) {
//            pauseController.mouseDragged(e);
//        }
//    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverController.keyPressed(e);
        } else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_DOWN:
                    player.setAttacking(true);
                    break;
                case KeyEvent.VK_ESCAPE:
                    paused = !paused;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
        }
    }

    public void unpauseGame() {
        paused = false;
    }

    public void  windowFocusLost(){
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }

}
