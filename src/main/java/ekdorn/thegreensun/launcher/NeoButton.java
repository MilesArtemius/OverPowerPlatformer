package ekdorn.thegreensun.launcher;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class NeoButton extends StackPane {
    Text text;
    Rectangle frame;
    Shape shape;

    public NeoButton() {
        super();

        this.setStyle("-fx-cursor: hand;");
        this.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> paintIt(Color.TRANSPARENT, Color.WHITE));
        this.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> paintIt(Color.WHITE, Color.TRANSPARENT));
    }

    public void setText(String txt, double value) {
        frame = new Rectangle();
        text = new Text(0, 0, txt);
        frame.setStrokeWidth(value / 6);
        text.setFont(Font.font(text.getFont().getFamily(), FontWeight.BOLD, value));

        super.setHeight(text.getBoundsInLocal().getHeight() * 12 / 10);
        super.setWidth(text.getBoundsInLocal().getWidth() * 12 / 10);
        frame.setHeight(text.getBoundsInLocal().getHeight() * 12 / 10);
        frame.setWidth(text.getBoundsInLocal().getWidth() * 12 / 10);
        frame.setArcWidth(text.getBoundsInLocal().getHeight() / 10);
        frame.setArcHeight(text.getBoundsInLocal().getHeight() / 10);

        text.setX(frame.getX() + frame.getWidth()/2 - text.getBoundsInLocal().getWidth()/2);
        text.setY(frame.getHeight() - frame.getHeight()/2 + text.getBoundsInLocal().getHeight()/7*2);

        shape = Shape.subtract(frame, text);
        shape.setStrokeType(StrokeType.OUTSIDE);
        shape.setStrokeWidth(value / 6);
        paintIt(Color.WHITE, Color.TRANSPARENT);

        this.getChildren().addAll(shape);
    }

    public void paintIt(Color internal, Color external) {
        shape.setStroke(internal);
        shape.setFill(external);
    }
}
