package greensun.engine_support.structure_classes;

import greensun.engine_support.every_day_singles.MediaStorage;
import greensun.platformer_engine.PlatformerAI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entity extends Coordinatable {
    String name;

    ArrayList<Ability> abilities;
    HashMap<String, String> aiModifiers;

    private ArrayList<String> texture;

    public Entity(Entity entity, double x, double y) {
        this.name = entity.name;
        this.texture = entity.texture;

        this.setX(x);
        this.setY(y);
    }

    public Entity(JsonObject entity) {
        abilities = new ArrayList<>();
        JsonObject abls = entity.get("abilities").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry: abls.entrySet()) {
            abilities.add(new Ability(entry.getKey(), entry.getValue().getAsJsonObject()));
        }
        this.name = entity.get("name").getAsString();

        aiModifiers = new HashMap<>();
        for (Map.Entry<String, JsonElement> entry: entity.get("ai_modifiers").getAsJsonObject().entrySet()) {
            aiModifiers.put(entry.getKey(), entry.getValue().getAsString());
        }

        this.texture = new ArrayList<>();
        for (Map.Entry<String, JsonElement> element: entity.get("texture").getAsJsonObject().entrySet()) {
            texture.add(element.getValue().getAsString());
        }
    }

    public Image getTexture(int mill) {
        return MediaStorage.get().getTextures().get(this.texture.get((int) Math.ceil(mill / (60 / texture.size()))));
    }

    public ArrayList<String> getImageLocation() {
        return texture;
    }

    public void react() {

    }
}