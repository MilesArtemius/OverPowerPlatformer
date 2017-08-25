package greensun.LevelEditor;

import greensun.Additionals.CommandList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

/**
 * Created by HP on 09.07.2017.
 */
public class ConsoleReader {
    EditorController father;
    TextArea console;
    TextField writableConsole;

    CommandList commandList;

    JsonObject config;

    public ConsoleReader(EditorController father) {
        this.father = father;
        this.console = father.console;
        this.writableConsole = father.writableConsole;
        writeConsole(true, false, father.folder.getAbsolutePath());
        this.commandList = CommandList.get();
        try {
            String fileContent = new String(Files.readAllBytes(father.config.toPath()));
            JsonParser parser = new JsonParser();
            config = parser.parse(fileContent).getAsJsonObject();
        } catch (Exception e) {
            config = new JsonObject();
            System.out.println("configuration is null");
        }
        this.writableConsole.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                writeConsole(false, true, writableConsole.getText());
                readConsole(writableConsole.getText());
                writableConsole.clear();
            }
        });
    }

    public void writeConsole(boolean isInit, boolean userInput, String value) {
        console.setEditable(true);
        if (isInit) {
            console.appendText("Console window initialized!" + "\n" + "Path to level folder: " + value + "\n" + "\n");
        } else if (userInput) {
            console.appendText("\n" + "User commands: " + value + "\n");
        } else {
            console.appendText(value + "\n");
        }
        console.setEditable(false);
    }

    public void readConsole(String input) {
        String [] inputArray = input.split(" ");
        //System.out.println(Arrays.toString(inputArray));
        if (inputArray[0].equals(commandList.getCommands().get("set").getValue())) {
            setCommand(inputArray);
        } else if (inputArray[0].equals(commandList.getCommands().get("help").getValue())) {
            helpCommand(inputArray);
        }
    }

    private void writeToFile(File file, JsonElement value) {
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, false)));
            for (Map.Entry<String, JsonElement> entry: value.getAsJsonObject().entrySet()) {
                config.add(entry.getKey(), entry.getValue());
            }
            System.out.println(config.toString());
            printWriter.println(config.toString());
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCommand(String[] input) {
        switch (input[1]) {
            case "const":
                JsonParser parser = new JsonParser();
                JsonObject o = parser.parse("{\"" + input[2] + "\": \"" + input[3] + "\"}").getAsJsonObject();
                writeToFile(father.config, o);
        }
    }

    private void helpCommand(String[] input) {
        for (String name: commandList.getCommands().keySet()) {
            if (input[1].equals(name)) {
                writeConsole(false, false, commandList.getCommands().get(name).toString());
            }
        }
    }
}