package greensun.engine_support.every_day_singles;

import com.google.gson.JsonElement;
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
        if (filepath.equals("null")) {
            BlockSet.putAll(getStartedBlockz(getClass(), "/blockset.json"));
            EntitySet.putAll(getStartedEntities(getClass(), "/entityset.json"));
        } else {
            BlockSet.putAll(getStartedBlockz(getClass(), filepath + "&blockset.json"));
            EntitySet.putAll(getStartedEntities(getClass(), filepath + "&entityset.json"));
        }
    }

    public static HashMap<String, Entity> getStartedEntities(Class app, String path) {
        HashMap<String, Entity> hm = new HashMap<>();

        try {
            for (Map.Entry<String, JsonElement> element: Depacker.getStarted(app, path).entrySet()) {
                hm.put(element.getKey(), new Entity(path, element.getValue().getAsJsonObject()));
            }
        } catch (Exception e) {
            //e.getMessage();
        }
        return hm;
    }

    public static HashMap<String, Block> getStartedBlockz(Class app, String path) {
        HashMap<String, Block> hm = new HashMap<>();

        try {
            System.out.println(Depacker.getStarted(app, path));

            for (Map.Entry<String, JsonElement> element: Depacker.getStarted(app, path).entrySet()) {
                hm.put(element.getKey(), new Block(element.getValue().getAsJsonObject()));
            }
        } catch (Exception e) {
            //e.getMessage();
        }
        return hm;
    }
}
