package greensun.platformer_engine;

import greensun.engine_support.Depacker;
import greensun.engine_support.OuterFunctions;
import greensun.engine_support.ResizableCanvas;
import greensun.engine_support.every_day_singles.MediaStorage;
import greensun.engine_support.structure_classes.Block;
import greensun.engine_support.every_day_singles.GameRules;
import greensun.engine_support.structure_classes.Entity;
import greensun.engine_support.structure_classes.Level;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PlatformerUploader {
    ResizableCanvas structure;
    GraphicsContext structureGC;
    ResizableCanvas source;
    GraphicsContext sourceGC;
    ResizableCanvas decoration;
    GraphicsContext decorationGC;

    GameRules gr = GameRules.get("null"); // current level & rules.
    HashMap<String, Double> gm; // library of level rules.

    double x = 0; // x coordinate of protagonist.
    double y = 0; // x coordinate of protagonist.
    double t = 1; // time for leap.
    double ATX = 0; // translation X from the 0,0.
    double ATY = 0; // translation Y from the 0,0.

    AnimationTimer at; // timer of animation.

    boolean AntiJumper = true; // preventer of multiple jump.
    int jumper = 0;
    int movementer = 0;

    Level level; // level map.
    String levelPath = "null"; //absolute path to level;
    Double param; // min(ScreenWidth, ScreenHeight).

    interActivator activator;

    int millimomentsCounter = 0;



    public ResizableCanvas getStructure() {
        return structure;
    }

    public ResizableCanvas getSource() {
        return source;
    }

    public ResizableCanvas getDecoration() {
        return decoration;
    }

    public PlatformerUploader() {
        this.structure = new ResizableCanvas() {
            @Override
            public void redraw() {
                setBackground();
                if (!levelPath.equals("null")) {
                    resizeCanvas();
                }
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

        this.gr = GameRules.get(levelPath);
        resizeCanvas();
    }

    public void setBackground() {
        structureGC.drawImage(new Image(getClass().getResourceAsStream("/utils/pictures/TheGreenSun.jpg")), 0, 0, structure.getWidth(), structure.getHeight());
    }

    public void setSource(String path) {
        this.levelPath = path;
        level = Depacker.getStartedLevel(getClass(), levelPath);

        this.gr = GameRules.get(path);
        resizeCanvas();

        MediaPlayer player = new MediaPlayer(MediaStorage.get().getSounds().get("sofia.mp3"));
        //player.play(); //TODO: wrong.

        redrawCanvas();
    }

    public void resizeCanvas() {
        param = ((source.getHeight() > source.getWidth()) ? (source.getWidth()) : (source.getHeight()));
        gm = gr.getRules(source.getWidth(), source.getHeight(), param, true);

        if (level != null) {
            for (Map.Entry<String, Image> texture: MediaStorage.get().getTextures().entrySet()) {
                try {
                    MediaStorage.get().getTextures().replace(texture.getKey(), OuterFunctions.scale(texture.getValue(), gm.get("BLOCK_SIZE").intValue(), gm.get("BLOCK_SIZE").intValue(), (!gm.get("IMG_QUALITY").equals(0.0)))); //0 - bad, 1 - good;
                } catch (NullPointerException npe) {
                    npe.getMessage();
                }
            }
        }
    }

    public void redrawCanvas() {
        if ((source.getWidth() > 0) && (source.getHeight() > 0)) {

            if (ATX == 0) {
                x = level.mainCharacterX * gm.get("BLOCK_SIZE");
                y = level.mainCharacterY * gm.get("BLOCK_SIZE");
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

                    millimomentsCounter++;
                    if (millimomentsCounter >= 60) {
                        millimomentsCounter = 0;
                    }

                    if ((gm.get("PLATFORMER") == 1) && (gm.get("TEST_LEVEL") == 1) && (y > (level.Height * gm.get("BLOCK_SIZE")))) {
                        y = gm.get("BASIC_STATE_Y");
                        t = 2 * gm.get("SPEED") / gm.get("GRAVITY");;
                    }

                    System.out.println();

                    sourceGC.clearRect(ATX, ATY, source.getWidth() + ATX, source.getHeight() + ATY);

                    redraw(millimomentsCounter);

                    //TODO: choose redraw parameter: BlockByBlock, 9Images or ScreenCamera;

                    //
                    sourceGC.drawImage(MediaStorage.get().getTextures().get("OH_MOY_GRANDSON_NOT_BAD.png"), x, y);

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
            jumper = moves;
        }

        at.start();
    }

    public void move(int moves) {
        movementer = moves;
        at.start();
    }

    public void start() {
        at.start();
    }

    public void redraw(int millis) {
        structureGC.clearRect(ATX, ATY, structure.getWidth() + ATX, structure.getHeight() + ATY);

        structureGC.drawImage(MediaStorage.get().getBackgrounds().get("abyss.png"), ATX, ATY, structure.getWidth(), structure.getHeight());

        for (Entity entity: level.entitiesInCoordinate(true, Math.ceil((ATX + source.getWidth()) / gm.get("BLOCK_SIZE")), Math.floor(ATX / gm.get("BLOCK_SIZE")),
                level.entitiesInCoordinate(false, Math.ceil((ATY + source.getHeight()) / gm.get("BLOCK_SIZE")), Math.floor(ATY / gm.get("BLOCK_SIZE")), null))) {
            try {
                structureGC.drawImage(entity.getTexture(), gm.get("BLOCK_SIZE") * entity.getX(), gm.get("BLOCK_SIZE") * entity.getY());
            } catch (NullPointerException npe) {
                npe.getMessage();
            }
        }

        for (Block block: level.blocksInCoordinate(true, Math.ceil((ATX + source.getWidth()) / gm.get("BLOCK_SIZE")), Math.floor(ATX / gm.get("BLOCK_SIZE")),
                level.blocksInCoordinate(false, Math.ceil((ATY + source.getHeight()) / gm.get("BLOCK_SIZE")), Math.floor(ATY / gm.get("BLOCK_SIZE")), null))) {
            try {
                structureGC.drawImage(block.getTexture(millis), gm.get("BLOCK_SIZE") * block.getX(), gm.get("BLOCK_SIZE") * block.getY());
            } catch (NullPointerException npe) {
                npe.getMessage();
            }
        }
    }
}
