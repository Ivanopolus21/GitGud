package yeremiva.gitgud.core.states;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.view.MainMenuButtonView;

import java.awt.event.MouseEvent;

/**
 * State class.
 * <p>
 *     Class that controls the states.
 * </p>
 */
public class State {
    protected GameController gameController;

    public State(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Is in a button.
     * <p>
     * Check if players' cursor is "in" a button bounds.
     *
     * @param gmb the button
     * @param e the e
     * @return the boolean
     */
    public boolean isIn(MouseEvent e, MainMenuButtonView gmb) {
        return gmb.getBounds().contains(e.getX(), e.getY());
    }
}
