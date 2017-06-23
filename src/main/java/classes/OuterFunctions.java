package classes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by HP on 21.06.2017.
 */
public class OuterFunctions {
    public static Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
        ImageView imageView = new ImageView(source);
        imageView.setPreserveRatio(preserveRatio);
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        return imageView.snapshot(null, null);
    }
}
