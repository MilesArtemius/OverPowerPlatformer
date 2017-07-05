package classes;

import classes.StructureClasses.TreeFile;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

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

    public static TreeItem<TreeFile> fileTreeBuilder(TreeFile file, boolean deep) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        if (file.isDirectory()) {
            for (File subFile: file.listFiles()) {
                TreeFile subTreeFile = new TreeFile(subFile.getAbsolutePath(), subFile.getAbsolutePath().substring(subFile.getAbsolutePath().lastIndexOf('\\') + 1));
                treeItem.getChildren().add(((deep) ? (fileTreeBuilder(subTreeFile, true)) : (new TreeItem<>(subTreeFile))));
            }
        } else {
            return new TreeItem<>(file);
        }
        return treeItem;
    }
}
