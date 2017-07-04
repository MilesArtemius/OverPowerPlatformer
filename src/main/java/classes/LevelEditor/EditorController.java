package classes.LevelEditor;

import classes.ResizableCanvas;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;

import static classes.MainAndMenu.Depacker.pathname;

/**
 * Created by HP on 04.07.2017.
 */
public class EditorController {
    File folder;
    File textureFolder;
    File levelsFolder;
    File level;
    File config;
    File blockset;

    @FXML
    private BorderPane rootPane;
    @FXML
    private SplitPane baseSplit;
    @FXML
    private SplitPane leftSplit;
    @FXML
    private TreeView folderView;
    @FXML
    private TextArea consoleView;
    @FXML
    private AnchorPane levelViewContainer;
    @FXML
    private ResizableCanvas levelView;
    @FXML
    private SplitPane rightSplit;
    @FXML
    private Accordion locatorMenu;
    @FXML
    private TitledPane blockSector;
    @FXML
    private TitledPane entitySector;
    @FXML
    private TitledPane comingsoonSector;
    @FXML
    private MenuBar toolbarView;
    @FXML
    private Menu fileMenu;

    @FXML
    public void initialize() {
        levelView.widthProperty().bind(levelViewContainer.widthProperty());
        levelView.heightProperty().bind(levelViewContainer.heightProperty());

        MenuItem mit = new MenuItem("New level");
        mit.setOnAction(event -> {
            try {
                folder = new File(pathname + "games\\custom_levels\\newLevel " + new File(pathname + "games\\custom_levels").listFiles().length);
                folder.mkdir();
                textureFolder = new File(folder.getAbsolutePath() + "\\textures");
                textureFolder.mkdir();
                levelsFolder = new File(folder.getAbsolutePath() + "\\levels");
                levelsFolder.mkdir();
                config = new File(folder.getAbsolutePath() + "\\config.json");
                config.createNewFile();
                blockset = new File(folder.getAbsolutePath() + "\\blockset.json");
                blockset.createNewFile();

                TreeItem root = new TreeItem(folder);
                root.getChildren().addAll(new TreeItem<>(folder.listFiles()));
                folderView.setRoot(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        fileMenu.getItems().add(mit);
    }
}