package classes.NoControllers;

/**
 * Created by User on 18.03.2017.
 */
public class Const {
    public String name;
    public double value;

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Const(String name, double value) {
        this.name = name;
        this.value = value;
    }
}
