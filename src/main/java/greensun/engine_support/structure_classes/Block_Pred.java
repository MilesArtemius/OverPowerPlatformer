package greensun.engine_support.structure_classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

public class Block_Pred {
    public String name;
    private ArrayList<String> texture;
    public boolean isOneTextured;

    public Block_Pred(JsonObject block) {
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

    public ArrayList<String> getImageLocation() {
        return texture;
    }
}
