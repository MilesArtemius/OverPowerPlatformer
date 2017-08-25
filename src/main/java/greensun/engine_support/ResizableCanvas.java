package greensun.engine_support;

import javafx.scene.canvas.Canvas;

import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by User on 18.03.2017.
 */
public abstract class ResizableCanvas extends Canvas {

    public abstract void redraw();
        //LUP.redrawCanvas();

    public ResizableCanvas() {
        widthProperty().addListener(evt -> redraw());
        heightProperty().addListener(evt -> redraw());
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
