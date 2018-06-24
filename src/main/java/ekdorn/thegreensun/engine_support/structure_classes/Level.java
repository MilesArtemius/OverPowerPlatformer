package ekdorn.thegreensun.engine_support.structure_classes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ekdorn.thegreensun.engine_support.every_day_singles.BlocksNEntities;
import ekdorn.thegreensun.engine_support.every_day_singles.MediaStorage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
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

    public ArrayList<Block> level;
    public ArrayList<Entity> entities;

    public Level(String filepath, JsonObject level) {
        // jo, jo, jo, jo
        this.name = level.get("name").getAsString();
        this.Height = level.get("height").getAsInt();
        this.Width = level.get("width").getAsInt();

        String positionMC = level.get("character_start").getAsString();
        System.out.println(positionMC);
        this.mainCharacterX = Integer.parseInt(positionMC.substring(0, positionMC.indexOf('x')));
        this.mainCharacterY = Integer.parseInt(positionMC.substring(positionMC.indexOf('x') + 1));

        this.level = new ArrayList<>();
        this.entities = new ArrayList<>();

        MediaStorage MS = MediaStorage.init(filepath);
        BlocksNEntities BnE = BlocksNEntities.init(filepath);

        System.out.println(BnE.getBlockz());
        System.out.println(BnE.getEntitiez());
        System.out.println("\n\n\n");
        System.out.println(MS.getTextures());
        System.out.println(MS.getBackgrounds());
        System.out.println(MS.getSounds());
        System.out.println("\n\n\n");

        for (Map.Entry<String, JsonElement> coordinates: level.get("level_pack").getAsJsonObject().entrySet()) {
            int x_coord = Integer.parseInt(coordinates.getKey().substring(0, coordinates.getKey().indexOf('x')));
            int y_coord = Integer.parseInt(coordinates.getKey().substring(coordinates.getKey().indexOf('x') + 1));

            this.level.add(new Block(BnE.getBlockz().get(level.get("level_pack").getAsJsonObject().get(coordinates.getKey()).getAsString()), x_coord, y_coord));
        }

        for (Map.Entry<String, JsonElement> coordinates: level.get("entity_pack").getAsJsonObject().entrySet()) {
            int x_coord = Integer.parseInt(coordinates.getKey().substring(0, coordinates.getKey().indexOf('x')));
            int y_coord = Integer.parseInt(coordinates.getKey().substring(coordinates.getKey().indexOf('x') + 1));

            this.entities.add(new Entity(BnE.getEntitiez().get(coordinates.getValue().getAsString()), x_coord, y_coord));
        }

        BnE.clearAll();
    }



    public ArrayList<Block> blocksInCoordinate(boolean horizontal, double upperRange, double lowerRange, @Nullable ArrayList<Block> source) {
        if (source == null) {
            source = this.level;
        }
        ArrayList<Block> answer = new ArrayList<>();
        for (Block block: source) {
            if (horizontal) {
                if ((block.getX() <= upperRange) && (block.getX() >= lowerRange)) {
                    answer.add(block);
                }
            } else {
                if ((block.getY() <= upperRange) && (block.getY() >= lowerRange)) {
                    answer.add(block);
                }
            }
        }
        return answer;
    }

    public Block getBlock(double xCoord, double yCoord) {
        for (Block block: this.level) {
            if ((block.getX() == xCoord) && (block.getY() == yCoord)) {
                return block;
            }
        }
        return null;
    }

    public ArrayList<Entity> entitiesInCoordinate(boolean horizontal, double upperRange, double lowerRange, @Nullable ArrayList<Entity> source) {
        if (source == null) {
            source = this.entities;
        }
        ArrayList<Entity> answer = new ArrayList<>();
        for (Entity entity: source) {
            if (horizontal) {
                if ((entity.getX() <= upperRange) && (entity.getX() >= lowerRange)) {
                    answer.add(entity);
                }
            } else {
                if ((entity.getY() <= upperRange) && (entity.getY() >= lowerRange)) {
                    answer.add(entity);
                }
            }
        }
        return answer;
    }

    public Entity getEntity(double xCoord, double yCoord) {
        for (Entity entity: this.entities) {
            if ((entity.getX() == xCoord) && (entity.getY() == yCoord)) {
                return entity;
            }
        }
        return null;
    }
}