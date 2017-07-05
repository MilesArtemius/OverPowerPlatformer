package classes.LevelEditor;

import classes.ResizableCanvas;
import classes.StructureClasses.TreeFile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    TreeFile openedInConsoleFile;
    PrintWriter consolePrintWriter;

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

    private TreeItem<TreeFile> fileTreeBuilder(TreeFile file, boolean deep) {
        TreeItem<TreeFile> treeItem = new TreeItem<>(file);
        if (file.isDirectory()) {
            for (File subfile: file.listFiles()) {
                TreeFile subtreefile = new TreeFile(subfile.getAbsolutePath(), subfile.getAbsolutePath().substring(subfile.getAbsolutePath().lastIndexOf('\\') + 1));
                treeItem.getChildren().add(((deep) ? (fileTreeBuilder(subtreefile, true)) : (new TreeItem<>(subtreefile))));
            }
        } else {
            return new TreeItem<>(file);
        }
        return treeItem;
    }

    private void editLevel() {
        try {
            File rootFolder = new File(pathname + "games\\custom_levels");
            TreeItem root = fileTreeBuilder(new TreeFile(rootFolder.getAbsolutePath(), rootFolder.getAbsolutePath().substring(rootFolder.getAbsolutePath().lastIndexOf('\\') + 1)), false);
            folderView.setRoot(root);
            folderView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                TreeItem file = (TreeItem<TreeFile>) newValue;
                TreeFile kk = (TreeFile) file.getValue();
                TreeItem newRoot = fileTreeBuilder(new TreeFile(kk.getAbsolutePath(), kk.getAbsolutePath().substring(kk.getAbsolutePath().lastIndexOf('\\') + 1)), true);
                folderView.setRoot(newRoot);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        levelView.widthProperty().bind(levelViewContainer.widthProperty());
        levelView.heightProperty().bind(levelViewContainer.heightProperty());

        folderView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                try {
                    TreeItem treeItem = (TreeItem) folderView.getSelectionModel().getSelectedItem();
                    TreeFile treeFile = (TreeFile) treeItem.getValue();
                    if (((treeFile.isFile()) && (treeFile.getName().substring(treeFile.getName().lastIndexOf('.')).equals(".upson"))) || (folderView.getRoot().equals(treeItem))) {
                        ContextMenu contextMenu = new ContextMenu();
                        MenuItem menuItem = new MenuItem("Rename");
                        menuItem.setOnAction(event1 -> {
                            try {
                                TextInputDialog renaming = new TextInputDialog(treeFile.toString());
                                renaming.showAndWait();
                                Path newPath = Files.move(Paths.get(treeFile.getAbsolutePath()), Paths.get(treeFile.getAbsolutePath()).resolveSibling(renaming.getResult()));
                                if (treeItem.equals(folderView.getRoot())) {
                                    TreeItem root = fileTreeBuilder(new TreeFile(newPath.toString(), newPath.toString().substring(newPath.toString().lastIndexOf('\\') + 1)), true);
                                    folderView.setRoot(root);
                                } else {
                                    treeFile.setViewName(renaming.getResult());
                                    treeItem.getParent().setExpanded(false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        contextMenu.getItems().add(menuItem);
                        contextMenu.show(rootPane.getScene().getWindow(), event.getScreenX(), event.getScreenY());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.getClickCount() > 1) {
                TreeItem treeItem = (TreeItem) folderView.getSelectionModel().getSelectedItem();
                TreeFile treeFile = (TreeFile) treeItem.getValue();
                try {
                    if (treeFile.isFile()) {
                        openedInConsoleFile = treeFile;
                        consoleView.setText(new String(Files.readAllBytes(Paths.get(treeFile.getAbsolutePath()))));
                        if (consoleView.getText().length() == 0) {
                            consoleView.setPromptText("Press 'Ctrl + Enter' to save changes");
                        }
                    } else {
                        consoleView.setText("");
                        consoleView.setPromptText("Press 'Ctrl + Enter' to save changes");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        consoleView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if ((event.getCode() == KeyCode.ENTER) && (consoleView.isFocused()) && (consoleView.getText() != null) && (event.isControlDown())) {
                try {
                    consolePrintWriter = new PrintWriter(openedInConsoleFile.getAbsolutePath());
                    consolePrintWriter.println(consoleView.getText());
                    consolePrintWriter.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        MenuItem mit = new MenuItem("New level");
        mit.setOnAction(event -> {
            try {
                folder = new File(pathname + "games\\custom_levels\\New Level (" + new TreeFile(pathname + "games\\custom_levels").listFiles().length + ")");
                folder.mkdir();
                textureFolder = new File(folder.getAbsolutePath() + "\\textures");
                textureFolder.mkdir();
                levelsFolder = new File(folder.getAbsolutePath() + "\\levels");
                levelsFolder.mkdir();

                level = new File(levelsFolder.getAbsolutePath() + "\\test.upson");
                level.createNewFile();

                config = new File(folder.getAbsolutePath() + "\\config.json");
                config.createNewFile();
                PrintWriter pw = new PrintWriter(config.getAbsolutePath());
                pw.println("It is the .json configuration file of the level...");
                pw.close();

                blockset = new File(folder.getAbsolutePath() + "\\blockset.json");
                blockset.createNewFile();

                TreeItem root = fileTreeBuilder(new TreeFile(folder.getAbsolutePath(), folder.getAbsolutePath().substring(folder.getAbsolutePath().lastIndexOf('\\') + 1)), true);
                folderView.setRoot(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        MenuItem mit1 = new MenuItem("Edit level");
        mit1.setOnAction(event -> {
            //editLevel();
        });


        fileMenu.getItems().add(mit);
        fileMenu.getItems().add(mit1);
    }
}