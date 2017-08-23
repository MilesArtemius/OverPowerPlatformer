package classes.ResizableCanvasStuff.LevelUploaders;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class screenRedrawer {
    LevelUploader LuP;

    double currentTranslationX = 0; // ATX / ScreenWidth.
    double currentTranslationY = 0; // ATY / ScreenHeight.
    WritableImage[] wim = new WritableImage [9]; // images around the protagonist.
    int multiplierX = 0; // number of currentXtranslations from 0,0.
    int multiplierY = 0; // number of currentYtranslations from 0,0.1

    public screenRedrawer(LevelUploader luP) {
        this.LuP = luP;
    }

    /*public void draw() {
        System.out.println("Translation by X: " + LuP.ATX);
        System.out.println("Translation by Y: " + LuP.ATY);
        System.out.println("Translation by X limit: " + LuP.currentTranslationX);
        System.out.println("Translation by Y limit: " + LuP.currentTranslationY);
        System.out.println("Window width: " + LuP.source.getWidth());
        System.out.println("Window height: " + LuP.source.getHeight());
        System.out.println("X: " + LuP.x);
        System.out.println("Y: " + LuP.y);

        if (LuP.forceRedraw) {
            System.out.println("Force redraw");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            for (int i = 0; i < 9; i++) {
                params.setViewport(new Rectangle2D(LuP.source.getWidth() * ((i % 3) + LuP.multiplierX - 1), LuP.source.getHeight() * (((int) (i / 3)) + LuP.multiplierY - 1), LuP.source.getWidth(), LuP.source.getHeight()));
                LuP.wim[i] = LuP.structure.snapshot(params, null);
            }

        } else if ((LuP.ATX < LuP.currentTranslationX) || (LuP.ATX > (LuP.currentTranslationX + LuP.source.getWidth()))) {
            System.out.println("X changed redraw");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            LuP.multiplierX += Math.signum(LuP.ATX - (LuP.currentTranslationX + LuP.source.getWidth()));
            System.out.println("multiplierX changed " + LuP.multiplierX);

            for (int i = 2; i < 9; i += 3) {
                LuP.wim[i-(1+((int) Math.signum(LuP.ATX - (LuP.currentTranslationX + LuP.source.getWidth()))))] = LuP.wim[i-1];
                LuP.wim[i-1] = LuP.wim[i-(1-((int) Math.signum(LuP.ATX - (LuP.currentTranslationX + LuP.source.getWidth()))))];
                if (i == 5) {
                    LuP.gc.drawImage(LuP.wim[4], LuP.source.getWidth() * LuP.multiplierX, LuP.source.getHeight() * LuP.multiplierY);
                }
                params.setViewport(new Rectangle2D(LuP.source.getWidth() * (LuP.multiplierX + Math.signum(LuP.ATX - (LuP.currentTranslationX + LuP.source.getWidth()))), LuP.source.getHeight() * (LuP.multiplierY + ((i-5)/2)), LuP.source.getWidth(), LuP.source.getHeight()));
                LuP.wim[i-(1-((int) Math.signum(LuP.ATX - (LuP.currentTranslationX + LuP.source.getWidth()))))] = LuP.structure.snapshot(params, null);
            }

            LuP.currentTranslationX += LuP.source.getWidth() * Math.signum(LuP.ATX - (LuP.currentTranslationX + LuP.source.getWidth()));

        } else if ((LuP.ATY < LuP.currentTranslationY) || (LuP.ATY > (LuP.currentTranslationY + LuP.source.getHeight()))) {
            System.out.println("Y changed redraw");

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.AQUA);

            LuP.multiplierY += Math.signum(LuP.ATY - (LuP.currentTranslationY + LuP.source.getHeight()));
            System.out.println("multiplierY changed " + LuP.multiplierY);

            for (int i = 0; i < 3; i++) {
                LuP.wim[i+3+(3*(-((int) Math.signum(LuP.ATY - (LuP.currentTranslationY + LuP.source.getHeight())))))] = LuP.wim[i+3];
                LuP.wim[i+3] = LuP.wim[i+3+(3*((int) Math.signum(LuP.ATY - (LuP.currentTranslationY + LuP.source.getHeight()))))];
                if (i == 1) {
                    LuP.gc.drawImage(LuP.wim[4], LuP.source.getWidth() * LuP.multiplierX, LuP.source.getHeight() * LuP.multiplierY);
                }
                params.setViewport(new Rectangle2D(LuP.source.getWidth() * (LuP.multiplierX + (1-i)), LuP.source.getHeight() * (LuP.multiplierY + Math.signum(LuP.ATY - (LuP.currentTranslationY + LuP.source.getHeight()))), LuP.source.getWidth(), LuP.source.getHeight()));
                LuP.wim[i+3+(3*((int) Math.signum(LuP.ATY - (LuP.currentTranslationY + LuP.source.getHeight()))))] = LuP.structure.snapshot(params, null);
            }

            LuP.currentTranslationY += LuP.source.getHeight() * Math.signum(LuP.ATY - (LuP.currentTranslationY + LuP.source.getHeight()));
            System.out.println("Limit Y changed " + LuP.currentTranslationY);

        } else {
            LuP.gc.drawImage(LuP.wim[4], LuP.source.getWidth() * LuP.multiplierX, LuP.source.getHeight() * LuP.multiplierY);
            LuP.gc.drawImage(LuP.wim[0], -LuP.source.getWidth() + (LuP.source.getWidth() * LuP.multiplierX), -LuP.source.getHeight() + (LuP.source.getHeight() * LuP.multiplierY));
            LuP.gc.drawImage(LuP.wim[1], LuP.source.getWidth() * LuP.multiplierX, -LuP.source.getHeight() + (LuP.source.getHeight() * LuP.multiplierY));
            LuP.gc.drawImage(LuP.wim[2], LuP.source.getWidth() + (LuP.source.getWidth() * LuP.multiplierX), -LuP.source.getHeight() + (LuP.source.getHeight() * LuP.multiplierY));
            LuP.gc.drawImage(LuP.wim[3], -LuP.source.getWidth() + (LuP.source.getWidth() * LuP.multiplierX), LuP.source.getHeight() * LuP.multiplierY);
            LuP.gc.drawImage(LuP.wim[5], +LuP.source.getWidth() + (LuP.source.getWidth() * LuP.multiplierX), LuP.source.getHeight() * LuP.multiplierY);
            LuP.gc.drawImage(LuP.wim[6], -LuP.source.getWidth() + (LuP.source.getWidth() * LuP.multiplierX), LuP.source.getHeight() + (LuP.source.getHeight() * LuP.multiplierY));
            LuP.gc.drawImage(LuP.wim[7], LuP.source.getWidth() * LuP.multiplierX, LuP.source.getHeight() + (LuP.source.getHeight() * LuP.multiplierY));
            LuP.gc.drawImage(LuP.wim[8], +LuP.source.getWidth() + (LuP.source.getWidth() * LuP.multiplierX), LuP.source.getHeight() + (LuP.source.getHeight() * LuP.multiplierY));
        }
    }*/
}
