package classes;

import classes.ResizableCanvasStuff.LevelUploader;
import javafx.scene.canvas.Canvas;

import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by User on 18.03.2017.
 */
public class ResizableCanvas extends Canvas {

    public LevelUploader LUP = new LevelUploader(this);

    public double Standartify(int parameter, char definitor) {
        if (definitor == 'w') {
            return (double) parameter/602*getWidth();
        } else {
            return (double) parameter/500*getHeight();
        }
    }

    public ResizableCanvas() {
        widthProperty().addListener(evt -> redraw());
        heightProperty().addListener(evt -> redraw());
    }

    private void redraw() {
        LUP.redrawCanvas();
    }

    public void getLevel(String pathTo) {
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(pathTo)))
        {
            ZipEntry entry;
            String name;
            long size;
            while((entry = zin.getNextEntry())!=null){

                name = entry.getName(); // получим название файла
                size=entry.getSize();  // получим его размер в байтах
                System.out.printf("Название: %s \t размер: %d \n", name, size);
            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
