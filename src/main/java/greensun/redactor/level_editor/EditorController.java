package greensun.redactor.level_editor;

import greensun.engine_support.OuterFunctions;
import greensun.redactor.TreeBuilder;
import greensun.redactor.TreeFile;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static greensun.engine_support.Depacker.pathname;

/**
 * Created by HP on 04.07.2017.
 */
public class EditorController {
    File folder;
    File textureFolder;
    File levelsFolder;
    ArrayList<File> levels;
    File config;
    File blockset;

    TreeFile openedInConsoleFile;
    PrintWriter consolePrintWriter;

    ConsoleReader consoleReader;

    @FXML
    public BorderPane rootPane;
    @FXML
    public SplitPane baseSplit;
    @FXML
    public SplitPane leftSplit;
    @FXML
    public TreeView folderView;
    @FXML
    public TextArea consoleView;
    @FXML
    public AnchorPane levelViewContainer;
    @FXML
    public Canvas levelView;
    @FXML
    public SplitPane rightSplit;
    @FXML
    public Accordion locatorMenu;
    @FXML
    public TitledPane blockSector;
    @FXML
    public TitledPane entitySector;
    @FXML
    public TitledPane comingsoonSector;
    @FXML
    public MenuBar toolbarView;
    @FXML
    public Menu fileMenu;
    @FXML
    public TextArea console;
    @FXML
    public TextField writableConsole;
    @FXML
    public VBox consoleContainer;


    @FXML
    public void initialize() {
        levelView.widthProperty().bind(levelViewContainer.widthProperty());
        levelView.heightProperty().bind(levelViewContainer.heightProperty());

        console.setEditable(false);

        levels = new ArrayList<>();

        folderView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                try {
                    TreeItem treeItem = (TreeItem) folderView.getSelectionModel().getSelectedItem();
                    TreeFile treeFile = (TreeFile) treeItem.getValue();
                    if (((treeFile.isFile()) && (treeFile.getName().substring(treeFile.getName().lastIndexOf('.')).equals(".upson"))) || (folderView.getRoot().equals(treeItem))) {
                        ContextMenu contextMenu = new ContextMenu();
                        MenuItem renameItem = new MenuItem("Rename");
                        renameItem.setOnAction(event1 -> {
                            try {
                                TextInputDialog renaming = new TextInputDialog(treeFile.toString());
                                renaming.setTitle("Rename");
                                renaming.setContentText("Enter a new name for the file");
                                renaming.showAndWait();
                                if (renaming.getResult() != null) { //TODO: with Files.getExtension;
                                    Path newPath = Files.move(Paths.get(treeFile.getAbsolutePath()), Paths.get(treeFile.getAbsolutePath()).resolveSibling(((renaming.getResult().contains(".upson")) || (treeItem.equals(folderView.getRoot())))?(renaming.getResult().substring(0, renaming.getResult().indexOf(".upson") + ".upson".length())):(renaming.getResult() + ".upson")));
                                    if (treeItem.equals(folderView.getRoot())) {
                                        TreeItem root = TreeBuilder.get().fileTreeBuilder(new TreeFile(newPath.toString(), newPath.toString().substring(newPath.toString().lastIndexOf('\\') + 1)), true);
                                        folderView.setRoot(root);
                                    } else {
                                        treeFile.setViewName(((renaming.getResult().contains(".upson"))?(renaming.getResult().substring(0, renaming.getResult().indexOf(".upson") + ".upson".length())):(renaming.getResult() + ".upson")));
                                        treeItem.getParent().setExpanded(false);
                                    }
                                } else {
                                    System.out.println("No renaming");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        contextMenu.getItems().add(renameItem);
                        contextMenu.show(rootPane.getScene().getWindow(), event.getScreenX(), event.getScreenY());
                    } else if (treeFile.getName().equals("levels")) {
                        ContextMenu contextMenu = new ContextMenu();
                        MenuItem addItem = new MenuItem("Add new level");
                        addItem.setOnAction(event1 -> {
                            try {
                                TextInputDialog addingNew = new TextInputDialog(treeFile.toString());
                                addingNew.showAndWait();
                                if (addingNew.getResult() != null) {
                                    Path path = Files.createFile(Paths.get(treeFile.getAbsolutePath() + ((addingNew.getResult().contains(".upson"))?(addingNew.getResult()):(addingNew.getResult() + ".upson"))));
                                    TreeItem item =new TreeItem<>(new TreeFile(path.toString(), ((addingNew.getResult().contains(".upson"))?(addingNew.getResult()):(addingNew.getResult() + ".upson"))));
                                    item.setGraphic(new ImageView(TreeBuilder.get().IMGlevel));
                                    treeItem.getChildren().add(item);
                                } else {
                                    System.out.println("No adding");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                        contextMenu.getItems().add(addItem);
                        contextMenu.show(rootPane.getScene().getWindow(), event.getScreenX(), event.getScreenY());
                    } else if (treeFile.getName().equals("textures")) {
                        ContextMenu contextMenu = new ContextMenu();
                        MenuItem addItem = new MenuItem("Add new texture");
                        addItem.setOnAction(event1 -> {
                            try {
                                FileChooser fc = new FileChooser();
                                fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Only capable image yet", "*.png"));
                                //DirectoryChooser fc = new DirectoryChooser();
                                //fc.showDialog((rootPane.getScene().getWindow()));
                                List<File> files = fc.showOpenMultipleDialog(rootPane.getScene().getWindow());
                                //ArrayList<File> files = new ArrayList<File>();
                                for (File file: files) {
                                    TextInputDialog addingNew = new TextInputDialog(file.toString());
                                    addingNew.showAndWait();
                                    if (addingNew.getResult() != null) {
                                        Path path = Files.copy(file.toPath(), Paths.get(treeFile.getAbsolutePath() + (addingNew.getResult() + "." + com.google.common.io.Files.getFileExtension(file.getName()))));
                                        TreeItem item = new TreeItem<>(new TreeFile(path.toString(), addingNew.getResult() + "." + com.google.common.io.Files.getFileExtension(file.getName())));
                                        item.setGraphic(new ImageView(TreeBuilder.get().IMGtexture));
                                        treeItem.getChildren().add(item);
                                    } else {
                                        System.out.println("No adding");
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("No adding");
                            }
                        });
                        contextMenu.getItems().add(addItem);
                        contextMenu.show(rootPane.getScene().getWindow(), event.getScreenX(), event.getScreenY());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.getClickCount() > 1) {
                try {
                    TreeItem treeItem = (TreeItem) folderView.getSelectionModel().getSelectedItem();
                    TreeFile treeFile = (TreeFile) treeItem.getValue();
                    if (treeFile.isImage()) {
                        Image image = new Image(Files.newInputStream(treeFile.toPath()));
                        double param = (levelView.getWidth() > levelView.getHeight())?(levelView.getHeight()):(levelView.getWidth());
                        levelView.getGraphicsContext2D().drawImage(OuterFunctions.scale(image, param, param, true), (levelView.getWidth() - param) / 2, (levelView.getHeight() - param) / 2);
                    } else if (treeFile.isFile()) {
                        openedInConsoleFile = treeFile;
                        consoleView.setText(new String(Files.readAllBytes(Paths.get(treeFile.getAbsolutePath()))));
                        if (consoleView.getText().length() == 0) {
                            consoleView.setPromptText("This file is empty...");
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
                    System.out.println("was not appended");
                }
            }
        });

        {
            MenuItem mit = new MenuItem("New level");
            mit.setOnAction(event -> {
                try {
                    folder = new File(pathname + "games\\custom_levels\\New Level (" + new TreeFile(pathname + "games\\custom_levels").listFiles().length + ")");
                    folder.mkdir();
                    textureFolder = new File(folder.getAbsolutePath() + "\\textures");
                    textureFolder.mkdir();
                    levelsFolder = new File(folder.getAbsolutePath() + "\\levels");
                    levelsFolder.mkdir();

                    levels.add(new File(levelsFolder.getAbsolutePath() + "\\test.upson"));
                    levels.get(0).createNewFile();

                    config = new File(folder.getAbsolutePath() + "\\config.json");
                    config.createNewFile();

                    blockset = new File(folder.getAbsolutePath() + "\\blockset.json");
                    blockset.createNewFile();

                    TreeItem root = TreeBuilder.get().fileTreeBuilder(new TreeFile(folder.getAbsolutePath(), folder.getAbsolutePath().substring(folder.getAbsolutePath().lastIndexOf('\\') + 1)), true);
                    folderView.setRoot(root);

                    consoleReader = new ConsoleReader(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            MenuItem mit1 = new MenuItem("Edit level");
            mit1.setOnAction(event -> {
                TreeSelectionDialog tsd = new TreeSelectionDialog();
                tsd.setTreeRoot(new TreeFile(pathname + "games\\custom_levels"));
                tsd.showAndWait();
                String path = (String) tsd.getResult();
                if (path != null) {
                    TreeItem root = TreeBuilder.get().fileTreeBuilder(new TreeFile(path, path.substring(path.lastIndexOf('\\') + 1)), true);
                    folderView.setRoot(root);
                    folder = new File(path);
                    textureFolder = new File(folder.getAbsolutePath() + "\\textures");
                    levelsFolder = new File(folder.getAbsolutePath() + "\\levels");
                    System.out.println(levelsFolder.isDirectory());
                    levels.addAll(Arrays.asList(levelsFolder.listFiles()));
                    config = new File(folder.getAbsolutePath() + "\\config.json");
                    blockset = new File(folder.getAbsolutePath() + "\\blockset.json");
                } else {
                    System.out.println("no renaming");
                }

                consoleReader = new ConsoleReader(this);
            });


            fileMenu.getItems().add(mit);
            fileMenu.getItems().add(mit1);
        }
    }
}