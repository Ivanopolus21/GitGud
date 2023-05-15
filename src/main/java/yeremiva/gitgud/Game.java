package yeremiva.gitgud;

import yeremiva.gitgud.controller.GameController;

import java.util.logging.Logger;


public class Game {
    private final static Logger log = Logger.getLogger(Game.class.getName());

    public static void main(String[] args) {
        new GameController();

        log.info("Game was started!");
    }
}
