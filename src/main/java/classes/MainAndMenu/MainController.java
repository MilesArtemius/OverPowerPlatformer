package classes.MainAndMenu;

import classes.NeoButton;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;


public class MainController {

    @FXML
    private VBox vBox;

    @FXML
    private TilePane pane;

    @FXML
    private NeoButton new_game_button;

    @FXML
    private NeoButton continue_button;

    @FXML
    private NeoButton redactor_button;

    @FXML
    private Hyperlink link;

    private void continueGame() {
        try {

            //scrrenDownButton(continue_button, "C:\\OP_GAME_SYS\\games\\continueButton.png");

            Parent root = FXMLLoader.load(getClass().getResource("/layouts/game_layout.fxml"));

            Stage stage = (Stage) pane.getScene().getWindow();

            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startNewGame() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/level_chosen_layout.fxml"));

            Stage stage = (Stage) pane.getScene().getWindow();

            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void somethingElse() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/editor_layout.fxml"));

            Stage stage = new Stage();

            Scene scene = new Scene(root, pane.getScene().getWindow().getWidth(), pane.getScene().getWindow().getHeight());

            stage.setScene(scene);
            stage.setTitle("Ultimate Level Editor");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        pane.setBackground(new Background(new BackgroundImage(new Image(getClass().getResource("/utils/pictures/TheGreenSun.jpg").toString()),
                null, null, null,
                new BackgroundSize(1, 1, true, true, false, false))));

        vBox.spacingProperty().bind(pane.heightProperty().divide(vBox.getChildren().size() * 2));

        new_game_button.setHeight(64);
        new_game_button.setText("NEW GAME");
        new_game_button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startNewGame());

        continue_button.setHeight(64);
        continue_button.setText("CONTINUE");
        continue_button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> continueGame());

        redactor_button.setHeight(64);
        redactor_button.setText("REDACTOR");
        redactor_button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> somethingElse());
        /*link.setOnAction(event -> {
            try {
                Desktop.getDesktop().browse(new URI("https://firebase.google.com/"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/
    }

    public void scrrenDownButton(NeoButton button, String pathToFile) {
        WritableImage image = button.snapshot(null, null);
        try {
            File file = new File(pathToFile);
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            //System.out.println(n.getHeight());
            ImageIO.write(
                    bImage,
                    "png",
                    file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


/*<StackPane prefHeight="150.0" prefWidth="200.0">
            <children>
                <Text text="Text">
                    <font>
                        <Font name="Veranda" size="24.0" />
                    </font>
                </Text>
            </children>
         </StackPane>*/