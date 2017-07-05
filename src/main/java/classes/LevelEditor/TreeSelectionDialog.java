package classes.LevelEditor;

import classes.OuterFunctions;
import classes.StructureClasses.TreeFile;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.stage.Window;

/**
 * Created by HP on 06.07.2017.
 */
public class TreeSelectionDialog extends Dialog {
    TreeView<TreeFile> treeView;
    DialogPane pane;

    public TreeSelectionDialog() {
        super();
        Window window = this.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(event -> {
            this.setResult(null);
            window.hide();
        });


        treeView = new TreeView<>();

        pane = new DialogPane();
        pane.setContent(treeView);

        this.setDialogPane(pane);
    }

    public void setTreeRoot(TreeFile root) {
        TreeItem rootItem = OuterFunctions.fileTreeBuilder(new TreeFile(root.getAbsolutePath(), root.getAbsolutePath().substring(root.getAbsolutePath().lastIndexOf('\\') + 1)), true);
        treeView.setRoot(rootItem);
    }
}
