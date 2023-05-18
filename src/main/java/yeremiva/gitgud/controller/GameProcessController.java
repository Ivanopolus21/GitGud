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
import java.util.logging.Logger;

/**
 * Game Process Controller.
 * <p>
 *     Class that represents controller that control the actual gameplay of the game and the PLAYING state.
 * </p>
 */
public class GameProcessController extends State implements Statemethods {
    private final static Logger log = Logger.getLogger(GameProcessController.class.getName());

    private Player player;
    private LevelController levelController;
    private EnemyController enemyController;
    private ObjectController objectController;
    private PauseController pauseController;
    private GameOverController gameOverController;
    private LevelCompletedController levelCompletedController;
    private GameProcessView gameProcessView;
    private GameWinController gameWinController;

    private boolean paused = false;

    private int xLvlOffset, maxLvlOffsetX;

    private boolean gameOver, lvlCompleted, playerDying;
    private boolean endgameStatus;

    public GameProcessController(GameController gameController) {
        super(gameController);
        initClasses();

        calculateLevelOffset();
        loadStartLevel();
    }

    /**
     * Init.
     * <p>
     * Method that inits all the objects and also the player configuration.
     */
    private void initClasses() {
        gameProcessView = new GameProcessView(this);

        pauseController = new PauseController(this);
        levelCompletedController = new LevelCompletedController(this);
        gameOverController = new GameOverController(this);
        gameWinController = new GameWinController(this);

        levelController = new LevelController(gameController);
        enemyController = new EnemyController(this);
        objectController = new ObjectController(this);

        JSONObject playerConfig = PlayerConfig.getPlayerConfig();

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

    /**
     * Update.
     * <p>
     * Update method that call the specific controllers to update depending on which current game scenario is.
     */
    @Override
    public void update() {
        if (paused) {
            pauseController.update();
        } else if (isWin()) {
            gameWinController.update();
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

    /**
     * Draw.
     * <p>
     * Draw methid that calls the Game Process View draw method.
     *
     * @param g draw system
     */
    @Override
    public void draw(Graphics g) {
        gameProcessView.draw(g, xLvlOffset);
    }

    /**
     * Load Start Level.
     * <p>
     * Load start level enemies and objects.
     */
    public void loadStartLevel() {
        enemyController.loadEnemies(levelController.getCurrentLevel());
        objectController.loadObjects(levelController.getCurrentLevel());
    }

    /**
     * Load next level.
     */
    public void loadNextLevel() {
        levelController.loadNextLevel();
        player.setSpawn(levelController.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    /**
     * Unpause the game.
     */
    public void unpauseGame() {
        paused = false;
        log.info("Unpaused");
    }

    /**
     * Check close to border.
     * <p>
     * The method checks if players' hitbox is close to border.
     */
    private void checkCloseToBorder() {
        int playerX = (int) (player.getHitbox().x);
        int diff = playerX - xLvlOffset;

        int leftBorder = (int) (0.2 * GameController.GAME_WIDTH);
        int rightBorder = (int) (0.8 * GameController.GAME_WIDTH);
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

    /**
     * Check enemy hit.
     * <p>
     * Check if player hits an enemy.
     *
     * @param attackBox the player attack box
     * @param playerDamage the player damage
     */
    public void checkIfPlayerHitsEnemy(Rectangle2D.Float attackBox, int playerDamage) {
        enemyController.checkEnemyHit(attackBox, playerDamage);
    }

    /**
     * Check object hit.
     * <p>
     * Method checks if the player hits an object.
     *
     * @param attackBox the player attack box
     */
    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectController.checkObjectHit(attackBox);
    }

    /**
     * Check gem touched.
     * <p>
     * Checks if the player touched the gem.
     *
     * @param hitbox the player hitbox
     */
    public void checkGemTouched(Rectangle2D.Float hitbox) {
        objectController.checkObjectTouched(hitbox);
    }

    /**
     * Check spikes touched.
     * <p>
     * Checks if the player has touched the spikes.
     *
     * @param player the player
     */
    public void checkSpikesTouched(Player player) {
        objectController.checkSpikesTouched(player);
    }

    /**
     * Calculates level offset.
     */
    private void calculateLevelOffset() {
        maxLvlOffsetX = levelController.getCurrentLevel().getMaxLvlOffsetX();
    }

    /**
     * Mouse Dragged.
     * <p>
     * Checks if mouse has been dragged to change the state of the pause menu button.
     *
     * @param e the e
     */
    public void mouseDragged(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseDragged(e);
            }
        }
    }

    /**
     * Mouse Clicked.
     * <p>
     * Checks if left mouse button was clicked to set player attack.
     *
     * @param e the e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
        }
    }

    /**
     * Mouse Pressed.
     * <p>
     * Checks for any mouse pressing in any states.
     *
     * @param e the e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mousePressed(e);
            } else if (isWin()) {
                gameWinController.mousePressed(e);
            } else if (lvlCompleted) {
                levelCompletedController.mousePressed(e);
            }
         } else {
            gameOverController.mousePressed(e);
        }
    }

    /**
     * Mouse Released.
     * <p>
     * Checks for any mouse releasing in any states.
     *
     * @param e the e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseReleased(e);
            } else if (isWin()) {
                gameWinController.mouseReleased(e);
            } else if (lvlCompleted) {
                levelCompletedController.mouseReleased(e);
            }
        } else {
            gameOverController.mouseReleased(e);
        }
    }
    /**
     * Mouse Moved.
     * <p>
     * Checks for any mouse moving in any states.
     *
     * @param e the e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver) {
            if (paused) {
                pauseController.mouseMoved(e);
            } else if (isWin()) {
                gameWinController.mouseMoved(e);
            } else if (lvlCompleted) {
                levelCompletedController.mouseMoved(e);
            }
        } else {
            gameOverController.mouseMoved(e);
        }
    }

    /**
     * Key Pressed.
     * <p>
     * Checks for key presses in game over and game win states. Also, sets the player movement if the state in PLAYING.
     *
     * @param e the e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            gameOverController.keyPressed(e);
        } else if (isWin()) {
            gameWinController.keyPressed(e);
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

                        if (paused) {
                            log.info("Paused");
                        } else {
                            log.info("Unpaused");
                        }

                        break;
                }
        }
    }

    /**
     * Key Released.
     * <p>
     * Sets the key release for the player movement keys that he can stop moving after the press.
     *
     * @param e the e
     */
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

    /**
     * Set player dying.
     * <p>
     * Sets player dying boolean to true or false.
     *
     * @param playerDying the player dying state (boolean)
     */
    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }

    /**
     * Set max level offset.
     * <p>
     * Sets max level offset according to the parameter.
     *
     * @param xLvlOffset the level offset parameter
     */
    public void setMaxLvlOffsetX(int xLvlOffset) {
        this.maxLvlOffsetX = xLvlOffset;
    }

    /**
     * Set level completed.
     * <p>
     * Sets level completed boolean to true or false.
     *
     * @param levelCompleted the level completed state (boolean)
     */
    public void setLevelCompleted(boolean levelCompleted) {
        this.lvlCompleted = levelCompleted;
    }

    /**
     * Set game over.
     * <p>
     * Sets game over boolean to true or false.
     *
     * @param gameOver the game over state (boolean)
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Set game win.
     * <p>
     * Sets game win boolean to true or false.
     *
     * @param endgameStatus the game win state (boolean)
     */
    public void setWin(boolean endgameStatus) {
        this.endgameStatus = endgameStatus;
    }

    /**
     * Gets player.
     *
     * @return the Player
     */
    public Player getPlayer(){
        return player;
    }

    /**
     * Gets Enemy Controller.
     *
     * @return the EnemyController
     */
    public EnemyController getEnemyController() {
        return enemyController;
    }

    /**
     * Gets Object Controller.
     *
     * @return the ObjectController
     */
    public ObjectController getObjectController() {
        return objectController;
    }

    /**
     * Gets Level Controller.
     *
     * @return the LevelController
     */
    public LevelController getLevelController() {
        return levelController;
    }

    /**
     * Gets Pause Controller.
     *
     * @return the PauseController
     */
    public PauseController getPauseController() {
        return pauseController;
    }

    /**
     * Gets Game Over Controller.
     *
     * @return the GameOverController
     */
    public GameOverController getGameOverController() {
        return gameOverController;
    }

    /**
     * Gets Level Completed Controller.
     *
     * @return the LevelCompletedController
     */
    public LevelCompletedController getLevelCompletedController() {
        return levelCompletedController;
    }

    /**
     * Gets Game Win Controller.
     *
     * @return the GameWinController
     */
    public GameWinController getGameWinController() {
        return gameWinController;
    }

    /**
     * Gets Endgame Status (win).
     *
     * @return the boolean
     */
    public boolean isWin() {
        return endgameStatus;
    }

    /**
     * Gets Paused.
     *
     * @return the boolean
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets Player Dying.
     *
     * @return the boolean
     */
    public boolean isPlayerDying() {
        return playerDying;
    }

    /**
     * Gets Game Over.
     *
     * @return the boolean
     */

    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Gets Level Completed.
     *
     * @return boolean
     */
    public boolean isLvlCompleted() {
        return lvlCompleted;
    }

    /**
     * Reset.
     * <p>
     * Main level reset that resets all the main level things.
     */
    public void resetAll() {
        //reset player, enemy etc.
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        player.resetAll();
        enemyController.resetAllEnemies();
        objectController.resetAllObjects();

        if (levelController.getLvlIndex() >= 1) {
            log.info("Level was reseted");
        }
    }
}