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
    LevelUploader LUP;

    @FXML
    private StackPane stackPane;

    @FXML
    private Button button;

    private void startGame() {
        System.out.println(stackPane.getUserData());
        LUP.setSource((String) stackPane.getUserData());

        Scene scene = stackPane.getScene();

        System.out.println(scene);

        scene.addEventHandler(KeyEvent.ANY, keyEvent -> {
            if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                switch (keyEvent.getCode()) {
                    case UP:
                        LUP.jump(2);
                        break;
                    case DOWN:
                        LUP.jump(1);
                        break;
                    case RIGHT:
                        LUP.move(1);
                        break;
                    case LEFT:
                        LUP.move(2);
                        break;
                }
            } else {
                switch (keyEvent.getCode() ) {
                    case RIGHT:
                    case LEFT:
                        LUP.move(0);
                        break;
                    case UP:
                    case DOWN:
                        LUP.jump(0);
                        break;
                }
            }
        });

        button.setVisible(false);

        LUP.start();
    }

    @FXML
    public void initialize() {
        ResizableCanvas resizableCanvas = new ResizableCanvas();
        stackPane.getChildren().add(resizableCanvas);

        LUP = new LevelUploader(resizableCanvas);
        resizableCanvas.setUploader(LUP);

        button.toFront();

        ReadOnlyDoubleProperty widthProperty = stackPane.widthProperty();
        ReadOnlyDoubleProperty heightProperty = stackPane.heightProperty();
        resizableCanvas.widthProperty().bind(widthProperty);
        resizableCanvas.heightProperty().bind(heightProperty);

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startGame());
    }
}

