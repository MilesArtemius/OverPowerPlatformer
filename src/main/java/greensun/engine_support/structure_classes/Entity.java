package greensun.engine_support.structure_classes;

import greensun.engine_support.every_day_singles.GameRulez;
import greensun.platformer_engine.PlatformerAI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Entity {
    String name;

    ArrayList<Ability> abilities;

    PlatformerAI ai;

    public Image skin;

    int xCoord;
    int yCoord;

    public Entity(String filepath, JsonObject entity) {
        abilities = new ArrayList<>();
        JsonObject abls = entity.get("abilities").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry: abls.entrySet()) {
            abilities.add(new Ability(entry.getKey(), entry.getValue().getAsJsonObject()));
        }
        this.name = entity.get("name").getAsString();
        try {
            if ((filepath.indexOf("/") == 0) || (filepath.equals("null"))) {
                this.skin = new Image(getClass().getResource("/textures/" + entity.get("texture").getAsString()).toString(), 128, 128, true, true);
            } else {
                this.skin = new Image(Files.newInputStream(Paths.get(filepath.substring(0, filepath.lastIndexOf("levels")) + "textures\\" + entity.get("texture").getAsString())), 128, 128, true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Entity(String filepath, String name) {
        GameRulez gr = GameRulez.get(filepath);
        this.name = gr.getEntitiez(filepath, true).get(name).name;
        this.skin = gr.getEntitiez(filepath, true).get(name).skin;
    }

    public void resetImage(Image texture) {
        this.skin = texture;
    }
}
