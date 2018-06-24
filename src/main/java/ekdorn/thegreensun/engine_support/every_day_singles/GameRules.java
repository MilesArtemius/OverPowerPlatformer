package ekdorn.thegreensun.engine_support.every_day_singles;


import ekdorn.thegreensun.engine_support.Depacker;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by User on 11.03.2017.
 */
public class GameRules {
    private static GameRules GRulez;
    static double width = 0;
    static double height = 0;

    private static HashMap<String, JsonElement> RulesFromFile;
    private static HashMap<String, Double> RuleSet = new HashMap<>();

    public HashMap<String, Double> getRules(double w, double h, double param, boolean force) {
        if ((w != width) || (h != height) || (force)) {
            System.out.println("-=-=-=-= REDRAWN =-=-=-=-");
            width = w;
            height = h;
            for (String name : RulesFromFile.keySet()) {
                switch (name.charAt(0)) {
                    case '0':
                        RuleSet.put(name.substring(2), width / RulesFromFile.get(name).getAsDouble());
                        break;
                    case '1':
                        RuleSet.put(name.substring(2), height / RulesFromFile.get(name).getAsDouble());
                        break;
                    case '2':
                        RuleSet.put(name.substring(2), param / RulesFromFile.get(name).getAsDouble());
                        break;
                    case '3':
                        RuleSet.put(name.substring(2), RulesFromFile.get(name).getAsDouble());
                        break;
                }
            }
        }
        return RuleSet;
    }


    public static GameRules get(String filepath) {
        return GRulez = new GameRules(filepath);
    }

    private GameRules(String filepath) {
        if (filepath.equals("null")) {
            RulesFromFile = getStartedConfiguration(getClass(), "/config.json");
        } else {
            RulesFromFile = getStartedConfiguration(getClass(), filepath + "&config.json");
        }
    }

    public static HashMap<String, JsonElement> getStartedConfiguration(Class app, String path) {
        HashMap<String, JsonElement> hm = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry: Depacker.getStarted(app, path).entrySet()) {
            hm.put(entry.getKey(), entry.getValue());
        }

        return hm;
    }
}