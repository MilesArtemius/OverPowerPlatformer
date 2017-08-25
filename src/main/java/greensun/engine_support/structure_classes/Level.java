package greensun.engine_support.structure_classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by User on 15.06.2017.
 */
public class Level {
    public String name;
    public int Height;
    public int Width;

    public int mainCharacterX;
    public int mainCharacterY;

    public Block[][] level;
    public Entity[][] entities;

    public Level(String filepath, JsonObject level) {
        // jo, jo, jo, jo
        this.name = level.get("name").getAsString();
        this.Height = level.get("height").getAsInt();
        this.Width = level.get("width").getAsInt();

        String positionMC = level.get("character_start").getAsString();
        System.out.println(positionMC);
        this.mainCharacterX = Integer.parseInt(positionMC.substring(0, positionMC.indexOf('x')));
        this.mainCharacterY = Integer.parseInt(positionMC.substring(positionMC.indexOf('x') + 1));

        this.level = new Block [Height][Width];
        this.entities = new Entity [Height][Width];

        for (Map.Entry<String, JsonElement> coordinates: level.get("level_pack").getAsJsonObject().entrySet()) {
            int x_coord = Integer.parseInt(coordinates.getKey().substring(0, coordinates.getKey().indexOf('x')));
            int y_coord = Integer.parseInt(coordinates.getKey().substring(coordinates.getKey().indexOf('x') + 1));

            this.level[x_coord][y_coord] = new Block(filepath, level.get("level_pack").getAsJsonObject().get(coordinates.getKey()).getAsString());
        }

        for (Map.Entry<String, JsonElement> coordinates: level.get("entity_pack").getAsJsonObject().entrySet()) {
            int x_coord = Integer.parseInt(coordinates.getKey().substring(0, coordinates.getKey().indexOf('x')));
            int y_coord = Integer.parseInt(coordinates.getKey().substring(coordinates.getKey().indexOf('x') + 1));

            this.entities[x_coord][y_coord] = new Entity(filepath, level.get("entity_pack").getAsJsonObject().get(coordinates.getKey()).getAsString());
        }
    }
}
