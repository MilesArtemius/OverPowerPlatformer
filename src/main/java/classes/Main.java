package classes;

import classes.NoControllers.Depacker;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.FirebaseDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by User on 11.06.2017.
 */
public class Main extends Application {
    private static Stage stage;

    //

    @Override
    public void start(Stage primaryStage) throws Exception {

        Depacker.getStartedConnection(getClass());

        Parent root = FXMLLoader.load(getClass().getResource("/layouts/main_layout.fxml"));
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
        Main.stage = pStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
