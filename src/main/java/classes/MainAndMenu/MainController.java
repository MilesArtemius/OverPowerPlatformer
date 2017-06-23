package classes.MainAndMenu;

import classes.ResizableCanvas;
import classes.StructureClasses.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainController {

    ResizableCanvas gc = new ResizableCanvas();
    Level level;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button button;

    private void setScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/game_layout.fxml"));

            Stage stage = (Stage) anchorPane.getScene().getWindow();

            System.out.println(stage);

            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

            stage.setScene(scene);

            //gc.start();

            /*scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    switch (keyEvent.getCode()) {
                        case UP:
                            gc.jump(1);
                            break;
                        case RIGHT:
                            gc.move(1);
                            break;
                        case LEFT:
                            gc.move(2);
                            break;
                    }
                } else {
                    switch (keyEvent.getCode() ) {
                        case RIGHT:
                        case LEFT:
                            gc.move(0);
                            break;
                        case UP:
                            gc.jump(0);
                            break;
                    }
                }
            });*/

            //stage.setFullScreen(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> setScene());
    }
}