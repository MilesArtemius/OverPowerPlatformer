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
    double ATX = 0; // translation X from the 0,0.
    double ATY = 0; // translation Y from the 0,0.
    AnimationTimer at; // timer of animation.
    boolean AntiJumper = false; // preventer of multiple jump.
    int MOVEMENTER = 0; // move variable.
    int MOVEMENTER2 = 0; // jump variable.
    Level level; // level map.
    Double param; // min(ScreenWidth, ScreenHeight).
    WritableImage[] wim = new WritableImage [9]; // images around the protagonist.
    Canvas structure; // level canvas.
    double currentTranslationX = 0; // ATX / ScreenWidth.
    double currentTranslationY = 0; // ATY / ScreenHeight.
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

            if (ATX == 0) {
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
            currentTranslationY = (currentTranslationY / CurrentSourceH) * source.getHeight();

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
                    gc.fillRect(ATX, ATY, source.getWidth() + ATX, source.getHeight() + ATY);

                    ScreenDrawer();

                    forceRedraw = false;

                    //
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz().get("sample").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), x, y); //0 - bad, 1 - good;

                    //
                    if (gm.get("PLATFORMER") == 1) {
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
                    } else {
                        switch (MOVEMENTER) {
                            case 1:
                                y -= gm.get("MOVEMENT");
                                gc.translate(0, gm.get("MOVEMENT"));
                                ATY -= gm.get("MOVEMENT");
                                break;
                            case 2:
                                y += gm.get("MOVEMENT");
                                gc.translate(0, -gm.get("MOVEMENT"));
                                ATY += gm.get("MOVEMENT");
                                break;
                        }
                    }
                    //
                    switch (MOVEMENTER2) {
                        case 1:
                            x += gm.get("MOVEMENT");
                            gc.translate(-gm.get("MOVEMENT"), 0);
                            ATX += gm.get("MOVEMENT");
                            break;
                        case 2:
                            x -= gm.get("MOVEMENT");
                            gc.translate(+gm.get("MOVEMENT"), 0);
                            ATX -= gm.get("MOVEMENT");
                            break;
                    }
                }
            };
        }
    }

    public void ScreenDrawer() {
        System.out.println("Translation by X: " + ATX);
        System.out.println("Translation by Y: " + ATY);
        System.out.println("Translation by X limit: " + currentTranslationX);
        System.out.println("Translation by Y limit: " + currentTranslationY);
        System.out.println("Window width: " + source.getWidth());
        System.out.println("Window height: " + source.getHeight());

        if (forceRedraw) {
            System.out.println("Force redraw");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            for (int i = 0; i < 9; i++) {
                params.setViewport(new Rectangle2D(source.getWidth() * ((i % 3) + multiplierX - 1), source.getHeight() * (((int) (i / 3)) + multiplierY - 1), source.getWidth(), source.getHeight()));
                wim[i] = structure.snapshot(params, null);
            }

        } else if ((ATX < currentTranslationX) || (ATX > (currentTranslationX + source.getWidth()))) {
            System.out.println("X changed redraw");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            multiplierX += Math.signum(ATX - (currentTranslationX + source.getWidth()));
            System.out.println("multiplierX changed " + multiplierX);

            for (int i = 2; i < 9; i += 3) {
                wim[i-(1+((int) Math.signum(ATX - (currentTranslationX + source.getWidth()))))] = wim[i-1];
                wim[i-1] = wim[i-(1-((int) Math.signum(ATX - (currentTranslationX + source.getWidth()))))];
                if (i == 5) {
                    gc.drawImage(wim[4], source.getWidth() * multiplierX, source.getHeight() * multiplierY);
                }
                params.setViewport(new Rectangle2D(source.getWidth() * (multiplierX + Math.signum(ATX - (currentTranslationX + source.getWidth()))), source.getHeight() * (multiplierY + ((i-5)/2)), source.getWidth(), source.getHeight()));
                wim[i-(1-((int) Math.signum(ATX - (currentTranslationX + source.getWidth()))))] = structure.snapshot(params, null);
            }

            currentTranslationX += source.getWidth() * Math.signum(ATX - (currentTranslationX + source.getWidth()));

        } else if ((ATY < currentTranslationY) || (ATY > (currentTranslationY + source.getHeight()))) {
            System.out.println("Y changed redraw");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            multiplierY += Math.signum(ATY - (currentTranslationY + source.getHeight()));
            System.out.println("multiplierY changed " + multiplierY);

            for (int i = 0; i < 3; i++) {
                wim[i+3+(3*(-((int) Math.signum(ATY - (currentTranslationY + source.getHeight())))))] = wim[i+3];
                wim[i+3] = wim[i+3+(3*((int) Math.signum(ATY - (currentTranslationY + source.getHeight()))))];
                if (i == 1) {
                    gc.drawImage(wim[4], source.getWidth() * multiplierX, source.getHeight() * multiplierY);
                }
                params.setViewport(new Rectangle2D(source.getWidth() * (multiplierX + (1-i)), source.getHeight() * (multiplierY + Math.signum(ATY - (currentTranslationY + source.getHeight()))), source.getWidth(), source.getHeight()));
                wim[i+3+(3*((int) Math.signum(ATY - (currentTranslationY + source.getHeight()))))] = structure.snapshot(params, null);
            }

            currentTranslationY += source.getHeight() * Math.signum(ATY - (currentTranslationY + source.getHeight()));
            System.out.println("Limit Y changed " + currentTranslationY);

        } else {
            gc.drawImage(wim[4], source.getWidth() * multiplierX, source.getHeight() * multiplierY);
            gc.drawImage(wim[0], -source.getWidth() + (source.getWidth() * multiplierX), -source.getHeight() + (source.getHeight() * multiplierY));
            gc.drawImage(wim[1], source.getWidth() * multiplierX, -source.getHeight() + (source.getHeight() * multiplierY));
            gc.drawImage(wim[2], source.getWidth() + (source.getWidth() * multiplierX), -source.getHeight() + (source.getHeight() * multiplierY));
            gc.drawImage(wim[3], -source.getWidth() + (source.getWidth() * multiplierX), source.getHeight() * multiplierY);
            gc.drawImage(wim[5], +source.getWidth() + (source.getWidth() * multiplierX), source.getHeight() * multiplierY);
            gc.drawImage(wim[6], -source.getWidth() + (source.getWidth() * multiplierX), source.getHeight() + (source.getHeight() * multiplierY));
            gc.drawImage(wim[7], source.getWidth() * multiplierX, source.getHeight() + (source.getHeight() * multiplierY));
            gc.drawImage(wim[8], +source.getWidth() + (source.getWidth() * multiplierX), source.getHeight() + (source.getHeight() * multiplierY));
        }
    }





    public void jump(int moves) {
        if ((gm.get("PLATFORMER") == 1) && (moves == 0)) {
            AntiJumper = true;
        } else {
            MOVEMENTER = moves;
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