package yeremiva.gitgud.controller;

import yeremiva.gitgud.core.settings.LoadSave;
import yeremiva.gitgud.core.states.Gamestate;
import yeremiva.gitgud.view.UrmButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static yeremiva.gitgud.core.settings.Constants.View.URMButtons.URM_SIZE;

public class GameOverController {
    private GameProcessController gameProcessController;
    private BufferedImage img;
    private int imgX, imgY, imgWidth, imgHeight;
    private UrmButton menu, play;

    public GameOverController(GameProcessController gameProcessController) {
        this.gameProcessController = gameProcessController;
        createImg();
        createButtons();
    }

    private void createButtons() {
        int menuX = (int) (335 * GameController.SCALE);
        int playX = (int) (440 * GameController.SCALE);
        int y = (int) (195 * GameController.SCALE);
        play = new UrmButton(playX, y, URM_SIZE, URM_SIZE, 0);
        menu = new UrmButton(menuX, y, URM_SIZE, URM_SIZE, 2);
    }

    private void createImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN);
        imgWidth = (int) (img.getWidth() * GameController.SCALE);
        imgHeight = (int) (img.getHeight() * GameController.SCALE);
        imgX = GameController.GAME_WIDTH / 2 - imgWidth / 2;
        imgY = (int) (100 * GameController.SCALE);
    }

    public void draw (Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, GameController.GAME_WIDTH, GameController.GAME_HEIGHT);

        g.drawImage(img, imgX, imgY, imgWidth, imgHeight, null);

        menu.draw(g);
        play.draw(g);
//        g.setColor(Color.white);
//        g.drawString("Game Over", GameController.GAME_WIDTH / 2, 150);
//        g.drawString("Press ESCAPE button to enther Main Menu!", GameController.GAME_WIDTH / 2, 300);
    }

    public void update() {
        menu.update();
        play.update();
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gameProcessController.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    private boolean isIn(UrmButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e) {
        play.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e)) {
            menu.setMouseOver(true);
        } else if (isIn(play, e)) {
            play.setMouseOver(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(menu, e)) {
            if (menu.isMousePressed()) {
                gameProcessController.resetAll();
                Gamestate.state = Gamestate.MENU;
            }
        } else if (isIn(play, e)) {
            if (play.isMousePressed()) {
                gameProcessController.resetAll();
            }
        }
        menu.resetBools();
        play.resetBools();
    }

    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e)) {
            menu.setMousePressed(true);
        } else if (isIn(play, e)) {
            play.setMousePressed(true);
        }
    }
}
