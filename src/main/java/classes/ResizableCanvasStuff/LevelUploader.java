package classes.ResizableCanvasStuff;


import classes.Depacker;
import classes.OuterFunctions;
import classes.ResizableCanvas;
import classes.StructureClasses.GameRulez;
import classes.StructureClasses.Level;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class LevelUploader {
    GameRulez gr = GameRulez.get(true); // current level & rules.
    HashMap<String, Double> gm; // library of level rules.
    double x = 0; // x coordinate of protagonist.
    double y = 0; // x coordinate of protagonist.
    double t = 1; // time for leap.
    double AT = 0; // translation from the 0,0.
    AnimationTimer at; // timer of animation.
    boolean AntiJumper = false; // preventer of multiple jump.
    int MOVEMENTER = 0; // move variable.
    int MOVEMENTER2 = 0; // jump variable.
    Level level; // level map.
    Double param; // min(ScreenWidth, ScreenHeight).
    WritableImage[] wim = new WritableImage [9]; // images around the protagonist.
    Canvas structure; // level canvas.
    double currentTranslationX = 0; // AT / ScreenWidth.
    double currentTranslationY; // AT / ScreenHeight.
    static int multiplier = 0; // number of currenttranslations from 0,0.
    ResizableCanvas source;
    GraphicsContext gc;

    public LevelUploader(ResizableCanvas resizableCanvas) {
        this.source = resizableCanvas;
        this.gc = resizableCanvas.getGraphicsContext2D();
    }

    public void redrawCanvas() {
        if ((source.getWidth() > 0) && (source.getHeight() > 0)) {
            GraphicsContext gc = source.getGraphicsContext2D();

            param = ((source.getHeight() > source.getWidth()) ? (source.getWidth()) : (source.getHeight()));
            gm = gr.getRulez(source.getWidth(), source.getHeight(), param);
            System.out.println(gm.toString());

            x = gm.get("BASIC_STATE_X");
            y = gm.get("BASIC_STATE_Y");

            level = Depacker.getStartedLevel(getClass());

            gc.clearRect(0, 0, source.getWidth(), source.getHeight());
            gc.setFill(Color.WHEAT);
            gc.fillRect(0, 0, source.getWidth(), source.getHeight());


            structure = new Canvas(level.Width * gm.get("BLOCK_SIZE"), level.Height * gm.get("BLOCK_SIZE"));
            GraphicsContext str_gc = structure.getGraphicsContext2D();
            str_gc.clearRect(0, 0, source.getWidth(), source.getHeight());


            for (int i = 0; i < level.level.length; i++) {
                for (int j = 0; j < level.level[i].length; j++) {
                    try {
                        str_gc.drawImage(OuterFunctions.scale(level.level[i][j].texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), gm.get("BLOCK_SIZE") * i, gm.get("BLOCK_SIZE") * j);
                    } catch (NullPointerException npe) {
                        npe.getMessage();
                    }
                }
            }

            //str_gc.translate(3*getWidth(), 0);


            //System.out.println(wim[0].getWidth() / getWidth() + "\n" + wim[0].getHeight() / getHeight());
            currentTranslationX = -source.getWidth();
            //gc.drawImage(wim [0], 0, 0);
        }




        if (at == null) {
            at = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    System.out.println();
                    System.out.println(AT);
                    System.out.println(source.getWidth());


                    // Секция объявления фона: на канвах рисуется прямоугольник определённого фона.
                    gc.setFill(Color.WHEAT);
                    gc.fillRect(AT, 0, source.getWidth() + AT, source.getHeight());

                    ScreenDrawer(gc, source.getWidth(), source.getHeight(), AT, 0);

                    //
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("sample").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), x, y); //0 - bad, 1 - good;

                    //

                    //gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("floor").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), Standartify(1000, 'w'), Standartify(200, 'h'));
                    //gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("floor").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), Standartify(299, 'w'), Standartify(200, 'h'));
                    //gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("floor").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), Standartify(200, 'w'), Standartify(200, 'h'));

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
                            AT += gm.get("MOVEMENT");
                            //}
                            break;
                        case 2:
                            x -= gm.get("MOVEMENT");
                            gc.translate(+gm.get("MOVEMENT"), 0);
                            AT -= gm.get("MOVEMENT");
                            break;
                    }
                }
            };
        }
    }

    public void ScreenDrawer(GraphicsContext gc, double screenwidth, double screenheight, double ATX, double ATY) {
        System.out.println(currentTranslationX);
        System.out.println(currentTranslationX + screenwidth);
        System.out.println((ATX >= (currentTranslationX + screenwidth)));
        if (ATX >= (currentTranslationX + screenwidth)) {
            System.out.println("redrawed");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            if (ATX != 0) {
                multiplier++;


                { //TODO: optimize;
                    wim[0] = wim[1];
                    wim[1] = wim[2];
                    params.setViewport(new Rectangle2D(screenwidth * (1 + multiplier), 0, screenwidth, screenheight));
                    wim[2] = structure.snapshot(params, null);

                    wim[3] = wim[4];
                    wim[4] = wim[5];
                    params.setViewport(new Rectangle2D(screenwidth * (1 + multiplier), 0, screenwidth, screenheight));
                    wim[5] = structure.snapshot(params, null);

                    wim[6] = wim[7];
                    wim[7] = wim[8];
                    params.setViewport(new Rectangle2D(screenwidth * (1 + multiplier), 0, screenwidth, screenheight));
                    wim[8] = structure.snapshot(params, null);
                }
            } else {
                for (int i = 0; i < 9; i++) {
                    params.setViewport(new Rectangle2D(screenwidth * ((i % 3) + multiplier - 1), 0, screenwidth, screenheight));
                    wim[i] = structure.snapshot(params, null);
                }
            }
            currentTranslationX += screenwidth;
        } else {
            /*gc.drawImage(OuterFunctions.scale(wim[4], 100, 100, true), 0, 0);
            gc.drawImage(OuterFunctions.scale(wim[0], 100, 100, true), 100, 0);
            gc.drawImage(OuterFunctions.scale(wim[1], 100, 100, true), 200, 0);
            gc.drawImage(OuterFunctions.scale(wim[2], 100, 100, true), 300, 0);
            gc.drawImage(OuterFunctions.scale(wim[3], 100, 100, true), 400, 0);
            gc.drawImage(OuterFunctions.scale(wim[5], 100, 100, true), 500, 0);
            gc.drawImage(OuterFunctions.scale(wim[6], 100, 100, true), 600, 0);
            gc.drawImage(OuterFunctions.scale(wim[7], 100, 100, true), 700, 0);
            gc.drawImage(OuterFunctions.scale(wim[8], 100, 100, true), 800, 0);*/
            gc.drawImage(wim[4], 0 + (screenwidth * multiplier), 0);
            gc.drawImage(wim[0], -screenwidth + (screenwidth * multiplier), -screenheight);
            gc.drawImage(wim[1], 0 + (screenwidth * multiplier), -screenheight);
            gc.drawImage(wim[2], +screenwidth + (screenwidth * multiplier), -screenheight);
            gc.drawImage(wim[3], -screenwidth + (screenwidth * multiplier), 0);
            gc.drawImage(wim[5], +screenwidth + (screenwidth * multiplier), 0);
            gc.drawImage(wim[6], -screenwidth + (screenwidth * multiplier), +screenheight);
            gc.drawImage(wim[7], 0 + (screenwidth * multiplier), +screenheight);
            gc.drawImage(wim[8], +screenwidth + (screenwidth * multiplier), +screenheight);
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
}