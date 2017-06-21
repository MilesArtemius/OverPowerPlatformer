package classes.NoControllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

/**
 * Created by User on 15.06.2017.
 */
public class Level {
    String name;
    int Height;
    int Width;
    Block [][] level;

    public Level(String name, int height, int width, JsonObject level_pack) {
        this.name = name;
        Height = height;
        Width = width;
        this.level = new Block [Height][Width];
        for (Map.Entry<String, JsonElement> coordinates: level_pack.entrySet()) {
            int x_coord = Integer.parseInt(coordinates.getKey().substring(0, coordinates.getKey().indexOf('x')));
            int y_coord = Integer.parseInt(coordinates.getKey().substring(coordinates.getKey().indexOf('x') + 1));
            this.level[x_coord][y_coord] = new Block(level_pack.get(coordinates.getKey()).getAsString());
        }
    }
}
