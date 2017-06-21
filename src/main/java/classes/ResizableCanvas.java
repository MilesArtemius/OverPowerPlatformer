package classes;

import classes.NoControllers.Block;
import classes.NoControllers.OuterFunctions;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import classes.NoControllers.GameRulez;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by User on 18.03.2017.
 */
public class ResizableCanvas extends Canvas {

    GameRulez gr = GameRulez.get(true);
    HashMap<String, Double> gm;
    double x = 0;
    double y = 0;
    double t = 1;
    static int AT = 0;
    static AnimationTimer at;
    private static GraphicsContext gc;
    static boolean AntiJumper = false;
    static int MOVEMENTER = 0;
    static int MOVEMENTER2 = 0;

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
        if ((getWidth() > 0) && (getHeight() > 0)) {
            gm = gr.getRulez(getWidth(), getHeight());
            System.out.println(gm.toString());

            x = gm.get("BASIC_STATE_X");
            y = gm.get("BASIC_STATE_Y");
        }

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        // Секция объявления фона: на канвах рисуется прямоугольник определённого фона.
        gc.setFill(Color.WHEAT);
        gc.fillRect(0, 0, getWidth(), getHeight());

        //gc.translate(AT, 0);

        //System.out.println("TRANSLATED " + AT);
        //System.out.println();

        if (at == null) {
            at = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    System.out.println(AT);
                    System.out.println(x);
                    System.out.println();

                    // Секция объявления фона: на канвах рисуется прямоугольник определённого фона.
                    gc.setFill(Color.WHEAT);
                    gc.fillRect(-AT, 0, getWidth() - AT, getHeight());

                    //
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("sample").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), x, y); //0 - bad, 1 - good;

                    //
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("floor").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), Standartify(100, 'w'), Standartify(200, 'h'));
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("floor").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), Standartify(200, 'w'), Standartify(200, 'h'));

                    //
                    if (MOVEMENTER == 1) {
                        y = y - (gm.get("SPEED") * t - gm.get("GRAVITY") * t * t / 2) / gm.get("MULTIPLIER");
                        t += 0.3;
                        if (y >= gm.get("BASIC_STATE_Y")) {
                            t = 1;
                            y = gm.get("BASIC_STATE_Y");
                            if (AntiJumper) {
                                MOVEMENTER = 0;
                                AntiJumper = false;
                            }
                        }
                    }
                    //
                    switch (MOVEMENTER2) {
                        case 1:
                            x += gm.get("MOVEMENT");
                            //if (x >= (sSize.width/3*2+AT)) {
                            gc.translate(-gm.get("MOVEMENT"), 0);
                            AT -= gm.get("MOVEMENT");
                            //}

                            break;
                        case 2:
                            x -= gm.get("MOVEMENT");
                            gc.translate(+gm.get("MOVEMENT"), 0);
                            AT += gm.get("MOVEMENT");
                            break;
                    }
                }
            };
        }
    }

    public void jump(int moves) {
        if (!(moves == 0)) {
            MOVEMENTER = moves;
        } else {
            AntiJumper = true;
        }
        at.start();
    }

    public void move(int moves) {
        MOVEMENTER2 = moves;
        at.start();
    }

    public void start() {
        at.start();
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
