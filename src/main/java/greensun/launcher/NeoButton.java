package greensun.launcher;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class NeoButton extends StackPane {
    Label text;
    Rectangle frame;

    public NeoButton() {
        super();

        this.setStyle("-fx-cursor: hand;");
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> paintIt(Color.BLACK, Color.WHITE)); //disable for screening
        this.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> paintIt(Color.WHITE, Color.TRANSPARENT));
    }

    public void setText(String txt, double value) {
        frame = new Rectangle();
        this.text = new Label(txt);
        paintIt(Color.WHITE, Color.TRANSPARENT);

        frame.setStrokeWidth(value / 6);
        text.setFont(Font.font(text.getFont().getFamily(), value));
        this.getChildren().addAll(frame, text);

        recalculateSize();
    }

    private void recalculateSize () {
        Text theText = new Text(text.getText());
        theText.setFont(text.getFont());
        double width = theText.getBoundsInLocal().getWidth();
        System.out.println(width);

        super.setHeight(theText.getBoundsInLocal().getHeight() * 12 / 10);
        super.setWidth(theText.getBoundsInLocal().getWidth() * 12 / 10);
        frame.setHeight(theText.getBoundsInLocal().getHeight() * 12 / 10);
        frame.setWidth(theText.getBoundsInLocal().getWidth() * 12 / 10);
        frame.setArcWidth(theText.getBoundsInLocal().getHeight() / 10);
        frame.setArcHeight(theText.getBoundsInLocal().getHeight() / 10);
    }

    public void paintIt(Color internal, Color external) {
        frame.setStroke(internal);
        text.setTextFill(internal);
        frame.setFill(external);
    }
}
