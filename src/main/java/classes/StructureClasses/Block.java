package classes.StructureClasses;

import com.google.gson.JsonObject;
import javafx.scene.image.Image;

/**
 * Created by User on 15.06.2017.
 */
public class Block {
    public String name;
    public Image texture;

    public Block(String name) {
        GameRulez gr = GameRulez.get("null");
        this.name = gr.getBlockz().get(name).name;
        this.texture = gr.getBlockz().get(name).texture;
    }

    public Block(JsonObject block) {
        this.name = block.get("name").getAsString();
        if (block.get("texture").getAsString().equals("null")) {
            this.texture = null;
        } else {
            this.texture = new Image(getClass().getResource("/textures/" + block.get("texture").getAsString() + ".png").toString(), 122, 122, true, true);
        }
    }

    public Block() {
    }
}
