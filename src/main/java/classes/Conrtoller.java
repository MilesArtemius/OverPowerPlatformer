package classes;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class Conrtoller {

    @FXML
    private StackPane pane;

    @FXML
    private ResizableCanvas resizableCanvas;

    @FXML
    private void initialize() {
        ReadOnlyDoubleProperty widthProperty = pane.widthProperty();
        ReadOnlyDoubleProperty heightProperty = pane.heightProperty();
        resizableCanvas.widthProperty().bind(widthProperty);
        resizableCanvas.heightProperty().bind(heightProperty);
    }
}

