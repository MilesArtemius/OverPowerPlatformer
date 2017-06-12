package classes;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.FirebaseDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * Created by User on 11.06.2017.
 */
public class OPApp extends Application {
    private static Stage stage;

    //

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\User\\Documents\\SECRET FILE FOR OP\\ultimateplatformer-firebase-adminsdk-0aory-7936ac32f2.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                .setDatabaseUrl("https://ultimateplatformer.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);

        FirebaseDatabase.getInstance().getReference().child("sample").setValue("sample text");

        Parent root = FXMLLoader.load(getClass().getResource("/main_layout.fxml"));
        copyPrimaryStage(primaryStage);
        //primaryStage.setFullScreen(true);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Ultimate Platformer");
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    private void copyPrimaryStage(Stage pStage) {
        OPApp.stage = pStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
