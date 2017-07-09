package classes.LevelEditor;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by HP on 09.07.2017.
 */
public class ConsoleReader {
    TextArea console;

    public ConsoleReader(EditorController father) {
        this.console = father.console;
        this.console.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                readConsole(console.getText());
            }
        });
    }

    public void readConsole(String input) {
        String [] inputArray = input.split(" ");
    }
}
