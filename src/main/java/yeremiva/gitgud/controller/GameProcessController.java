package yeremiva.gitgud.controller;

import yeremiva.gitgud.Game;
import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.core.states.*;
import yeremiva.gitgud.model.characters.Enemy;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.view.GameOverView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.regex.Pattern;

import static yeremiva.gitgud.core.settings.Constants.Enviroment.*;

//PLAYING
public class GameProcessController extends State implements Statemethods {
    private Player player;
    private LevelController levelController;
    private EnemyController enemyController;
    private ObjectController objectController;
    private PauseController pauseController;
    private GameOverController gameOverController;
    private LevelCompletedController levelCompletedController;
    private boolean paused = false;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * GameController.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * GameController.GAME_WIDTH);
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    private boolean gameOver;
    private boolean lvlCompleted;
    private boolean playerDying;

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

        calculateLevelOffset();
        loadStartLevel();
    }

    public void loadNextLevel() {
        levelController.loadNextLevel();
        player.setSpawn(levelController.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    private void loadStartLevel() {
        enemyController.loadEnemies(levelController.getCurrentLevel());
        objectController.loadObjects(levelController.getCurrentLevel());
    }

    private void calculateLevelOffset() {
        maxLvlOffsetX = levelController.getCurrentLevel().getMaxLvlOffsetX();
    }

    private void initClasses() {
        levelController = new LevelController(gameController);
        enemyController = new EnemyController(this);
        objectController = new ObjectController(this);

        player = new Player(200, 200, (int) (32 * GameController.SCALE), (int) (32 * GameController.SCALE), this);
        player.loadLvlData(levelController.getCurrentLevel().getLvlData());
        player.setSpawn(levelController.getCurrentLevel().getPlayerSpawn());

        pauseController = new PauseController(this);
        gameOverController = new GameOverController(this);
        levelCompletedController = new LevelCompletedController(this);
    }

    @Override
    public void update() {
        if (paused) {
            pauseController.update();
        } else if (lvlCompleted) {
            levelCompletedController.update();
        } else if (gameOver) {
            gameOverController.update();
        } else if (playerDying) {
            player.update();
        } else {
            levelController.update();
            player.update();
            enemyController.update(levelController.getCurrentLevel().getLvlData(), player);
            objectController.update();
            checkCloseToBorder();
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
        objectController.draw(g, xLvlOffset);

        if (paused) {
            pauseController.draw(g);
        } else if (gameOver) {
            gameOverController.draw(g);
        } else if (lvlCompleted) {
            levelCompletedController.draw(g);
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkIfPlayerHitsEnemy(Rectangle2D.Float attackBox) {
        enemyController.checkEnemyHit(attackBox);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectController.checkObjectHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectController.checkObjectTouched(hitbox);
    }

    public void checkSpikesTouched(Player player) {
        objectController.checkSpikesTouched(player);
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
            } else if (lvlCompleted) {
                levelCompletedController.mousePressed(e);
            }
         } else {
            gameOverController.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseReleased(e);
            } else if (lvlCompleted) {
                levelCompletedController.mouseReleased(e);
            }
        } else {
            gameOverController.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseMoved(e);
            } else if (lvlCompleted) {
                levelCompletedController.mouseMoved(e);
            }
        } else {
            gameOverController.mouseMoved(e);
        }
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

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

    public void setMaxLvlOffsetX(int xLvlOffset) {
        this.maxLvlOffsetX = xLvlOffset;
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

    public EnemyController getEnemyController() {
        return enemyController;
    }

    public ObjectController getObjectController() {
        return objectController;
    }

    public LevelController getLevelController() {
        return levelController;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

    public void resetAll() {
        //reset player, enemy etc.
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyController.resetAllEnemies();
        objectController.resetAllObjects();
    }
}
