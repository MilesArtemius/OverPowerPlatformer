package classes.ResizableCanvasStuff;

import classes.ResizableCanvas;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    private StackPane stackPane;

    @FXML
    private ResizableCanvas resizableCanvas;

    @FXML
    private Button button;

    private void startGame() {
        Scene scene = stackPane.getScene();

        //Stage stage = (Stage) stackPane.getScene().getWindow();

        System.out.println(scene);

        scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
            if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (keyEvent.getCode()) {
                    case UP:
                        resizableCanvas.LUP.jump(2);
                        break;
                    case DOWN:
                        resizableCanvas.LUP.jump(1);
                        break;
                    case RIGHT:
                        resizableCanvas.LUP.move(1);
                        break;
                    case LEFT:
                        resizableCanvas.LUP.move(2);
                        break;
                }
            } else {
                switch (keyEvent.getCode() ) {
                    case RIGHT:
                    case LEFT:
                        resizableCanvas.LUP.move(0);
                        break;
                    case UP:
                    case DOWN:
                        resizableCanvas.LUP.jump(0);
                        break;
                }
            }
        });

        button.setVisible(false);

        resizableCanvas.LUP.start();
    }

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty widthProperty = stackPane.widthProperty();
        ReadOnlyDoubleProperty heightProperty = stackPane.heightProperty();
        resizableCanvas.widthProperty().bind(widthProperty);
        resizableCanvas.heightProperty().bind(heightProperty);

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startGame());
    }
}

