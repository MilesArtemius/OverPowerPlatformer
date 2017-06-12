package classes.NoControllers;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 11.03.2017.
 */
public class GameRulez {
    private static GameRulez GRulez;
    static double width;
    static double height;

    private List<Const> RuleSet;

    public static GameRulez get(double w, double h) {
        width = w;
        height = h;
        return GRulez = new GameRulez();
    }

    private GameRulez() {
        RuleSet = new ArrayList<>();

            RuleSet.add(new Const("BASIC_STATE_X", width/5));
            RuleSet.add(new Const("BASIC_STATE_Y", height/3*2));
            RuleSet.add(new Const("SPEED", 40));
            RuleSet.add(new Const("GRAVITY", 10));
            RuleSet.add(new Const("DELAY", 15));
            RuleSet.add(new Const("BLOCK_SIZE", width/64));
            RuleSet.add(new Const("MULTIPLIER", 2));
            RuleSet.add(new Const("MOVEMENT", width/275));
    }

    public List<Const> getRulez() {
        return RuleSet;
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