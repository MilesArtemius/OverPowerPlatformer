package ekdorn.thegreensun.engine_support.structure_classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ekdorn.thegreensun.engine_support.every_day_singles.MediaStorage;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by User on 15.06.2017.
 */
public class Block extends Coordinatable {
    public String name;
    private ArrayList<String> texture;
    public boolean isOneTextured;

    public Block(Block block, double x, double y) {
        this.name = block.name;
        this.isOneTextured = block.isOneTextured;
        this.texture = block.getImageLocation();

        this.setX(x);
        this.setY(y);
    }

    public Block(JsonObject block) {
        this.name = block.get("name").getAsString();
        this.texture = new ArrayList<>();

        if (block.get("texture") instanceof JsonObject) {
            isOneTextured = false;
            for (Map.Entry<String, JsonElement> element: block.get("texture").getAsJsonObject().entrySet()) {
                texture.add(element.getValue().getAsString());
            }
        } else {
            isOneTextured = true;
            System.out.println("lolololo");
            texture.add(0, block.get("texture").getAsString());
        }
    }

    public Image getTexture(int mill) {
        if (isOneTextured) {
            return MediaStorage.get().getTextures().get(this.texture.get(0));
        } else {
            return MediaStorage.get().getTextures().get(this.texture.get((int) Math.ceil(mill / (60 / texture.size()))));
        }
    }

    public ArrayList<String> getImageLocation() {
        return texture;
    }
}
