package classes.NoControllers;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
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

        try {
            FileInputStream serviceAccount = new FileInputStream(app.getResource("/preinstallations/connector.json").getFile());
            //FirebaseOptions options = new FirebaseOptions.Builder()
            //.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
            //.setDatabaseUrl("https://ultimateplatformer.firebaseio.com/")
            //.build();

            //FirebaseApp.initializeApp(options);
            //FirebaseDatabase.getInstance().getReference().child("sample").setValue("sample text");
        } catch (FileNotFoundException e) {
            System.out.println("game .jar damaged");
            e.printStackTrace();
        }
    }

    public static HashMap<String, JsonElement> getStartedConfiguration(Class app, String path) {

        HashMap<String, JsonElement> hm = new HashMap<>();

        try {
            JsonParser JP = new JsonParser();

            JsonElement config = JP.parse(new FileReader(app.getResource("/preinstallations" + path).getFile()));
            JsonObject jo = config.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry: jo.entrySet()) {
                hm.put(entry.getKey(), entry.getValue());
            }

        } catch (FileNotFoundException e) {
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

            JsonElement config = JP.parse(new FileReader(pathname + "games\\test.upson"));
            jo = config.getAsJsonObject();

        } catch (FileNotFoundException e) {
            System.out.println("folder malformed");
            e.printStackTrace();
        }

        return new Level(jo.get("name").getAsString(), jo.get("height").getAsInt(), jo.get("width").getAsInt(), jo.get("level_pack").getAsJsonObject());

        //File file = new File(pathname);
        //file.mkdir();
    }
}
