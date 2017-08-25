package greensun.strategy_engine;

import greensun.engine_support.ResizableCanvas;
import greensun.engine_support.screenRedrawer;
import greensun.engine_support.structure_classes.GameRulez;
import greensun.engine_support.structure_classes.Level;
import greensun.engine_support.Depacker;
import greensun.engine_support.OuterFunctions;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class StrategyUploader {
    ResizableCanvas structure;
    GraphicsContext structureGC;
    ResizableCanvas source;
    GraphicsContext sourceGC;
    ResizableCanvas decoration;
    GraphicsContext decorationGC;

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

    Level level; // level map.
    String levelPath = "null"; //absolute path to level;
    Double param; // min(ScreenWidth, ScreenHeight).

    interActivator activator;
    screenRedrawer redrawer;



    public ResizableCanvas getStructure() {
        return structure;
    }

    public ResizableCanvas getSource() {
        return source;
    }

    public ResizableCanvas getDecoration() {
        return decoration;
    }

    public StrategyUploader() {
        this.structure = new ResizableCanvas() {
            @Override
            public void redraw() {
                redrawCanvas();
            }
        };
        this.structureGC = structure.getGraphicsContext2D();
        this.source = new ResizableCanvas() {
            @Override
            public void redraw() {}
        };
        this.sourceGC = source.getGraphicsContext2D();
        this.decoration = new ResizableCanvas() {
            @Override
            public void redraw() {}
        };
        this.decorationGC = decoration.getGraphicsContext2D();
    }

    public void setSource(String path) {
        this.levelPath = path;
        this.gr = GameRulez.get(path);

    }

    public void redrawCanvas() {
        if ((source.getWidth() > 0) && (source.getHeight() > 0)) {

            param = ((source.getHeight() > source.getWidth()) ? (source.getWidth()) : (source.getHeight()));

            gm = gr.getRulez(source.getWidth(), source.getHeight(), param, true);
            System.out.println(gm.toString());

            if (ATX == 0) {
                level = Depacker.getStartedLevel(getClass(), levelPath);

                x = level.mainCharacterX * gm.get("BLOCK_SIZE");
                y = level.mainCharacterY * gm.get("BLOCK_SIZE");
            }

            structureGC.clearRect(0, 0, source.getWidth(), source.getHeight());
            structureGC.setFill(Color.WHEAT);
            structureGC.fillRect(0, 0, source.getWidth(), source.getHeight());

            for (int i = 0; i < level.level.length; i++) {
                for (int j = 0; j < level.level[i].length; j++) {
                    try {
                        structureGC.drawImage(OuterFunctions.scale(level.level[i][j].texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), gm.get("BLOCK_SIZE") * i, gm.get("BLOCK_SIZE") * j);
                        level.level[i][j].resetImage(OuterFunctions.scale(level.level[i][j].texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))));
                    } catch (NullPointerException npe) {
                        npe.getMessage();
                    }
                }
            }

            for (int i = 0; i < level.entities.length; i++) {
                for (int j = 0; j < level.entities[i].length; j++) {
                    try {
                        structureGC.drawImage(OuterFunctions.scale(level.entities[i][j].skin, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), gm.get("BLOCK_SIZE") * i, gm.get("BLOCK_SIZE") * j);
                    } catch (NullPointerException npe) {
                        npe.getMessage();
                    }
                }
            }

            activator = new interActivator(this);
            //redrawer = new screenRedrawer(this);
        }


        if (at == null) {
            at = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    System.out.println("Translation by X: " + ATX);
                    System.out.println("Translation by Y: " + ATY);
                    System.out.println("Window width: " + source.getWidth());
                    System.out.println("Window height: " + source.getHeight());
                    System.out.println("X: " + x);
                    System.out.println("Y: " + y);

                    /*System.out.println(gm.get("SPEED") * t);
                    System.out.println(gm.get("GRAVITY") * t * t / 2);
                    System.out.println(t);
                    System.out.println(jumper);*/

                    if ((gm.get("PLATFORMER") == 1) && (gm.get("TEST_LEVEL") == 1) && (y > (level.Height * gm.get("BLOCK_SIZE")))) {
                        y = gm.get("BASIC_STATE_Y");
                        t = 2 * gm.get("SPEED") / gm.get("GRAVITY");;
                    }

                    System.out.println();

                    sourceGC.clearRect(ATX, ATY, source.getWidth() + ATX, source.getHeight() + ATY);

                    redraw();

                    //TODO: choose redraw parameter: BlockByBlock, 9Images or ScreenCamera;

                    System.out.println(jumper);

                    //
                    sourceGC.drawImage(OuterFunctions.scale(gr.getBlockz(levelPath, false).get("sample").texture, gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0))), x, y); //0 - bad, 1 - good;

                    if (gm.get("PLATFORMER") == 1) {
                        if ((jumper == 2) || (!AntiJumper)) { // optimize

                            if (Math.abs((gm.get("SPEED") * t) - (gm.get("GRAVITY") * t * t / 2)) >=  gm.get("BLOCK_SIZE")) {
                                t -= 0.1;
                            } else {
                                t += 0.1;
                            }

                            y = y - (gm.get("SPEED") * t) + (gm.get("GRAVITY") * t * t / 2);
                            jumper = 2; //TODO: make jump not fixed height;
                        } else {
                            t = 2 * gm.get("SPEED") / gm.get("GRAVITY");
                            jumper = 2;
                        }

                        activator.activateDown();
                        activator.activateUp();
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
                    //jumper = jumpRequest;
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

    public void redraw() {
        structureGC.clearRect(ATX, ATY, structure.getWidth() + ATX, structure.getHeight() + ATY);
        for (int i = 0; i < level.level.length; i++) {
            for (int j = 0; j < level.level[i].length; j++) {
                try {
                    structureGC.drawImage(level.level[i][j].texture, gm.get("BLOCK_SIZE") * i, gm.get("BLOCK_SIZE") * j);
                } catch (NullPointerException npe) {
                    npe.getMessage();
                }
            }
        }
    }
}