package classes.StructureClasses;

import classes.LevelEditor.TreeSelectionDialog;
import classes.OuterFunctions;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;


/**
 * Created by HP on 08.07.2017.
 */
public class TreeBuilder {
    private static TreeBuilder treeBuilder;
    private Image level;
    private Image folder;
    private ImageView file;

    public static TreeBuilder get() {
        return treeBuilder = new TreeBuilder();
    }

    private TreeBuilder() {
        if (treeBuilder == null) {
            level = new Image(getClass().getResourceAsStream("/textures/ErEsursE.png"));
            folder = new Image(getClass().getResourceAsStream("/textures/floor.png"));
        }
    }

    public TreeItem<TreeFile> fileTreeBuilder(TreeFile file, boolean deep) {
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

    public TreeItem<TreeFile> fileTreeRootBuilder(TreeFile file, String pathToPrimaryFile, int lastIndex, TreeView parent, TreeSelectionDialog root) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        if ((file.getAbsolutePath().equals(pathToPrimaryFile.substring(0, pathToPrimaryFile.substring(lastIndex).indexOf('\\') + ((lastIndex == 0)?(1):(0)) + lastIndex))) || (file.getAbsolutePath().equals(pathToPrimaryFile))) {
            treeItem.setExpanded(true);
            for (File subFile: file.listFiles()) {
                TreeFile subTreeFile = new TreeFile(subFile.getAbsolutePath(), subFile.getAbsolutePath().substring(subFile.getAbsolutePath().lastIndexOf('\\') + 1));
                treeItem.getChildren().add(fileTreeRootBuilder(subTreeFile, pathToPrimaryFile, pathToPrimaryFile.substring(lastIndex).indexOf('\\') + 1 + lastIndex, parent, root));
            }
        } else if (file.isDirectory()) {
            try {
                if ((!file.isHidden()) && (file.canRead())) {
                    if (file.isLevel()) {
                        ImageView iv = new ImageView(level);
                        treeItem.setGraphic(iv);
                        parent.setOnMouseClicked(event -> {
                            if (event.getClickCount() > 1) {
                                root.setResult(treeItem.getValue().getAbsolutePath());
                            }
                        });
                    } else {
                        loadTreeBuilder(treeItem);
                    }
                }
            } catch (Exception e) {
                return new TreeItem<>(file);
            }
        } else {
            return new TreeItem<>(file);
        }
        return treeItem;
    }

    private void loadTreeBuilder(TreeItem treeItem) {
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
