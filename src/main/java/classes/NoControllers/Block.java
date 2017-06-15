package classes.NoControllers;

import java.util.UUID;

/**
 * Created by User on 15.06.2017.
 */
public class Block {
    int sizeX;
    int sizeY;
    UUID texture;

    public Block getFromPresetType(UUID identificator) {
        return new Block();
    }
}
