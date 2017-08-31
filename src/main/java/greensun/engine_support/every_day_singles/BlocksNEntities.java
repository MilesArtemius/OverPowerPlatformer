package greensun.engine_support.every_day_singles;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import greensun.engine_support.Depacker;
import greensun.engine_support.structure_classes.Block;
import greensun.engine_support.structure_classes.Entity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlocksNEntities {
    private static BlocksNEntities singles;

    private static HashMap<String, Block> BlockSet = new HashMap<>();
    private static HashMap<String, Entity> EntitySet = new HashMap<>();

    public HashMap<String, Block> getBlockz() {
        return BlockSet;
    }

    public HashMap<String, Entity> getEntitiez() {
        return EntitySet;
    }

    public static BlocksNEntities get(String filepath) {
        if (singles == null) {
            BlockSet = new HashMap<>();
            EntitySet = new HashMap<>();
            return singles = new BlocksNEntities(filepath);
        } else {
            return singles;
        }
    }

    public static BlocksNEntities init(String filepath) {
        if (singles == null) {
            BlockSet = new HashMap<>();
            EntitySet = new HashMap<>();
            return singles = new BlocksNEntities(filepath);
        } else {
            return singles = new BlocksNEntities(filepath);
        }
    }

    private BlocksNEntities(String filepath) {
        BlockSet.putAll(getStartedBlockz(getClass(), filepath + "&blockset.json"));
        EntitySet.putAll(getStartedEntities(getClass(), filepath + "&entityset.json"));
    }

    public static HashMap<String, Entity> getStartedEntities(Class app, String path) {
        HashMap<String, Entity> hm = new HashMap<>();
        ArrayList<String> systemBlockNames = new ArrayList<>();
        try {
            for (Map.Entry<String, JsonElement> element: Depacker.getStarted(app, path).entrySet()) {
                if (element.getValue() instanceof JsonObject) {
                    hm.put(element.getKey(), new Entity(element.getValue().getAsJsonObject()));
                } else {
                    Entity missingBlock = new Entity(Depacker.getStartedSingleJson(app, "/entity.json").get(element.getKey()).getAsJsonObject());
                    systemBlockNames.add(missingBlock.getImageLocation());
                    hm.put(element.getKey(), missingBlock);
                }
            }
            if (!systemBlockNames.isEmpty()) {
                MediaStorage.get().subloadTextures(systemBlockNames);
            }
        } catch (Exception e) {
            //e.getMessage();
        }
        return hm;
    }

    public static HashMap<String, Block> getStartedBlockz(Class app, String path) {
        HashMap<String, Block> hm = new HashMap<>();
        ArrayList<String> systemBlockNames = new ArrayList<>();
        try {
            for (Map.Entry<String, JsonElement> element: Depacker.getStarted(app, path).entrySet()) {
                if (element.getValue() instanceof JsonObject) {
                    hm.put(element.getKey(), new Block(element.getValue().getAsJsonObject()));
                } else {
                    Block missingBlock = new Block(Depacker.getStartedSingleJson(app, "/blockset.json").get(element.getKey()).getAsJsonObject());
                    systemBlockNames.add(missingBlock.getImageLocation());
                    hm.put(element.getKey(), missingBlock);
                }
            }
            if (!systemBlockNames.isEmpty()) {
                MediaStorage.get().subloadTextures(systemBlockNames);
            }
        } catch (Exception e) {
            //e.getMessage();
        }
        return hm;
    }

    public void clearAll() {
        BlockSet.clear();
        EntitySet.clear();
        singles = null;
    }
}
