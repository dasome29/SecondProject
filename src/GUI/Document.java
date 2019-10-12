package GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Document {
//    private Rectangle base = new Rectangle();
//    double posy = base.getHeight() + base.getTranslateY();
    Document(Pane pane){
        Rectangle base = new Rectangle();
        base.setX(50);
        base.setY(50);
        base.setWidth(200);
        base.setHeight(100);
        base.setArcHeight(20);
        base.setArcHeight(20);
        base.setFill(Color.GREY);
        base.setStroke(Color.BLACK);
        pane.getChildren().addAll(base);
    }
}
