package greensun.engine_support.structure_classes;

import greensun.engine_support.every_day_singles.MediaStorage;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Created by User on 15.06.2017.
 */
public class Block extends Coordinatable {
    public String name;
    private ArrayList<String> texture;
    public boolean isOneTextured;

    public Block(Block_Pred block, double x, double y) {
        this.name = block.name;
        this.isOneTextured = block.isOneTextured;
        this.texture = block.getImageLocation();

        this.setX(x);
        this.setY(y);
    }

    public Image getTexture(int mill) {
        if (isOneTextured) {
            return MediaStorage.get().getTextures().get(this.texture.get(0));
        } else {
            return MediaStorage.get().getTextures().get(this.texture.get((int) Math.ceil(mill / (60 / texture.size()))));
        }
    }
}
