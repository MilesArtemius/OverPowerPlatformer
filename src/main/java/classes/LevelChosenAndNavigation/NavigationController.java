package classes.LevelChosenAndNavigation;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
    public void initialize() {

        text1.setText("Pre-installed levels");
        text2.setText("User levels");

        ArrayList<TitledPane> tp = new ArrayList<>();

        File folder = new File(finalPath);

        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            VBox vBox = new VBox();

            File[] newFile = new File(file.getAbsolutePath() + "\\levels").listFiles();
            for (File levelFile : newFile) {
                vBox.getChildren().add(new Button(levelFile.getName()));
            }
            tp.add(new TitledPane(file.getName(), vBox));
        }

        for (int i = 0; i < tp.size(); i++) {
            System.out.println(tp.get(i).getContent());
        }

        ap1.getPanes().addAll(tp);
    }
}
