package greensun.engine_support.structure_classes;

import com.google.gson.JsonObject;
import greensun.engine_support.every_day_singles.MediaStorage;
import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by User on 15.06.2017.
 */
public class Block {
    public String name;
    //public Image skin;
    private String texture;

    public double xCoord;
    public double yCoord;

    public Block(Block block, double x, double y) {
        this.name = block.name;
        this.texture = block.texture;

        this.xCoord = x;
        this.yCoord = y;
    }

    public Block(JsonObject block) {
        this.name = block.get("name").getAsString();
        this.texture = block.get("texture").getAsString();
    }

    public Image getTexture() {
        return MediaStorage.get().getTextures().get(this.texture);
    }
}
