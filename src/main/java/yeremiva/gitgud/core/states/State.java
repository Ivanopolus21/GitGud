package yeremiva.gitgud.core.states;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.view.MainMenuButtonView;

import java.awt.event.MouseEvent;

public class State {
    protected GameController gameController;

    public State(GameController gameController) {
        this.gameController = gameController;
    }

    //Function that says if player pressing inside the button
    public boolean isIn(MouseEvent e, MainMenuButtonView gmb) {
        return gmb.getBounds().contains(e.getX(), e.getY());
    }

    public GameController getGameController() {
        return gameController;
    }
}
