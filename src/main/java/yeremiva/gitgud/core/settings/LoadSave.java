package yeremiva.gitgud.core.settings;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Load Save.
 * <p>
 *     The class that loads all the images that the game needs.
 * </p>
 */
public class LoadSave {
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND_IMAGE = "background_menu.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String PLAYING_BACKGROUND_IMAGE = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_background_object.png";
    public static final String SMALL_CLOUDS = "small_background_objects.png";
    public static final String SKELETON_SPRITE = "mini_skeleton.png";
    public static final String STATUS_BAR = "health_bar.png";
    public static final String COMPLETED_IMAGE = "completed_sprite.png";
    public static final String GEM_ATLAS = "gems_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String WIN_SCREEN = "win_screen.png";

    /**
     * Gets Sprite Atlas.
     * <p>
     *     The method gets sprites of an atlas that was provided as an argument.
     * </p>
     *
     * @param atlas the atlas
     * @return sprites of the atlas
     */
    public static BufferedImage GetSpriteAtlas(String atlas){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + atlas);
        try {
            img = ImageIO.read(is);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    /**
     * Gets All Levels.
     * <p>
     *     Gets images of all levels that projects has in directory "levels".
     * </p>
     *
     * @return levels images
     */
    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/levels");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }
            }
        }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++) {
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return imgs;
    }
}
