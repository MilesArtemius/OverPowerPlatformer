package classes;

import classes.StructureClasses.TreeFile;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.IOException;

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

    public static TreeItem<TreeFile> fileTreeRootBuilder(TreeFile file, String pathToPrimaryFile, int lastIndex) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        System.out.println();
        if ((file.getAbsolutePath().equals(pathToPrimaryFile.substring(0, pathToPrimaryFile.substring(lastIndex).indexOf('\\') + ((lastIndex == 0)?(1):(0)) + lastIndex))) || (file.getAbsolutePath().equals(pathToPrimaryFile))) {
            System.out.println("TRUUUUUUUUUUUUUUE");
            treeItem.setExpanded(true);
            for (File subFile: file.listFiles()) {
                TreeFile subTreeFile = new TreeFile(subFile.getAbsolutePath(), subFile.getAbsolutePath().substring(subFile.getAbsolutePath().lastIndexOf('\\') + 1));
                treeItem.getChildren().add(fileTreeRootBuilder(subTreeFile, pathToPrimaryFile, pathToPrimaryFile.substring(lastIndex).indexOf('\\') + 1 + lastIndex));
            }
        } else if (file.isDirectory()) {
            if ((!file.getParent().equals("C:\\OP_GAME_SYS\\games\\custom_levels")) && (!file.isHidden() && (file.canRead()))) { //TODO: isLevel?
                OuterFunctions.loadTreeBuilder(treeItem);
            }
        } else {
            return new TreeItem<>(file);
        }
        return treeItem;
    }

    private static void loadTreeBuilder(TreeItem treeItem) {
        TreeFile treeFile = (TreeFile) treeItem.getValue();
        if ((treeFile.isDirectory()) && (!treeFile.isHidden() && (treeFile.canRead()))) {
            treeItem.getChildren().add(new TreeItem<>(new TreeFile("dummy", "dummy")));
            treeItem.setExpanded(false);
            treeItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Click");
                if (newValue) {
                    treeItem.getChildren().clear();
                    for (File filed : treeFile.listFiles()) {
                        TreeFile treeFiled = new TreeFile(filed.getAbsolutePath(), filed.getName());
                        TreeItem item = new TreeItem(treeFiled);
                        loadTreeBuilder(item);
                        treeItem.getChildren().add(item);
                    }
                }
            });
        }
    }
}
