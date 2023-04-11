package yeremiva.gitgud.core.settings;

import yeremiva.gitgud.controller.GameController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites (1).png";
//    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";

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

    public static int[][] GetLevelData(){
        int [][] lvlData = new int[GameController.TILES_IN_HEIGHT][GameController.TILES_IN_WIDTH];
        BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);

        for (int j = 0; j < img.getHeight(); j++){
            for (int i = 0; i < img.getWidth(); i ++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 150) {
                    value = 0;
                }
//                if (value >= 48) {
//                    value = 0;
//                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }
}
