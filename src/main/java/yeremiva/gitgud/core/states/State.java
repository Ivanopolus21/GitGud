package yeremiva.gitgud.core.states;

import yeremiva.gitgud.controller.GameController;

public class State {

    protected GameController gameController;
    public State(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getGameController() {
        return gameController;
    }
}
