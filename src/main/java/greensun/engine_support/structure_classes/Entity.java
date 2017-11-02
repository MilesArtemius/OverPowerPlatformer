package greensun.engine_support.structure_classes;

import greensun.engine_support.every_day_singles.MediaStorage;
import greensun.platformer_engine.PlatformerAI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Entity extends Coordinatable {
    String name;

    ArrayList<Ability> abilities;

    PlatformerAI ai;

    public String texture;

    public Entity(JsonObject entity) {
        abilities = new ArrayList<>();
        JsonObject abls = entity.get("abilities").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry: abls.entrySet()) {
            abilities.add(new Ability(entry.getKey(), entry.getValue().getAsJsonObject()));
        }
        this.name = entity.get("name").getAsString();
    }

    public Entity(Entity block, double x, double y) {
        this.name = block.name;
        this.texture = block.texture;

        this.setX(x);
        this.setY(y);
    }

    public Image getTexture() {
        return MediaStorage.get().getTextures().get(this.texture);
    }

    public String getImageLocation() {
        return texture;
    }
}