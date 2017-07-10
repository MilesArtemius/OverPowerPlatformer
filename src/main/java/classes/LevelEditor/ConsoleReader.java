package classes.LevelEditor;

import classes.Additionals.CommandList;
import com.google.api.client.json.Json;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by HP on 09.07.2017.
 */
public class ConsoleReader {
    EditorController father;
    TextArea console;

    CommandList commandList;

    JsonObject config;

    public ConsoleReader(EditorController father) {
        this.father = father;
        this.console = father.console;
        this.commandList = CommandList.get();
        try {
            String fileContent = new String(Files.readAllBytes(father.config.toPath()));
            JsonParser parser = new JsonParser();
            config = parser.parse(fileContent).getAsJsonObject();
        } catch (Exception e) {
            config = new JsonObject();
            System.out.println("configuration is null");
        }
        this.console.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                readConsole(console.getParagraphs().get(console.getParagraphs().size() - 1).toString());
            }
        });
    }

    public void readConsole(String input) {
        String [] inputArray = input.split(" ");
        //System.out.println(Arrays.toString(inputArray));
        if (inputArray[0].equals(commandList.getCommands().get("set").getValue())) {
            setCommand(inputArray);
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

    /*private void writeToFile(File file, String value) {
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.append(value);
        } catch (Exception e) {
            System.out.println("was not written");
        }
    }*/

    private void setCommand(String[] input) {
        switch (input[1]) {
            case "const":
                //writeToFile(father.config, (configWriteCount == 0)?(""):(","));
                //configWriteCount++;
                JsonParser parser = new JsonParser();
                JsonObject o = parser.parse("{\"" + input[2] + "\": \"" + input[3] + "\"}").getAsJsonObject();
                writeToFile(father.config, o);
        }
    }
}