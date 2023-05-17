package yeremiva.gitgud.core.settings;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Player Configuration.
 * <p>
 *     Class that have a configuration of the player JSON file.
 * </p>
 */
public class PlayerConfig {
    /**
     * Player Configuration.
     * <p>
     *     Configuration of the player JSON file.
     * </p>
     *
     * @return the player JSON file
     */
    public static JSONObject getPlayerConfig() {
        String path = "player.json";

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
