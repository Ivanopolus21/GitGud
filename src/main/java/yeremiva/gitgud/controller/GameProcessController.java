package yeremiva.gitgud.controller;

import org.json.JSONObject;

import yeremiva.gitgud.core.settings.PlayerConfig;
import yeremiva.gitgud.core.states.*;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.view.GameProcessView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class GameProcessController extends State implements Statemethods {
    private Player player;
    private LevelController levelController;
    private EnemyController enemyController;
    private ObjectController objectController;
    private PauseController pauseController;
    private GameOverController gameOverController;
    private LevelCompletedController levelCompletedController;
    private GameProcessView gameProcessView;

    private boolean paused = false;

    private int xLvlOffset, maxLvlOffsetX;
    private final int leftBorder = (int) (0.2 * GameController.GAME_WIDTH);
    private final int rightBorder = (int) (0.8 * GameController.GAME_WIDTH);

    private boolean gameOver, lvlCompleted, playerDying;

    public GameProcessController(GameController gameController) {
        super(gameController);
        initClasses();

        calculateLevelOffset();
        loadStartLevel();
    }

    private void initClasses() {
        gameProcessView = new GameProcessView(this);

        pauseController = new PauseController(this);
        levelCompletedController = new LevelCompletedController(this);
        gameOverController = new GameOverController(this);

        levelController = new LevelController(gameController);
        enemyController = new EnemyController(this);
        objectController = new ObjectController(this);

        JSONObject playerConfig = PlayerConfig.getPlayerConfig(false);

//        player = new Player(200, 200, (int) (32 * GameController.SCALE), (int) (32 * GameController.SCALE), 100, 100, 10, 1.5f,this);
        player = new Player(200, 200, (int) (32 * GameController.SCALE), (int) (32 * GameController.SCALE),
                playerConfig.getInt("maxHealth"),
                playerConfig.getInt("currentHealth"),
                playerConfig.getFloat("walkSpeed"),
                playerConfig.getInt("damage"),
                this);

        player.loadLvlData(levelController.getCurrentLevel().getLvlData());
        player.setSpawn(levelController.getCurrentLevel().getPlayerSpawn());
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

    @Override
    public void draw(Graphics g) {
        gameProcessView.draw(g, xLvlOffset);
    }

    private void loadStartLevel() {
        enemyController.loadEnemies(levelController.getCurrentLevel());
        objectController.loadObjects(levelController.getCurrentLevel());
    }

    public void loadNextLevel() {
        levelController.loadNextLevel();
        player.setSpawn(levelController.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    public void unpauseGame() {
        paused = false;
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

    public void checkIfPlayerHitsEnemy(Rectangle2D.Float attackBox, int playerDamage) {
        enemyController.checkEnemyHit(attackBox, playerDamage);
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

    private void calculateLevelOffset() {
        maxLvlOffsetX = levelController.getCurrentLevel().getMaxLvlOffsetX();
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

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

    public void setMaxLvlOffsetX(int xLvlOffset) {
        this.maxLvlOffsetX = xLvlOffset;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
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

    public PauseController getPauseController() {
        return pauseController;
    }

    public GameOverController getGameOverController() {
        return gameOverController;
    }

    public LevelCompletedController getLevelCompletedController() {
        return levelCompletedController;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isLvlCompleted() {
        return lvlCompleted;
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
