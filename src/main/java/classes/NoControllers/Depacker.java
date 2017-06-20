package classes.NoControllers;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.naming.Context;
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
            FileInputStream serviceAccount = new FileInputStream(pathname + "firebase_loc\\ultimateplatformer-firebase-adminsdk-0aory-7936ac32f2.json");
            //FirebaseOptions options = new FirebaseOptions.Builder()
            //.setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
            //.setDatabaseUrl("https://ultimateplatformer.firebaseio.com/")
            //.build();

            //FirebaseApp.initializeApp(options);
            //FirebaseDatabase.getInstance().getReference().child("sample").setValue("sample text");
        } catch (FileNotFoundException e) {
            System.out.println("folder malformed");
            e.printStackTrace();
        }
    }

    public static HashMap<String, Double> getStartedConfiguration(Class app) {

        HashMap<String, Double> hm = new HashMap<>();

        try {
            //FileInputStream serviceAccount = new FileInputStream(pathname + "config.json");

            JsonParser JP = new JsonParser();

            JsonElement config = JP.parse(new FileReader(pathname + "config.json"));
            JsonObject jo = config.getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry: jo.entrySet()) {
                hm.put(entry.getKey(), entry.getValue().getAsDouble());
            }
            System.out.println(hm.toString());

        } catch (FileNotFoundException e) {
            System.out.println("folder malformed");
            e.printStackTrace();
        }

        return hm;

        //File file = new File(pathname);
        //file.mkdir();
    }
}
