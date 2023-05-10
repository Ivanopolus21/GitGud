package yeremiva.gitgud.core.settings;

import org.json.JSONObject;
import yeremiva.gitgud.controller.GameProcessController;
import yeremiva.gitgud.model.characters.Player;
import yeremiva.gitgud.model.characters.Skeleton;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Logger;

public class EnemyConfig {
    // Logger
    private static Logger log = Logger.getLogger(Player.class.getName());
    private static GameProcessController gameProcessController;

    public static JSONObject getEnemyConfig(boolean fromSave) {
        String path = fromSave ? "save/savedLevel.json" : "enemy.json";

        try {
            File file = new File(path);
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));

            return new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveEnemyConfig() {
        JSONObject enemyConfig = new JSONObject();
        Skeleton skeleton = gameProcessController.getEnemyController().getSkeleton();

//        playerConfig.put("x", player.getX());
//        playerConfig.put("y", player.getY());
        enemyConfig.put("maxHealth", skeleton.getMaxHealth());
        enemyConfig.put("currentHealth", skeleton.getCurrentHealth());
        enemyConfig.put("walkSpeed", skeleton.getWalkSpeed());
        enemyConfig.put("damage", skeleton.getEnemyDamage());

//        try (
//                BufferedWriter writer = new BufferedWriter(new FileWriter("save/savedLevel.json"))
//        ) {
//            writer.write(playerConfig.toString());
//        } catch (IOException e) {
//            log.log(Level.WARNING, e.getMessage(), e);
//        }
    }
}
