package classes.StructureClasses;


import classes.MainAndMenu.Depacker;
import com.google.gson.JsonElement;

import java.util.HashMap;


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

    public HashMap<String, Double> getRulez(double w, double h, double param, boolean force) {
        if ((w != width) || (h != height) || (force)) {
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

    public HashMap<String, Block> getBlockz(String filepath, boolean force) {
        if (force) {
            for (String name : BlockzfromFile.keySet()) {
                if (!BlockSet.containsKey(name)) {
                    BlockSet.put(name, new Block(filepath, BlockzfromFile.get(name).getAsJsonObject()));
                }
            }
        }
        return BlockSet;
    }

    public static GameRulez get(String filepath) {
        return GRulez = new GameRulez(filepath);
    }

    private GameRulez(String filepath) {
        if (filepath.equals("null")) {
            RulezfromFile = Depacker.getStartedConfiguration(getClass(), "/config.json");
            BlockzfromFile = Depacker.getStartedConfiguration(getClass(), "/blockset.json");
        } else {
            RulezfromFile = Depacker.getStartedConfiguration(getClass(), filepath + "&config.json");
            BlockzfromFile = (Depacker.getStartedConfiguration(getClass(), filepath + "&blockset.json"));
        }

        BlockSet = getBlockz(filepath, true);
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