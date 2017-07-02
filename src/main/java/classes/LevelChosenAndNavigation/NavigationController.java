package classes.LevelChosenAndNavigation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

import static classes.Depacker.pathname;

/**
 * Created by HP on 02.07.2017.
 */
public class NavigationController {
    private static final String finalPath = pathname + "games\\preinstalled_levels";

    @FXML
    private Accordion ap1;

    @FXML
    private Accordion ap2;

    @FXML
    private Text text1;

    @FXML
    private Text text2;

    @FXML
    private SplitPane pane;

    @FXML
    public void initialize() {

        text1.setText("Pre-installed levels");
        text1.setFont(Font.font(34));
        text2.setFont(Font.font(34));
        text2.setText("User levels");

        ArrayList<TitledPane> tp = new ArrayList<>();

        File folder = new File(finalPath);

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            VBox vBox = new VBox();

            File[] newFile = new File(file.getAbsolutePath() + "\\levels").listFiles();
            for (File levelFile : newFile) {
                Button button = new Button(levelFile.getName());
                button.setPrefWidth(Double.MAX_VALUE);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/layouts/game_layout.fxml"));

                            Stage stage = (Stage) pane.getScene().getWindow();

                            root.setUserData(levelFile.getAbsolutePath());

                            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

                            stage.setScene(scene);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                vBox.getChildren().add(button);
            }
            tp.add(new TitledPane(file.getName(), vBox));
        }

        for (int i = 0; i < tp.size(); i++) {
            System.out.println(tp.get(i).getContent());
        }

        ap1.getPanes().addAll(tp);
    }
}
