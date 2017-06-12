package classes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;


public class MainController {

    ResizableCanvas gc = new ResizableCanvas();

    private void setScene() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/game_layout.fxml"));
            StackPane pane = (StackPane) loader.load();

            Stage stage = OPApp.getPrimaryStage();

            pane.setPrefSize(stage.getWidth(), stage.getHeight());
            Scene scene = new Scene(pane);

            scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
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
            });

            stage.setScene(scene);
            //stage.setFullScreen(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button button;

    @FXML
    public void initialize(){
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            setScene();
    });
}
}