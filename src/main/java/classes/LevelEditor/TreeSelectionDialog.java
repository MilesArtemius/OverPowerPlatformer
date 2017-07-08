package classes.LevelEditor;

import classes.OuterFunctions;
import classes.StructureClasses.TreeBuilder;
import classes.StructureClasses.TreeFile;
import com.google.common.collect.TreeTraverser;
import com.google.common.io.Files;
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
        System.out.println(root.getAbsolutePath());
        TreeItem rootItem = TreeBuilder.get().fileTreeRootBuilder(new TreeFile(root.getAbsolutePath().substring(0, root.getAbsolutePath().indexOf('\\') + 1), root.getAbsolutePath().substring(0, root.getAbsolutePath().indexOf('\\') + 1)), root.getAbsolutePath(), 0);
        treeView.setRoot(rootItem);
    }
}
