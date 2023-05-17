package yeremiva.gitgud.core.states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * State methods.
 * <p>
 *     All state methods that is being implemented.
 * </p>
 */
public interface Statemethods {
    /**
     * Update.
     */
    void update();

    /**
     * Draw.
     *
     * @param g draw system
     */
    void draw(Graphics g);

    /**
     * Mouse Clicked.
     *
     * @param e the e
     */
    void mouseClicked(MouseEvent e);

    /**
     * Mouse Pressed.
     *
     * @param e the e
     */
    void mousePressed(MouseEvent e);

    /**
     * Mouse Released.
     *
     * @param e the e
     */
    void mouseReleased(MouseEvent e);

    /**
     * Mouse Moved.
     *
     * @param e the e
     */
    void mouseMoved(MouseEvent e);

    /**
     * Key Pressed.
     *
     * @param e the e
     */
    void keyPressed(KeyEvent e);

    /**
     * Key Released.
     *
     * @param e the e
     */
    void keyReleased(KeyEvent e);
}
