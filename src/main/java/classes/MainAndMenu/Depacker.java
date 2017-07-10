package classes.MainAndMenu;


import classes.Additionals.CommandList;
import classes.StructureClasses.Level;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 21.06.2017.
 */
public class Depacker {
    public static final String pathname = "C:\\OP_GAME_SYS\\";

    public static void getStartedConnection(Class app) {
        String path = app.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);

        try {
            Path temo = Files.createTempFile("resource", ".json");
            Files.copy(app.getResourceAsStream("/preinstallations/connector.json"), temo, StandardCopyOption.REPLACE_EXISTING);
            FileInputStream serviceAccount = new FileInputStream(temo.toFile());
            FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
            .setDatabaseUrl("https://ultimateplatformer.firebaseio.com/")
            .build();

            FirebaseApp.initializeApp(options);
            FirebaseDatabase.getInstance().getReference().child("sample").setValue("epic fail");
        } catch (Exception e) {
            System.out.println("game .jar damaged");
            e.printStackTrace();
        }
    }

    public static HashMap<String, JsonElement> getStartedConfiguration(Class app, String path) {

        HashMap<String, JsonElement> hm = new HashMap<>();

        try {
            JsonParser JP = new JsonParser();
            JsonElement config;
            if (path.charAt(0) == '/') {
                config = JP.parse(new JsonReader(new InputStreamReader(app.getResourceAsStream("/preinstallations" + path))));
            } else {
                config = JP.parse(new JsonReader(new InputStreamReader(Files.newInputStream(Paths.get(path.substring(0, path.lastIndexOf("\\levels") + 1) + path.substring(path.lastIndexOf('&') + 1))))));
            }
            JsonObject jo = config.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry: jo.entrySet()) {
                hm.put(entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            System.out.println("game .jar damaged");
            e.printStackTrace();
        }

        return hm;
    }

    public static Level getStartedLevel(Class app, String filepath) {

        JsonObject jo = new JsonObject();

        try {
            JsonParser JP = new JsonParser();
            JsonElement config;

            if (!filepath.equals("null")) {
                config = JP.parse(new JsonReader(new InputStreamReader(Files.newInputStream(Paths.get(filepath)))));
            } else {
                config = JP.parse(new JsonReader(new InputStreamReader(app.getResourceAsStream("/preinstallations/null.upson"))));
            }

            jo = config.getAsJsonObject();

        } catch (Exception e) {
            System.out.println("folder malformed");
            e.printStackTrace();
        }

        return new Level(filepath, jo.get("name").getAsString(), jo.get("height").getAsInt(), jo.get("width").getAsInt(), jo.get("level_pack").getAsJsonObject());
    }

    public static HashMap<String, CommandList.Command> getCommands(Class app) {

        HashMap<String, CommandList.Command> hm = new HashMap<>();

        try {
            JsonParser JP = new JsonParser();
            JsonElement config = JP.parse(new JsonReader(new InputStreamReader(app.getResourceAsStream("/utils/commands.json"))));
            JsonObject jo = config.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry: jo.entrySet()) {
                hm.put(entry.getKey(), new CommandList.Command(entry.getValue().getAsJsonObject().get("value").getAsString(), entry.getValue().getAsJsonObject().get("description").getAsString()));
            }

        } catch (Exception e) {
            System.out.println("commands list damaged");
            e.printStackTrace();
        }

        return hm;
    }
}
