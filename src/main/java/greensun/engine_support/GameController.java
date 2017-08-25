package greensun.engine_support;

import greensun.platformer_engine.PlatformerUploader;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class GameController {
    PlatformerUploader LUP;

    @FXML
    private StackPane stackPane;

    @FXML
    private Button button;

    private void startGame() {
        System.out.println(stackPane.getUserData());
        LUP.setSource((String) stackPane.getUserData());

        LUP.redrawCanvas();

        Scene scene = stackPane.getScene();

        /*PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(1000);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(100);
        scene.setCamera(camera);*/

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
        LUP = new PlatformerUploader();

        stackPane.getChildren().add(LUP.getStructure());
        stackPane.getChildren().add(LUP.getSource());
        stackPane.getChildren().add(LUP.getDecoration());

        button.toFront();

        ReadOnlyDoubleProperty widthProperty = stackPane.widthProperty();
        ReadOnlyDoubleProperty heightProperty = stackPane.heightProperty();
        LUP.getStructure().widthProperty().bind(widthProperty);
        LUP.getStructure().heightProperty().bind(heightProperty);
        LUP.getSource().widthProperty().bind(widthProperty);
        LUP.getSource().heightProperty().bind(heightProperty);
        LUP.getDecoration().widthProperty().bind(widthProperty);
        LUP.getDecoration().heightProperty().bind(heightProperty);

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> startGame());
    }
}

