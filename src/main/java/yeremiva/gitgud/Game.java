package yeremiva.gitgud;

import yeremiva.gitgud.controller.GameController;

import java.util.logging.Logger;

/**
 * Game class.
 */
public class Game {
    private final static Logger log = Logger.getLogger(Game.class.getName());

    /**
     * Main class.
     * <p>
     *     Main class of the project that starts the actual game.
     * </p>
     * @param args the args
     */
    public static void main(String[] args) {
        new GameController();

        log.info("Game was started!");
    }
}
