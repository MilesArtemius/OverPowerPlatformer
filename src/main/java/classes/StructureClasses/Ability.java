package classes.StructureClasses;

import com.google.gson.JsonObject;

public class Ability {
    String name;

    int level;
    boolean isEndless;
    int consumesPerUse;

    int damageDealing;
    boolean nonHostile;

    int area;

    boolean isActive;

    public Ability(String key, JsonObject ability) {
        this.name = key;
        this.area = ability.get("area").getAsInt();
        this.isActive = ability.get("active").getAsBoolean();

        if (!ability.get("is_endless").getAsBoolean()) {
            try {
                this.level = ability.get("level").getAsInt();
                this.consumesPerUse = ability.get("consumes").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.isEndless = false;
        } else {
            this.isEndless = true;
            this.level = 1;
            this.consumesPerUse = 0;
        }

        if (ability.get("hostile").getAsBoolean()) {
            try {
                this.damageDealing = ability.get("damage").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.nonHostile = false;
        } else {
            this.damageDealing = 0;
            this.nonHostile = true;
        }
    }
}
