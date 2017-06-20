package classes.NoControllers;


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

    private static HashMap<String, Double> fromFile;
    private static HashMap<String, Double> RuleSet;

    public HashMap<String, Double> getRulez(double w, double h) {
        if ((w != width) || (h != height)) {
            System.out.println("-=-=-=-=REDRAWED=-=-=-=-");
            width = w;
            height = h;
            for (String name : fromFile.keySet()) { // убрать с применением единой системы отсчёта.
                switch (name) {
                    case "BASIC_STATE_X":
                        RuleSet.put(name, width / fromFile.get(name));
                        break;
                    case "BASIC_STATE_Y":
                        RuleSet.put(name, height / fromFile.get(name));
                        break;
                    case "SPEED":
                        RuleSet.put(name, fromFile.get(name));
                        break;
                    case "GRAVITY":
                        RuleSet.put(name, fromFile.get(name));
                        break;
                    case "DELAY":
                        RuleSet.put(name, fromFile.get(name));
                        break;
                    case "BLOCK_SIZE":
                        RuleSet.put(name, width / fromFile.get(name));
                        break;
                    case "MULTIPLIER":
                        RuleSet.put(name, fromFile.get(name));
                        break;
                    case "MOVEMENT":
                        RuleSet.put(name, width / fromFile.get(name));
                        break;
                }
            }
        }
        return RuleSet;
    }

    public static GameRulez get() {
        return GRulez = new GameRulez();
    }

    private  GameRulez() {
        fromFile = Depacker.getStartedConfiguration(getClass());
        RuleSet = new HashMap<>();
        System.out.println(fromFile.toString());
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