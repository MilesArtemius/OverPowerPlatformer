package classes;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import classes.NoControllers.Const;
import classes.NoControllers.GameRulez;

import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by User on 18.03.2017.
 */
public class ResizableCanvas extends Canvas {

    GameRulez gr;
    List<Const> gm;
    double x = 0;
    double y = 0;
    double t = 1;
    static int AT = 0;
    static AnimationTimer at;
    private static GraphicsContext gc;
    static boolean AntiJumper = false;
    static int MOVEMENTER = 0;
    static int MOVEMENTER2 = 0;

    public double getRule(String rule) {
        if (gm != null) {
            for (Const c : gm) {
                if (c.getName().equals(rule)) {
                    return c.getValue();
                }
            }
        } else {
            return 0;
        }
        return 0;
    }

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
            gr = GameRulez.get(getWidth(), getHeight());
            gm = gr.getRulez();

            x = getRule("BASIC_STATE_X");//gm.getRule("BASIC_STATE_X")
            y = getRule("BASIC_STATE_Y");//gm.getRule("BASIC_STATE_Y")
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
                    gc.setFill(Color.BLACK);
                    gc.fillRect(x, y, getRule("BLOCK_SIZE"), getRule("BLOCK_SIZE"));

                    //
                    gc.setFill(Color.BLACK);
                    gc.fillRect(Standartify(100, 'w'), Standartify(200, 'h'), getRule("BLOCK_SIZE"), getRule("BLOCK_SIZE"));
                    gc.fillRect(Standartify(200, 'w'), Standartify(200, 'h'), getRule("BLOCK_SIZE"), getRule("BLOCK_SIZE"));

                    getLevel("C:\\Users\\User\\Documents\\UP levels folder.zip");

                    //
                    if (MOVEMENTER == 1) {
                        y = y - (getRule("SPEED") * t - getRule("GRAVITY") * t * t / 2) / getRule("MULTIPLIER");
                        t += 0.3;
                        if (y >= getRule("BASIC_STATE_Y")) {
                            t = 1;
                            y = getRule("BASIC_STATE_Y");
                            if (AntiJumper) {
                                MOVEMENTER = 0;
                                AntiJumper = false;
                            }
                        }
                    }
                    //
                    switch (MOVEMENTER2) {
                        case 1:
                            x += getRule("MOVEMENT");
                            //if (x >= (sSize.width/3*2+AT)) {
                            gc.translate(-getRule("MOVEMENT"), 0);
                            AT -= getRule("MOVEMENT");
                            //}

                            break;
                        case 2:
                            x -= getRule("MOVEMENT");
                            gc.translate(+getRule("MOVEMENT"), 0);
                            AT += getRule("MOVEMENT");
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
