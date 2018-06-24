package ekdorn.thegreensun.redactor;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;


/**
 * Created by HP on 08.07.2017.
 */
public class TreeBuilder {
    private static TreeBuilder treeBuilder;
    private static Image IMGlevel_bundle;
    private static Image IMGfolder;
    private static Image IMGfile;
    public static Image IMGlevel;
    private static Image IMGlevel_folder;
    private static Image IMGresources;
    public static Image IMGtexture;
    private static Image IMGno_file;

    public static TreeBuilder get() {
        return treeBuilder = new TreeBuilder();
    }

    private TreeBuilder() {
        if (treeBuilder == null) {
            IMGlevel_bundle = new Image(getClass().getResourceAsStream("/utils/pictures/level_bundle.png"));
            IMGfolder = new Image(getClass().getResourceAsStream("/utils/pictures/folder.png"));
            IMGfile = new Image(getClass().getResourceAsStream("/utils/pictures/file.png"));
            IMGlevel = new Image(getClass().getResourceAsStream("/utils/pictures/level.png"));
            IMGlevel_folder = new Image(getClass().getResourceAsStream("/utils/pictures/level_folder.png"));
            IMGresources = new Image(getClass().getResourceAsStream("/utils/pictures/resources.png"));
            IMGtexture = new Image(getClass().getResourceAsStream("/utils/pictures/texture.png"));
            IMGno_file = new Image(getClass().getResourceAsStream("/utils/pictures/no_file.png"));
        }
    }

    public TreeItem<TreeFile> fileTreeBuilder(TreeFile file, boolean deep) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        treeItem.setExpanded(true);
        if (file.isDirectory()) {
            ImageView ivfo;
            if (file.getName().equals("levels"))  {
                ivfo = new ImageView(IMGlevel_folder);
            } else if (file.getName().equals("textures")) {
                ivfo = new ImageView(IMGresources);
            } else {
                ivfo = new ImageView(IMGlevel_bundle);
            }
            treeItem.setGraphic(ivfo);
            for (File subFile: file.listFiles()) {
                TreeFile subTreeFile = new TreeFile(subFile.getAbsolutePath(), subFile.getAbsolutePath().substring(subFile.getAbsolutePath().lastIndexOf('\\') + 1));
                treeItem.getChildren().add(((deep) ? (fileTreeBuilder(subTreeFile, true)) : (new TreeItem<>(subTreeFile))));
            }
        } else {
            ImageView ivfi;
            if (file.getParentFile().getName().equals("levels")) {
                ivfi = new ImageView(IMGlevel);
            } else if (file.getParentFile().getName().equals("textures")) {
                ivfi = new ImageView(IMGtexture);
            } else {
                ivfi = new ImageView(IMGfile);
            }
            treeItem.setGraphic(ivfi);
            return treeItem;
        }
        return treeItem;
    }

    public TreeItem<TreeFile> fileTreeRootBuilder(TreeFile file, String pathToPrimaryFile, int lastIndex) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        if ((file.getAbsolutePath().equals(pathToPrimaryFile.substring(0, pathToPrimaryFile.substring(lastIndex).indexOf('\\') + ((lastIndex == 0)?(1):(0)) + lastIndex))) || (file.getAbsolutePath().equals(pathToPrimaryFile))) {
            treeItem.setExpanded(true);
            for (File subFile: file.listFiles()) {
                TreeFile subTreeFile = new TreeFile(subFile.getAbsolutePath(), subFile.getAbsolutePath().substring(subFile.getAbsolutePath().lastIndexOf('\\') + 1));
                treeItem.getChildren().add(fileTreeRootBuilder(subTreeFile, pathToPrimaryFile, pathToPrimaryFile.substring(lastIndex).indexOf('\\') + 1 + lastIndex));
                ImageView ivfo = new ImageView(IMGfolder);
                treeItem.setGraphic(ivfo);
            }
        } else if (file.isDirectory()) {
            try {
                if ((!file.isHidden()) && (file.canRead())) {
                    ImageView iv;
                    if (file.isLevel()) {
                        iv = new ImageView(IMGlevel_bundle);
                        treeItem.setGraphic(iv);
                    } else {
                        iv = new ImageView(IMGfolder);
                        treeItem.setGraphic(iv);
                        loadTreeBuilder(treeItem);
                    }
                }
            } catch (Exception e) {
                ImageView ivnf = new ImageView(IMGno_file);
                treeItem.setGraphic(ivnf);
                return treeItem;
            }
        } else {
            ImageView ivfi = new ImageView(IMGfile);
            treeItem.setGraphic(ivfi);
            return treeItem;
        }
        if (treeItem.getGraphic() == null) {
            ImageView ivnf = new ImageView(IMGno_file);
            treeItem.setGraphic(ivnf);
        }
        return treeItem;
    }

    private void loadTreeBuilder(TreeItem treeItem) {
        TreeFile treeFile = (TreeFile) treeItem.getValue();
        if ((treeFile.isDirectory()) && (!treeFile.isHidden() && (treeFile.canRead()))) {
            if (treeFile.isLevel()) {
                ImageView ivle = new ImageView(IMGlevel_bundle);
                treeItem.setGraphic(ivle);
            } else {
                treeItem.getChildren().add(new TreeItem<>(new TreeFile("dummy", "dummy")));
                treeItem.setExpanded(false);
                ImageView ivfo = new ImageView(IMGfolder);
                treeItem.setGraphic(ivfo);
                treeItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
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
        } else {
            ImageView ivfi = new ImageView(IMGfile);
            treeItem.setGraphic(ivfi);
        }
    }
}
