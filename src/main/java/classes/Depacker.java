package classes;


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
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 21.06.2017.
 */
public class Depacker {
    private static final String pathname = "C:\\Users\\HP\\Documents\\OP_GAME_SYS\\";

    public static void getStartedConnection(Class app) {
        String path = app.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);

        /*try {
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
        }*/
    }

    public static HashMap<String, JsonElement> getStartedConfiguration(Class app, String path) {

        HashMap<String, JsonElement> hm = new HashMap<>();

        try {
            JsonParser JP = new JsonParser();

            JsonElement config = JP.parse(new JsonReader(new InputStreamReader(app.getResourceAsStream("/preinstallations" + path))));
            JsonObject jo = config.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry: jo.entrySet()) {
                hm.put(entry.getKey(), entry.getValue());
            }

        } catch (Exception e) {
            System.out.println("game .jar damaged");
            e.printStackTrace();
        }

        return hm;

        //File file = new File(pathname);
        //file.mkdir();
    }

    public static Level getStartedLevel(Class app) {

        JsonObject jo = new JsonObject();

        try {
            JsonParser JP = new JsonParser();

            JsonElement config = JP.parse(new JsonReader(new InputStreamReader(app.getResourceAsStream("/preinstallations/test.upson")))); //pathname + "games\\test.upson"
            jo = config.getAsJsonObject();

        } catch (Exception e) {
            System.out.println("folder malformed");
            e.printStackTrace();
        }

        return new Level(jo.get("name").getAsString(), jo.get("height").getAsInt(), jo.get("width").getAsInt(), jo.get("level_pack").getAsJsonObject());

        //File file = new File(pathname);
        //file.mkdir();
    }
}
