package classes;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NeoButton extends StackPane {
    Text text;
    Rectangle frame;

    public NeoButton() {
        super();
        text = new Text();
        frame = new Rectangle();
        paintIt(Color.WHITE, Color.TRANSPARENT);
        this.getChildren().addAll(frame, text);

        this.setStyle("-fx-cursor: hand;");
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> paintIt(Color.BLACK, Color.WHITE)); //disable for screening
        this.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> paintIt(Color.WHITE, Color.TRANSPARENT));
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    @Override
    public void setHeight(double value) {
        frame.setStrokeWidth(value / 6);
        text.setFont(Font.font("Veranda", FontWeight.BOLD, value));
        recalculateSize();
    }

    private void recalculateSize () {
        super.setHeight(text.getBoundsInLocal().getHeight() * 12 / 10);
        super.setWidth(text.getBoundsInLocal().getWidth() * 12 / 10);
        frame.setHeight(text.getBoundsInLocal().getHeight() * 12 / 10);
        frame.setWidth(text.getBoundsInLocal().getWidth() * 12 / 10);
        frame.setArcWidth(text.getBoundsInLocal().getWidth() / 10);
        frame.setArcHeight(frame.getArcWidth());
    }

    public void paintIt(Color internal, Color external) {
        frame.setStroke(internal);
        text.setFill(internal);
        frame.setFill(external);
    }
}
