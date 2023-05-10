package yeremiva.gitgud.core.settings;

import org.json.JSONObject;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.model.characters.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class PlayerConfig {
    // Logger
    private static Logger log = Logger.getLogger(Player.class.getName());
    private static GameProcessController gameProcessController;

    public static JSONObject getPlayerConfig(boolean fromSave) {
        String path = fromSave ? "save/savedLevel.json" : "player.json";

        try {
            File file = new File(path);
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));

            return new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void savePlayerConfig() {
        JSONObject playerConfig = new JSONObject();
        Player player = gameProcessController.getPlayer();

//        playerConfig.put("x", player.getX());
//        playerConfig.put("y", player.getY());
        playerConfig.put("maxHealth", player.getMaxHealth());
        playerConfig.put("currentHealth", player.getCurrentHealth());
        playerConfig.put("walkSpeed", player.getWalkSpeed());
        playerConfig.put("damage", player.getPlayerDamage());

//        try (
//                BufferedWriter writer = new BufferedWriter(new FileWriter("save/savedLevel.json"))
//        ) {
//            writer.write(playerConfig.toString());
//        } catch (IOException e) {
//            log.log(Level.WARNING, e.getMessage(), e);
//        }
    }
}
