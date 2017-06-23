package classes.NoControllers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by User on 11.03.2017.
 */
public class GameRulez {
    private static GameRulez GRulez;
    static double width = 0;
    static double height = 0;

    private static HashMap<String, JsonElement> RulezfromFile;
    private static HashMap<String, Double> RuleSet = new HashMap<>();
    private static HashMap<String, JsonElement> BlockzfromFile;
    private static HashMap<String, Block> BlockSet = new HashMap<>();

    public HashMap<String, Double> getRulez(double w, double h, double param) {
        if ((w != width) || (h != height)) {
            System.out.println("-=-=-=-= REDRAWED =-=-=-=-");
            width = w;
            height = h;
            for (String name : RulezfromFile.keySet()) {
                switch (name.charAt(0)) {
                    case '0':
                        RuleSet.put(name.substring(2), width / RulezfromFile.get(name).getAsDouble());
                        break;
                    case '1':
                        RuleSet.put(name.substring(2), height / RulezfromFile.get(name).getAsDouble());
                        break;
                    case '2':
                        RuleSet.put(name.substring(2), param / RulezfromFile.get(name).getAsDouble());
                        break;
                    case '3':
                        RuleSet.put(name.substring(2), (Double) RulezfromFile.get(name).getAsDouble());
                        break;
                }
            }
        }
        return RuleSet;
    }

    public HashMap<String, Block> getBlockz() {
        for (String name : BlockzfromFile.keySet()) {
            BlockSet.put(name, new Block(BlockzfromFile.get(name).getAsJsonObject()));
        }
        return BlockSet;
    }

    public static GameRulez get(boolean inout) {
        return GRulez = new GameRulez(inout);
    }

    private GameRulez(boolean inout) {
        if (inout) {
            RulezfromFile = Depacker.getStartedConfiguration(getClass(), "/config.json");
            BlockzfromFile = Depacker.getStartedConfiguration(getClass(), "/blockset.json");
        } else {
            //TODO: reading from an external resource;
        }
    }


    /*public double getRule(String rule) {
        for (Const c : RuleSet) {
            if (c.getName().equals(rule)) {
                return c.getValue();
            }
        }
        return 0;
    }

    public void setRule(String rule, int means) {
        for (Const c : RuleSet) {
            if (c.getName().equals(rule)) {
                c.setValue(means);
            }
        }
    }*/
}