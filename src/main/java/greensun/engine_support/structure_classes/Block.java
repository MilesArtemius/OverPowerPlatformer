package greensun.engine_support.structure_classes;

import com.google.gson.JsonObject;
import greensun.engine_support.every_day_singles.GameRulez;
import javafx.scene.image.Image;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by User on 15.06.2017.
 */
public class Block {
    public String name;
    public Image texture;

    public double xCoord;
    public double yCoord;

    public Block(String filepath, String name, double x, double y) {
        GameRulez gr = GameRulez.get(filepath);
        this.name = gr.getBlockz(filepath, true).get(name).name;
        this.texture = gr.getBlockz(filepath, true).get(name).texture;
        this.xCoord = x;
        this.yCoord = y;
    }

    public Block(Block block, double x, double y) {
        this.name = block.name;
        this.texture = block.texture;


        this.xCoord = x;
        this.yCoord = y;
    }

    public Block(String filepath, JsonObject block) {
        this.name = block.get("name").getAsString();
        try {
            if ((filepath.indexOf("/") == 0) || (filepath.equals("null"))) {
                this.texture = new Image(getClass().getResource("/textures/" + block.get("texture").getAsString()).toString(), 128, 128, true, true);
            } else {
                this.texture = new Image(Files.newInputStream(Paths.get(filepath.substring(0, filepath.lastIndexOf("levels")) + "textures\\" + block.get("texture").getAsString())), 128, 128, true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetImage(Image texture) {
        this.texture = texture;
    }
}
