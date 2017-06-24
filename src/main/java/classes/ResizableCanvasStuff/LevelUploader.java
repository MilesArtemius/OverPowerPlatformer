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
    static int multiplierX = 0; // number of currentXtranslations from 0,0.
    static int multiplierY = 0; // number of currentYtranslations from 0,0.
    boolean forceRedraw;
    ResizableCanvas source;
    GraphicsContext gc;
    double CurrentSourceW = 1;
    double CurrentSourceH = 1;

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

            if (AT == 0) {
                x = gm.get("BASIC_STATE_X");
                y = gm.get("BASIC_STATE_Y");

                level = Depacker.getStartedLevel(getClass());
            }

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

            currentTranslationX = (currentTranslationX / CurrentSourceW) * source.getWidth();

            forceRedraw = true;

            /*for (int i = 0; i < 9; i++) {
                wim[i] = new WritableImage(1,1);
            }*/

            CurrentSourceH = source.getHeight();
            CurrentSourceW = source.getWidth();
        }


        if (at == null) {
            at = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    //System.out.println(x);
                    //System.out.println(y);
                    System.out.println();


                    gc.setFill(Color.WHEAT);
                    gc.fillRect(AT, 0, source.getWidth() + AT, source.getHeight());

                    ScreenDrawer(gc, source.getWidth(), source.getHeight(), AT, 0);

                    forceRedraw = false;

                    //
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("sample").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), x, y); //0 - bad, 1 - good;

                    //

                    //
                    if (MOVEMENTER == 1) {
                        y = y - (gm.get("SPEED") * t - gm.get("GRAVITY") * t * t / 2) / gm.get("MULTIPLIER");
                        t += 0.3;
                        System.out.println("Y COORDINATE IS " + y);
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
        System.out.println(ATX);
        System.out.println(currentTranslationX);
        System.out.println(screenwidth);
        System.out.println((ATX >= (currentTranslationX + screenwidth)));
        if ((ATX < currentTranslationX) || (ATX > (currentTranslationX + screenwidth)) || (forceRedraw)) {
            System.out.println("redrawed");
            System.out.println("is force: " + forceRedraw);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            if ((ATX != 0) && (!forceRedraw)) {

                multiplierX += Math.signum(ATX - (currentTranslationX + screenwidth));
                System.out.println("multiplierX changed " + multiplierX);

                for (int i = 2; i < 9; i += 3) {
                    wim[i-(1+((int) Math.signum(ATX - (currentTranslationX + screenwidth))))] = wim[i-1];
                    wim[i-1] = wim[i-(1-((int) Math.signum(ATX - (currentTranslationX + screenwidth))))];
                    if (i == 5) {
                        gc.drawImage(wim[4], 0 + (screenwidth * multiplierX), 0);
                    }
                    params.setViewport(new Rectangle2D(screenwidth * (multiplierX + Math.signum(ATX - (currentTranslationX + screenwidth))), 0, screenwidth, screenheight));
                    wim[i-(1-((int) Math.signum(ATX - (currentTranslationX + screenwidth))))] = structure.snapshot(params, null);
                }

                currentTranslationX += screenwidth * Math.signum(ATX - (currentTranslationX + screenwidth));

            } else {
                for (int i = 0; i < 9; i++) {
                    params.setViewport(new Rectangle2D(screenwidth * ((i % 3) + multiplierX - 1), 0, screenwidth, screenheight));
                    wim[i] = structure.snapshot(params, null);
                }
            }
        } else {
            gc.drawImage(wim[4], 0 + (screenwidth * multiplierX), 0);
            gc.drawImage(wim[0], -screenwidth + (screenwidth * multiplierX), -screenheight);
            gc.drawImage(wim[1], 0 + (screenwidth * multiplierX), -screenheight);
            gc.drawImage(wim[2], +screenwidth + (screenwidth * multiplierX), -screenheight);
            gc.drawImage(wim[3], -screenwidth + (screenwidth * multiplierX), 0);
            gc.drawImage(wim[5], +screenwidth + (screenwidth * multiplierX), 0);
            gc.drawImage(wim[6], -screenwidth + (screenwidth * multiplierX), +screenheight);
            gc.drawImage(wim[7], 0 + (screenwidth * multiplierX), +screenheight);
            gc.drawImage(wim[8], +screenwidth + (screenwidth * multiplierX), +screenheight);
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