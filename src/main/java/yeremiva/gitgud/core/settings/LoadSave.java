package yeremiva.gitgud.core.settings;

import yeremiva.gitgud.controller.GameController;
import yeremiva.gitgud.model.characters.Skeleton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static yeremiva.gitgud.core.settings.Constants.EnemyConstants.SKELETON;

public class LoadSave {

//    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String PLAYER_ATLAS = "player_sprites_fixed_2.png";
//    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites_fixed.png";
//    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BUTTONS = "button_atlas_fixed.png";
//    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND = "menu_background_fixed.png";
//    public static final String MENU_BACKGROUND_IMAGE = "background_menu.png";
    public static final String MENU_BACKGROUND_IMAGE = "background_menu_fixed.png";
//    public static final String PAUSE_BACKGROUND = "pause_menu.png";
//    public static final String URM_BUTTONS = "urm_buttons.png";
//    public static final String VOLUME_BUTTONS = "volume_buttons.png";
//    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String PAUSE_BACKGROUND = "pause_menu_fixed.png";
    public static final String URM_BUTTONS = "urm_buttons_fixed.png";
    public static final String VOLUME_BUTTONS = "volume_buttons_fixed.png";
    public static final String SOUND_BUTTONS = "sound_button_fixed.png";
//    public static final String PLAYING_BACKGROUND_IMAGE = "playing_bg_img.png";
    public static final String PLAYING_BACKGROUND_IMAGE = "playing_bg_img_fixed.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
//    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds_fixed.png";
//    public static final String SKELETON_SPRITE = "mini_skeleton_fixed.png";
    public static final String SKELETON_SPRITE = "mini_skeleton_fixed_for_real_this_time (2).png";
//    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String STATUS_BAR = "health_power_bar_fixed.png";
    public static final String COMPLETED_IMAGE = "completed_sprite_fixed.png";
    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";
    public static final String DEATH_SCREEN = "death_screen.png";


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
