package yeremiva.gitgud.core.settings;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Enemy Configuration.
 * <p>
 *     Class that have a configuration of the enemy JSON file.
 * </p>
 */
public class EnemyConfig {
    /**
     * Enemy Configuration.
     * <p>
     *     Configuration of the enemy JSON file.
     * </p>
     *
     * @return the enemy JSON file
     */
    public static JSONObject getEnemyConfig() {
        String path = "enemy.json";
        try {
            File file = new File(path);
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));

            return new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
