package classes.Additionals;

import classes.MainAndMenu.Depacker;

import java.util.HashMap;

/**
 * Created by HP on 09.07.2017.
 */
public class CommandList {
    private HashMap<String, Command> commands;
    private static CommandList those;
    public static CommandList get() {
        return those = new CommandList();
    }
    private CommandList() {
        if (those == null) {
            this.commands = Depacker.getCommands(this.getClass());
        }
    }
    public HashMap<String, Command> getCommands() {
        return this.commands;
    }

    public static class Command {
        String value;
        String description;

        public Command(String value, String description) {
            this.value = value;
            this.description = description;
        }

        public String getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }
    }
}
