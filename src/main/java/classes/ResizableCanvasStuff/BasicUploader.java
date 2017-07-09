package classes.ResizableCanvasStuff;

import classes.ResizableCanvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by HP on 02.07.2017.
 */
public class BasicUploader {
    public ResizableCanvas source;
    public GraphicsContext gc;

    public BasicUploader(ResizableCanvas resizableCanvas) {
        this.source = resizableCanvas;
        this.gc = resizableCanvas.getGraphicsContext2D();
    }

    public void redrawCanvas() {

    }
}
