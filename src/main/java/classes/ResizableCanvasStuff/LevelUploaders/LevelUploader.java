package classes.ResizableCanvasStuff.LevelUploaders;


import classes.MainAndMenu.Depacker;
import classes.Additionals.OuterFunctions;
import classes.ResizableCanvas;
import classes.ResizableCanvasStuff.BasicUploader;
import classes.StructureClasses.Entity;
import classes.StructureClasses.GameRulez;
import classes.StructureClasses.Level;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.HashMap;

public class LevelUploader extends BasicUploader {
    GameRulez gr = GameRulez.get("null"); // current level & rules.
    HashMap<String, Double> gm; // library of level rules.
    double x = 0; // x coordinate of protagonist.
    double y = 0; // x coordinate of protagonist.
    double t = 1; // time for leap.
    double ATX = 0; // translation X from the 0,0.
    double ATY = 0; // translation Y from the 0,0.
    AnimationTimer at; // timer of animation.
    boolean AntiJumper = true; // preventer of multiple jump.
    int jumpRequest = 0;
    int jumper = 0;
    int movementer = 0;
    int moveRequest = 0;
    int MOVEMENTER = 0; // move variable.
    int MOVEMENTER2 = 0; // jump variable.
    Level level; // level map.
    ArrayList<Entity> entities;
    String levelPath = "null"; //absolute path to level;
    Double param; // min(ScreenWidth, ScreenHeight).
    WritableImage[] wim = new WritableImage [9]; // images around the protagonist.
    Canvas structure; // level canvas.
    double currentTranslationX = 0; // ATX / ScreenWidth.
    double currentTranslationY = 0; // ATY / ScreenHeight.
    int multiplierX = 0; // number of currentXtranslations from 0,0.
    int multiplierY = 0; // number of currentYtranslations from 0,0.
    boolean forceRedraw;
    double CurrentSourceW = 1;
    double CurrentSourceH = 1;

    interActivator activator;
    screenRedrawer redrawer;

    public LevelUploader(ResizableCanvas resizableCanvas) {
        super(resizableCanvas);
    }

    public void setSource(String path) {
        this.levelPath = path;
        this.gr = GameRulez.get(path);

    }

    @Override
    public void redrawCanvas() {
        if ((source.getWidth() > 0) && (source.getHeight() > 0)) {

            GraphicsContext gc = source.getGraphicsContext2D();

            param = ((source.getHeight() > source.getWidth()) ? (source.getWidth()) : (source.getHeight()));

            gm = gr.getRulez(source.getWidth(), source.getHeight(), param, true);
            System.out.println(gm.toString());

            if (ATX == 0) {
                level = Depacker.getStartedLevel(getClass(), levelPath);

                x = level.mainCharacterX * gm.get("BLOCK_SIZE");
                y = level.mainCharacterY * gm.get("BLOCK_SIZE");
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

            for (int i = 0; i < level.entities.length; i++) {
                for (int j = 0; j < level.entities[i].length; j++) {
                    try {
                        str_gc.drawImage(OuterFunctions.scale(level.entities[i][j].skin, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), gm.get("BLOCK_SIZE") * i, gm.get("BLOCK_SIZE") * j);
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

            activator = new interActivator(this);
            redrawer = new screenRedrawer(this);
        }


        if (at == null) {
            at = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    if ((gm.get("TEST_LEVEL") == 1) && (y > (level.Height * gm.get("BLOCK_SIZE")))) {
                        y = gm.get("BASIC_STATE_Y");
                        t = 2 * gm.get("SPEED") / gm.get("GRAVITY");;
                    }

                    System.out.println();


                    gc.setFill(Color.WHEAT);
                    gc.fillRect(ATX, ATY, source.getWidth() + ATX, source.getHeight() + ATY);

                    redrawer.draw();

                    forceRedraw = false;

                    System.out.println(jumper);

                    //
                    gc.drawImage(OuterFunctions.scale(gr.getBlockz(levelPath, false).get("sample").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), x, y); //0 - bad, 1 - good;

                    if (gm.get("PLATFORMER") == 1) {
                        if ((jumper == 2) || (!AntiJumper)) {
                            y = y - (gm.get("SPEED") * t - gm.get("GRAVITY") * t * t / 2);
                            t += 0.1;
                        } else {
                            t = 2 * gm.get("SPEED") / gm.get("GRAVITY");
                            jumper = 2;
                        }
                    } else {
                        switch (jumper) {
                            case 1:
                                y += gm.get("MOVEMENT");
                                activator.activateDown();
                                break;
                            case 2:
                                y -= gm.get("MOVEMENT");
                                activator.activateUp();
                                break;
                        }
                    }

                    switch (movementer) {
                        case 1:
                            x += gm.get("MOVEMENT");
                            activator.activateRight();
                            break;
                        case 2:
                            x -= gm.get("MOVEMENT");
                            activator.activateLeft();
                            break;
                    }

                    movementer = moveRequest;
                    jumper = jumpRequest;
                }
            };
        }
    }

    public void jump(int moves) {
        if (gm.get("PLATFORMER") == 1) {
            if (moves == 2) {
                AntiJumper = false;
            } else {
                AntiJumper = true;
            }
        } else {
            jumpRequest = moves;
        }
        at.start();
    }

    public void move(int moves) {
        moveRequest = moves;
        at.start();
    }

    public void start() {
        at.start();
    }
}